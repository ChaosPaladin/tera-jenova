package com.angelis.tera.game.presentation.network.connection;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.angelis.tera.common.network.connection.AbstractTeraConnection;
import com.angelis.tera.common.network.connection.ConnectionState;
import com.angelis.tera.common.network.packet.AbstractClientPacket;
import com.angelis.tera.common.network.packet.AbstractServerPacket;
import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.presentation.network.packet.ClientPacketHandler;
import com.angelis.tera.game.presentation.network.packet.ServerPacketHandler;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.key.KeyClientPacket;
import com.angelis.tera.game.presentation.network.packet.key.KeyServerPacket;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.services.AccountService;
import com.angelis.tera.game.process.services.PlayerService;

public class TeraGameConnection extends AbstractTeraConnection {

    /** LOGGER */
    private static Logger log = Logger.getLogger(TeraGameConnection.class.getName());

    private final Deque<AbstractServerPacket<TeraGameConnection>> sendMsgQueue = new ArrayDeque<AbstractServerPacket<TeraGameConnection>>();

    private Account account;
    private Player activePlayer;

    private final Lock lock = new ReentrantLock();

    public TeraGameConnection(final SocketChannel socketChannel, final SelectionKey selectionKey) {
        super(socketChannel, selectionKey);
    }

    @Override
    public void onConnect() {
        log.info("Client connected");

        this.state = ConnectionState.CONNECTED;
        this.sendPacket(new KeyServerPacket());
    }

    @Override
    public void onAuthenticate() {
        this.state = ConnectionState.AUTHENTICATED;
    }

    @Override
    public void onDisconnect() {
        log.info("Client left");

        this.state = ConnectionState.NONE;

        if (this.activePlayer != null) {
            PlayerService.getInstance().onPlayerDisconnect(this.activePlayer);
            this.activePlayer = null;
        }

        if (this.account != null) {
            AccountService.getInstance().onAccountDisconnect(this.account);
            this.account = null;
        }
    }

    public final void setAccount(final Account account) {
        this.account = account;
        account.setConnection(this);
    }

    public final Account getAccount() {
        return this.account;
    }

    public final void setActivePlayer(final Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public final Player getActivePlayer() {
        return this.activePlayer;
    }

    @Override
    public boolean readPacket(final ByteBuffer buffer) {
        AbstractClientPacket<TeraGameConnection> packet = null;

        switch (this.state) {
            case CONNECTED:
                packet = new KeyClientPacket(buffer, this);
            break;

            case AUTHENTICATED:
                final short id = buffer.getShort();

                final Class<? extends AbstractClientPacket<TeraGameConnection>> packetClass = ClientPacketHandler.getClientPacket(id);
                if (packetClass == null) {
                    log.error(PrintUtils.toHex(buffer) + "\n");
                    buffer.clear();
                    return true;
                }

                try {
                    packet = packetClass.getConstructor(ByteBuffer.class, TeraGameConnection.class).newInstance(buffer, this);
                }
                catch (final Exception e) {
                    log.error(e.getMessage(), e);
                }
            break;

            default:
                throw new RuntimeException("Trying to readPacket while connection is in unknown state");
        }

        if (packet != null) {
            if (packet.read()) {
                lock.lock();
                new Thread(packet).start();
                lock.unlock();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean writePacket(final ByteBuffer buffer) {
        synchronized (gate) {
            if (this.sendMsgQueue.isEmpty()) {
                return false;
            }

            final AbstractServerPacket<TeraGameConnection> packet = this.sendMsgQueue.removeFirst();
            final Short opcode = ServerPacketHandler.getServerPacketId(packet.getClass());
            
            if (opcode == null) {
                return false;
            }

            packet.setOpcode(opcode);
            packet.write(this, buffer);
            return true;
        }
    }

    @Override
    public boolean hasPacketToRead() {
        return true;
    }

    @Override
    public boolean hasPacketToWrite() {
        return !this.sendMsgQueue.isEmpty();
    }

    // UTILITY
    public void sendPacket(final AbstractServerPacket<TeraGameConnection> teraServerPacket) {
        synchronized (gate) {
            sendMsgQueue.addLast(teraServerPacket);
            this.enableWriteInterest();
        }
    }

    public void sendPackets(final List<TeraServerPacket> packets) {
        for (final TeraServerPacket teraServerPacket : packets) {
            this.sendPacket(teraServerPacket);
        }
    }
}

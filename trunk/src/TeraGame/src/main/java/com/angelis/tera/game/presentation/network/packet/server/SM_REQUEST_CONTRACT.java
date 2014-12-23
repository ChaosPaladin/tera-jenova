package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.model.player.request.NpcTradeRequest;
import com.angelis.tera.game.process.model.player.request.PartyInviteRequest;

public class SM_REQUEST_CONTRACT extends TeraServerPacket {

    private final AbstractRequest<?, ?> request;

    public SM_REQUEST_CONTRACT(final AbstractRequest<?, ?> request) {
        this.request = request;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int initiatorNameStartShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        final int initiatorNameEndShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        final int endShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        if (connection.getActivePlayer().equals(this.request.getInitiator())) {
            writeH(byteBuffer, this.request.getMaxResponseTime());
            writeUid(byteBuffer, this.request.getInitiator());
        }
        else {
            writeH(byteBuffer, 0);
            writeUid(byteBuffer, this.request.getTarget());
        }

        // TODO find those values
        switch (this.request.getRequestType()) {
            case PLAYER_TRADE:
                writeUid(byteBuffer, this.request.getTarget());
            break;

            default:
                writeQ(byteBuffer, 0);
        }

        writeD(byteBuffer, this.request.getRequestType().value);
        writeQ(byteBuffer, this.request.getUid());
        writeD(byteBuffer, 0);

        switch (this.request.getRequestType()) {
            case PARTY_INVITE:
                if (connection.getActivePlayer().equals(this.request.getInitiator())) {
                    this.writeBufferPosition(byteBuffer, initiatorNameStartShift);
                    writeS(byteBuffer, ((Player) this.request.getInitiator()).getName());
                    this.writeBufferPosition(byteBuffer, initiatorNameEndShift);

                    writeH(byteBuffer, 0);
                    this.writeBufferPosition(byteBuffer, endShift);

                    writeS(byteBuffer, ((PartyInviteRequest) this.request).getTarget().getName());
                    writeH(byteBuffer, 0);
                }
                else {
                    this.writeBufferPosition(byteBuffer, initiatorNameStartShift);
                    writeS(byteBuffer, ((Player) this.request.getTarget()).getName());
                    this.writeBufferPosition(byteBuffer, initiatorNameEndShift);

                    writeH(byteBuffer, 0);
                    this.writeBufferPosition(byteBuffer, endShift);
                }
            break;

            case PLAYER_TRADE:
                writeS(byteBuffer, ((NpcTradeRequest) this.request).getTarget().getName());
            break;

            case PEGASUS:
            // Nothing to do
            break;

            case MAILBOX:
                this.writeBufferPosition(byteBuffer, initiatorNameStartShift);
                writeS(byteBuffer, ((Player) this.request.getInitiator()).getName());
                this.writeBufferPosition(byteBuffer, initiatorNameEndShift);

                writeH(byteBuffer, 0);
                this.writeBufferPosition(byteBuffer, endShift);
            break;

            default:
                // TODO
                /*
                 * if(Request is GuildCreate) writeS(byteBuffer,
                 * ((GuildCreate)Request).GuildName); else if(Request is
                 * GuildInvite) writeS(byteBuffer, Request.Owner.Guild != null ?
                 * Request.Owner.Guild.GuildName : "Unknown guild");
                 */
        }
    }
}

package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.model.player.request.DuelRequest;
import com.angelis.tera.game.process.model.player.request.PartyInviteRequest;
import com.angelis.tera.game.process.model.player.request.PegasusShowMapRequest;
import com.angelis.tera.game.process.model.player.request.PlayerTradeRequest;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;
import com.angelis.tera.game.process.services.RequestService;
import com.angelis.tera.game.process.services.WorldService;

public class CM_REQUEST_CONTRACT extends TeraClientPacket {

    private AbstractRequest<?, ?> request;
    
    public CM_REQUEST_CONTRACT(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        final short nameShift = (short) (readH()-2);
        readH(); //unk shift
        readH();

        final RequestTypeEnum requestType = RequestTypeEnum.fromValue(readH());
        if (requestType != null) {
            switch (requestType) {
                case PARTY_INVITE:
                    this.readUntil(nameShift);
                    final String invitedTargetName = readS();
                    this.request = new PartyInviteRequest(this.getConnection().getActivePlayer(), WorldService.getInstance().getOnlinePlayerByName(invitedTargetName));
                    readC();
                break;
                
                case DUEL:
                    // TODO
                    this.readUntil(nameShift);
                    final String duelTargetName = readS();
                    this.request = new DuelRequest(this.getConnection().getActivePlayer(), WorldService.getInstance().getOnlinePlayerByName(duelTargetName));
                break;
                
                case PEGASUS:
                    readB(new byte[16]); // unk
                    this.request = new PegasusShowMapRequest(this.getConnection().getActivePlayer());
                break;
                
                case NPC_TRADE:
                    this.readUntil(nameShift);
                    final String tradeTargetName = readS();
                    this.request = new PlayerTradeRequest(this.getConnection().getActivePlayer(), WorldService.getInstance().getOnlinePlayerByName(tradeTargetName));
                break;
                
                default:
            }
        }
    }

    @Override
    protected void runImpl() {
        RequestService.getInstance().onPlayerRequest(this.getConnection().getActivePlayer(), this.request);
    }
}

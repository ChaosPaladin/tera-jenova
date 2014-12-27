package com.angelis.tera.game.process.model.player.request;

import java.util.concurrent.TimeUnit;

import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_COMPLETE;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT_CANCEL;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_WINDOW_HIDE;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_WINDOW_SHOW;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;
import com.angelis.tera.game.process.services.ThreadPoolService;
import com.angelis.tera.game.process.tasks.request.AutoCancelRequestTask;

public class PlayerTradeRequest extends AbstractRequest<Player, Player> {

    public PlayerTradeRequest(final Player initiator, final Player target) {
        super(initiator, target, RequestTypeEnum.PLAYER_TRADE);
    }

    @Override
    public boolean check() {
        // TODO check if player is not already trading or target isn't too
        return true;
    }

    @Override
    public void onStart() {
        this.target.getConnection().sendPacket(new SM_REQUEST_CONTRACT(this));
        this.target.getConnection().sendPacket(new SM_REQUEST_WINDOW_SHOW(this));
        ThreadPoolService.getInstance().scheduleTask(new AutoCancelRequestTask(this), this.getMaxResponseTime(), TimeUnit.SECONDS);
    }

    @Override
    public void onAction() {
        this.target.getConnection().sendPacket(new SM_REQUEST_CONTRACT_CANCEL(this));
        this.target.getConnection().sendPacket(SystemMessages.PLAYER_TRADE_START());
        this.initiator.getConnection().sendPacket(SystemMessages.PLAYER_TRADE_START());
        this.initiator.getConnection().sendPacket(new SM_REQUEST_COMPLETE(this));
    }

    @Override
    public void onCancel() {
        this.initiator.getController().setRequest(null);
        this.target.getConnection().sendPacket(new SM_REQUEST_CONTRACT_CANCEL(this));
        this.target.getConnection().sendPacket(new SM_REQUEST_WINDOW_HIDE(this));
    }
    
    @Override
    public int getMaxResponseTime() {
        return 30;
    }
}

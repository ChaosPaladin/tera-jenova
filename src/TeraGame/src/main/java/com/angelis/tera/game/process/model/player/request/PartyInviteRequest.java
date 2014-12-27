package com.angelis.tera.game.process.model.player.request;

import java.util.concurrent.TimeUnit;

import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.group.SM_GROUP_UNK;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_COMPLETE;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT_CANCEL;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_WINDOW_HIDE;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_WINDOW_SHOW;
import com.angelis.tera.game.process.model.group.Group;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;
import com.angelis.tera.game.process.services.GroupService;
import com.angelis.tera.game.process.services.ThreadPoolService;
import com.angelis.tera.game.process.tasks.request.AutoCancelRequestTask;

public class PartyInviteRequest extends AbstractRequest<Player, Player> {

    public PartyInviteRequest(final Player initiator, final Player target) {
        super(initiator, target, RequestTypeEnum.PARTY_INVITE);
    }
    
    @Override
    public boolean check() {
        final TeraGameConnection connection = this.initiator.getConnection();
        if (this.target == null) {
            connection.sendPacket(SystemMessages.REQUEST_PLAYER_NOT_FOUND());
            return false;
        }
        
        if (this.target.equals(this.initiator)) {
            connection.sendPacket(SystemMessages.REQUEST_CHOOSE_ANOTHER_PLAYER());
            return false;
        }

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
        Group group = this.initiator.getGroup();
        if (group == null) {
            group = new Group();
        }

        group.addPlayer(this.initiator);
        group.addPlayer(this.target);
        
        this.initiator.setGroup(group);
        this.target.setGroup(group);
        
        this.target.getConnection().sendPacket(new SM_REQUEST_CONTRACT_CANCEL(this));
        
        this.initiator.getConnection().sendPacket(new SM_REQUEST_COMPLETE(this));
        this.initiator.getConnection().sendPacket(new SM_GROUP_UNK());
        
        GroupService.getInstance().sendGroupMemberList(group);
        
        this.target.getConnection().sendPacket(new SM_GROUP_UNK());
    }
    
    @Override
    public void onCancel() {
        this.initiator.getController().setRequest(null);
        this.target.getConnection().sendPacket(new SM_REQUEST_CONTRACT_CANCEL(this));
        this.target.getConnection().sendPacket(new SM_REQUEST_WINDOW_HIDE(this));
    }
    
    @Override
    public int getMaxResponseTime() {
        return 20;
    }
}

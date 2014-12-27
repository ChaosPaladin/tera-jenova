package com.angelis.tera.game.process.model.player.request;

import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT_CANCEL;
import com.angelis.tera.game.presentation.network.packet.server.request.SM_REQUEST_CONTRACT_REPLY;
import com.angelis.tera.game.process.model.AbstractUniqueTeraModel;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;
import com.angelis.tera.game.process.services.ThreadPoolService;
import com.angelis.tera.game.process.tasks.TaskTypeEnum;

public abstract class AbstractRequest<I extends AbstractCreature, T extends AbstractCreature> extends AbstractUniqueTeraModel {
    
    protected final I initiator;
    protected final T target;
    protected final RequestTypeEnum requestType;
    private boolean canceled;

    public AbstractRequest(final I initiator, final T target, final RequestTypeEnum requestType) {
        super(null);
        this.initiator = initiator;
        this.target = target;
        this.requestType = requestType;
    }

    public I getInitiator() {
        return initiator;
    }
    
    public T getTarget() {
        return target;
    }

    public final RequestTypeEnum getRequestType() {
        return requestType;
    }

    public final void doStart() {
        if (this.initiator instanceof Player) {
            final Player player = (Player) this.initiator;
            player.getConnection().sendPacket(new SM_REQUEST_CONTRACT_REPLY(this.requestType));
            
            if (this.check()) {
                player.getConnection().sendPacket(new SM_REQUEST_CONTRACT(this));
                player.getController().setRequest(this);
                
                this.onStart();
            }
        }
    }
    
    public final void doAction() {
        this.onAction();
    }
    
    public final void doCancel() {
        ThreadPoolService.getInstance().cancelTask(this, TaskTypeEnum.REQUEST_AUTO_CANCEL);
        if (this.initiator instanceof Player) {
            final Player player = (Player) this.initiator;
            player.getConnection().sendPacket(new SM_REQUEST_CONTRACT_CANCEL(this));
        }

        this.canceled = true;
        this.onCancel();
    }
    
    public final boolean isCanceled() {
        return this.canceled;
    }
    
    public int getMaxResponseTime() {
        return 0;
    }
    
    public abstract boolean check();
    public abstract void onStart();
    public abstract void onAction();
    public abstract void onCancel();
}

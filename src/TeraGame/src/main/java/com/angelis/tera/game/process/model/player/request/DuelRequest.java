package com.angelis.tera.game.process.model.player.request;

import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;

public class DuelRequest extends AbstractRequest<Player, Player> {

    public DuelRequest(final Player initiator, final Player target) {
        super(initiator, target, RequestTypeEnum.DUEL);
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAction() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
        
    }

}

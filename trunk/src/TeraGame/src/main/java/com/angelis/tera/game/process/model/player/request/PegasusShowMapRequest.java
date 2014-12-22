package com.angelis.tera.game.process.model.player.request;

import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;

public class PegasusShowMapRequest extends AbstractRequest<Player, AbstractCreature> {
    
    public PegasusShowMapRequest(final Player initiator) {
        super(initiator, null, RequestTypeEnum.PEGASUS);
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onAction() {
    }
    
    @Override
    public void onCancel() {
    }
}

package com.angelis.tera.game.process.tasks;

import com.angelis.tera.common.utils.collection.Pair;
import com.angelis.tera.game.presentation.network.packet.server.SM_ABNORMALITY_END;
import com.angelis.tera.game.process.model.abnormality.Abnormality;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.services.VisibleService;

public class CreatureEndAbnormality extends AbstractTask<Pair<AbstractCreature, Abnormality>> {

    public CreatureEndAbnormality(final Pair<AbstractCreature, Abnormality> linkedObject) {
        super(linkedObject, TaskTypeEnum.PLAYER_ABNORMALITY_END);
    }

    @Override
    public void execute() {
        this.linkedObject.first.getAbnormalities().remove(this.linkedObject.second);
        VisibleService.getInstance().sendPacketForVisible(this.linkedObject.first, new SM_ABNORMALITY_END(this.linkedObject.second));
    }
}

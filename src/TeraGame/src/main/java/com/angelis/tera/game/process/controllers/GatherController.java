package com.angelis.tera.game.process.controllers;

import java.util.List;

import com.angelis.tera.game.presentation.network.packet.server.gather.SM_GATHER_END;
import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.creature.VisibleObjectEventTypeEnum;
import com.angelis.tera.game.process.model.drop.Drop;
import com.angelis.tera.game.process.model.drop.enums.ItemCategoryEnum;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.gather.enums.GatherResultEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.services.DropService;
import com.angelis.tera.game.process.services.SpawnService;
import com.angelis.tera.game.process.services.StorageService;
import com.angelis.tera.game.process.services.VisibleService;

public class GatherController extends Controller<Gather> {

    @Override
    public void update(final VisibleObjectEventTypeEnum creatureEventType, final TeraObject object, final Object... data) {
        // TODO Auto-generated method stub
    }

    public void gather(final Player player) {
        this.owner.processGather();
        final int remainingGather = this.owner.getRemainingGather();
        if (remainingGather <= 0) {
            this.onDie(this.owner.getGatherer());
        }
        
        final List<Drop> drops = DropService.getInstance().getDropFromGather(this.owner, ItemCategoryEnum.NONE);
        for (final Drop drop : drops) {
            StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), drop.getItemId(), DropService.getInstance().generateDropAmount(player, drop));
        }
    }

    @Override
    public void onStartAttack(final VisibleTeraObject target) {
        // Nothing to do
    }

    @Override
    public void onDamage(final VisibleTeraObject attacker, final int damage) {
        // Nothing to do
    }
    
    @Override
    public void onEndAttack() {
        // Nothing to do
    }

    @Override
    public void onDie(final VisibleTeraObject killer) {
        SpawnService.getInstance().despawnGather(this.owner, true);
    }

    @Override
    public void onRespawn() {
        this.owner.initGather();
        this.owner.getKnownList().update();
    }

    public void endGather(final GatherResultEnum gatherResult) {
        final Player player = this.owner.getGatherer();
        if (player == null) {
            return;
        }

        this.owner.setGatherer(null);
        VisibleService.getInstance().sendPacketForVisible(player, new SM_GATHER_END(player, this.owner, gatherResult));
    }
}

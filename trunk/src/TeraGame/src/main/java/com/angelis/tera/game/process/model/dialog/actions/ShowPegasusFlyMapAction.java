package com.angelis.tera.game.process.model.dialog.actions;

import java.util.List;

import com.angelis.tera.game.network.packet.server.SM_PEGASUS_MAP_SHOW;
import com.angelis.tera.game.process.model.dialog.AbstractDialogAction;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.pegasus.PegasusFly;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.PegasusFlyService;

public class ShowPegasusFlyMapAction extends AbstractDialogAction {

    public ShowPegasusFlyMapAction(final Player player, final Dialog dialog) {
        super(player, dialog);
    }

    @Override
    public void action() {
        final List<PegasusFly> pegasusFlies = PegasusFlyService.getInstance().getPegasusFliesByCreatureFullId(this.dialog.getNpc().getTemplate().getFullId());
        if (pegasusFlies != null) {
            this.player.getConnection().sendPacket(new SM_PEGASUS_MAP_SHOW(pegasusFlies));
        }
    }
}

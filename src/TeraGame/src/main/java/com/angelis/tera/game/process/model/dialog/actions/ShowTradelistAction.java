package com.angelis.tera.game.process.model.dialog.actions;

import com.angelis.tera.game.presentation.network.packet.server.SM_NPC_TRADELIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_REQUEST_CONTRACT;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.dialog.AbstractDialogAction;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.NpcTradeRequest;
import com.angelis.tera.game.process.model.tradelist.Tradelist;
import com.angelis.tera.game.process.services.TradelistService;

public class ShowTradelistAction extends AbstractDialogAction {

    public ShowTradelistAction(final Player player, final Dialog dialog) {
        super(player, dialog);
    }

    @Override
    public void action() {
        final Npc npc = this.dialog.getNpc();
        final Tradelist tradelist = TradelistService.getInstance().getTradelistByCreatureFullId(npc.getTemplate().getFullId());
        if (tradelist == null) {
            return;
        }

        final NpcTradeRequest request = new NpcTradeRequest(npc, this.player);
        this.player.getConnection().sendPacket(new SM_REQUEST_CONTRACT(request));
        this.player.getConnection().sendPacket(new SM_NPC_TRADELIST(npc, request, tradelist, this.dialog.getLastClickedButton().getDialogString()));
    }
}

package com.angelis.tera.game.process.services;

import java.util.List;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_SOCIAL;
import com.angelis.tera.game.presentation.network.packet.server.dialog.SM_DIALOG;
import com.angelis.tera.game.presentation.network.packet.server.dialog.SM_DIALOG_CLOSE;
import com.angelis.tera.game.presentation.network.packet.server.dialog.SM_DIALOG_EVENT;
import com.angelis.tera.game.presentation.network.packet.server.dialog.SM_DIALOG_MENU_SELECT;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.dialog.DialogButton;
import com.angelis.tera.game.process.model.dialog.actions.ShowBankAction;
import com.angelis.tera.game.process.model.dialog.actions.ShowMailBoxAction;
import com.angelis.tera.game.process.model.dialog.actions.ShowPegasusFlyMapAction;
import com.angelis.tera.game.process.model.dialog.actions.ShowTradelistAction;
import com.angelis.tera.game.process.model.dialog.enums.DialogIconEnum;
import com.angelis.tera.game.process.model.dialog.enums.DialogStringEnum;
import com.angelis.tera.game.process.model.enums.CreatureTitleEnum;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.EmoteEnum;
import com.angelis.tera.game.process.model.template.CreatureTemplate;

public class DialogService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DialogService.class.getName());

    @Override
    public void onInit() {
        log.info("DialogService started");
    }

    @Override
    public void onDestroy() {
        log.info("DialogService stopped");
    }

    public void onNpcContact(final Player player, final int uid) {
        final Creature creature = (Creature) ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.CREATURE, uid);
        if (creature == null) {
            return;
        }

        final Npc npc = (Npc) creature;
        final CreatureTemplate creatureTemplate = creature.getTemplate();
        final Dialog dialog = new Dialog(player, npc, creatureTemplate.getHuntingZoneId());

        player.getConnection().sendPacket(new SM_DIALOG_MENU_SELECT(1));

        // TODO creature template
        final CreatureTitleEnum creatureTitle = creatureTemplate.getCreatureTitle();
        if (creatureTitle != null) {
            switch (creatureTitle) {
                case FLIGHT_MASTER:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.FLIGHTPOINTS, new ShowPegasusFlyMapAction(player, dialog)));
                break;

                case BANK:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.BANK, new ShowBankAction(player, dialog)));
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.MAILBOX, new ShowMailBoxAction(player, dialog)));

                    if (player.getGuild() != null) {
                        dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.GUILD_BANK, new ShowBankAction(player, dialog)));
                    }
                break;

                case MERCHANT:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.BUY_GENERAL_GOODS, new ShowTradelistAction(player, dialog)));
                break;

                case CRYSTAL_MERCHANT:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.BUY_CRYSTALS, new ShowTradelistAction(player, dialog)));
                break;

                case PAINT_MERCHANT:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.DYE_SHOP, new ShowTradelistAction(player, dialog)));
                break;

                case PVP_MERCHANT:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.PVP_SHOP, new ShowTradelistAction(player, dialog)));
                break;

                case SPECIALITY_MERCHANT:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.SPECIALITY_STORE, new ShowTradelistAction(player, dialog)));
                break;

                case NOCTENIUM_MERCHANT:
                    dialog.addDialogButton(new DialogButton(dialog, DialogIconEnum.CENTERED_GREEN, DialogStringEnum.TEST, new ShowTradelistAction(player, dialog)));
                break;
            }
        }

        // Add quest buttons to the dialog
        QuestService.getInstance().addQuestDialogButtons(npc, player, dialog);

        this.sendDialogToPlayer(player, dialog);
    }

    public void onDialogEvent(final Player player, final int page, final int dialogUid) {
        final Dialog dialog = (Dialog) ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.DIALOG, dialogUid);
        if (dialog == null) {
            return;
        }

        final List<TeraServerPacket> packets = new FastList<>();
        if (page == 2) {
            this.playerCloseDialog(player, dialog);
            return;
        }

        if (page == 0) {
            packets.add(new SM_SOCIAL(player, EmoteEnum.TALK, 0));
        }

        packets.add(new SM_DIALOG_EVENT(dialog.getNpc(), player, page));
        player.getConnection().sendPackets(packets);
    }

    public void onDialog(final Player player, final int dialogUid, final int choice) {
        final Dialog dialog = ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.DIALOG, dialogUid);
        if (dialog == null) {
            return;
        }

        final DialogButton button = dialog.getDialogButtons().get(choice - 1);
        if (button == null) {
            return;
        }

        // Not sure
        if (button.getDialogString() != null) {
            player.getConnection().sendPacket(new SM_DIALOG_MENU_SELECT(button.getDialogString().value));
        }
        dialog.progress(choice);
    }

    public void onPlayerMove(final Player player) {
        final Dialog dialog = player.getController().getDialog();
        if (dialog == null) {
            return;
        }

        this.playerCloseDialog(player, dialog);
    }

    public final void sendDialogToPlayer(final Player player, final Dialog dialog) {
        player.getController().setDialog(dialog);
        player.getConnection().sendPacket(new SM_DIALOG(dialog));
    }

    private void playerCloseDialog(final Player player, final Dialog dialog) {
        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(new SM_DIALOG_CLOSE(dialog.getNpc()));
        connection.sendPacket(new SM_DIALOG_EVENT(dialog.getNpc(), player, dialog.getPage()));
        player.getController().setDialog(null);

        ObjectIDService.getInstance().releaseId(ObjectFamilyEnum.DIALOG, dialog.getUid());
    }

    /** SINGLETON */
    public static DialogService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final DialogService instance = new DialogService();
    }
}

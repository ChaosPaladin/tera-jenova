package com.angelis.tera.game.process.model.dialog;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.game.process.model.AbstractUniqueTeraModel;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.quest.Quest;

public class Dialog extends AbstractUniqueTeraModel {

    private final Player player;
    private final Npc npc;
    private final List<DialogButton> dialogButtons;
    private final int special1;
    private Quest quest;
    private int page = 1;
    
    private DialogButton lastClickedButton;

    public Dialog(final Player player, final Npc npc, final int special1) {
        super(null);
        this.player = player;
        this.npc = npc;
        this.special1 = special1;
        this.dialogButtons = new FastList<>();
    }

    public Dialog(final Player player, final Npc npc) {
        this(player, npc, 0);
    }

    public final void progress(final int choice) {
        if (choice > this.dialogButtons.size()) {
            return;
        }

        final DialogButton button = this.dialogButtons.get(choice - 1);
        if (button != null) {
            this.lastClickedButton = button;
            button.action();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Npc getNpc() {
        return npc;
    }

    public List<DialogButton> getDialogButtons() {
        return this.dialogButtons;
    }

    public void addDialogButton(final DialogButton dialogButton) {
        this.dialogButtons.add(dialogButton);
    }

    public void clearDialogButtons() {
        this.dialogButtons.clear();
    }

    public Quest getQuest() {
        return this.quest;
    }

    public void setQuest(final Quest quest) {
        this.quest = quest;
    }

    public int getSpecial1() {
        return this.special1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }
    
    public void addPage() {
        this.page++;
    }

    public DialogButton getLastClickedButton() {
        return lastClickedButton;
    }
}

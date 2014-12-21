package com.angelis.tera.game.process.model.dialog.actions;

import com.angelis.tera.game.process.model.dialog.AbstractDialogAction;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.StorageService;

public class ShowBankAction extends AbstractDialogAction {

    public ShowBankAction(final Player player, final Dialog dialog) {
        super(player, dialog);
    }

    @Override
    public void action() {
        StorageService.getInstance().onPlayerOpenStorage(player, StorageTypeEnum.PLAYER_WAREHOUSE);
    }
}

package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.command.AdminErrorMessageEnum;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.creature.Npc;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.services.QuestService;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.services.WorldService;


public class QuestCommand extends AbstractAdminCommand {
    
    private enum ActionEnum {
        START,
        CANCEL,
        REFRESH
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final Player targetPlayer = WorldService.getInstance().getOnlinePlayerByName(arguments[1]);
        if (targetPlayer == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_NOT_FOUND.key);
            return;
        }
        
        final Account targetAccount = targetPlayer.getAccount();
        if (targetAccount.getAccess() > connection.getAccount().getAccess()) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.TARGET_HAS_MORE_RIGHTS_THAN_YOU.key);
            return;
        }
        
        final int questId = Integer.parseInt(arguments[2]);
        final Quest quest = QuestService.getInstance().getQuestById(questId);
        if (quest == null) {
            this.sendTranslatedErrorMessage(connection, AdminErrorMessageEnum.QUEST_WITH_THIS_ID_NOT_FOUND.key);
            return;
        }
        
        final Npc startNpc = (Npc) SpawnService.getInstance().getCreatureByFullId(quest.getStartNpcFullId());
        final Player player = connection.getActivePlayer();
        switch (ActionEnum.valueOf(arguments[0].toUpperCase())) {
            case START:
                QuestService.getInstance().onPlayerStartQuest(player, startNpc, quest);
            break;
            
            case CANCEL:
                QuestService.getInstance().onPlayerCancelQuest(player, quest);
            break;
            
            case REFRESH:
                QuestService.getInstance().onPlayerEnterWorld(player);
            break;
        }
    }

    @Override
    public int getAccessLevel() {
        return 0;
    }

    @Override
    public int getArgumentCount() {
        return 3;
    }
    
    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            ActionEnum.valueOf(arguments[0].toUpperCase());
            Integer.parseInt(arguments[2]);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "quest [start|cancel|refresh] [playerName] [questId]";
    }
}

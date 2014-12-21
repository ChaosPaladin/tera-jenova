package com.angelis.tera.game.utils;

import com.angelis.tera.game.process.model.player.Player;

public class GroupUtils {

    public static boolean isInSameGroup(final Player player, final Player otherPlayer) {
        if (player.getGroup() == null || otherPlayer.getGroup() == null) {
            return false;
        }
        
        return player.getGroup().equals(otherPlayer.getGroup());
    }

}

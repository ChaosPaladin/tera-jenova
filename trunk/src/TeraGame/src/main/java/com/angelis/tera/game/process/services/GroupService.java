package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_SHOW_HP;
import com.angelis.tera.game.presentation.network.packet.server.SM_GROUP_ABNORMALS;
import com.angelis.tera.game.presentation.network.packet.server.SM_GROUP_LEADER_CHANGED;
import com.angelis.tera.game.presentation.network.packet.server.SM_GROUP_LEAVE;
import com.angelis.tera.game.presentation.network.packet.server.SM_GROUP_MEMBER_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_GROUP_MEMBER_MOVE;
import com.angelis.tera.game.presentation.network.packet.server.SM_GROUP_MEMBER_STATS;
import com.angelis.tera.game.process.model.creature.Creature;
import com.angelis.tera.game.process.model.group.Group;
import com.angelis.tera.game.process.model.group.enums.GroupVoteRequestEnum;
import com.angelis.tera.game.process.model.player.Player;

public class GroupService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DropService.class.getName());

    public static final int COMMON_GROUP_PACKET_VALUE = 14;
    
    @Override
    public void onInit() {
        log.info("GroupService started");
    }

    @Override
    public void onDestroy() {
        log.info("GroupService stopped");
    }

    public void onPlayerMove(final Player player) {
        if (player.getGroup() == null) {
            return;
        }
        
        player.getGroup().doOnEachPlayerExcept(new Function<Player>() {
            @Override
            public void call(final Player groupPlayer) {
                groupPlayer.getConnection().sendPacket(new SM_GROUP_MEMBER_MOVE(player));
            }
            
        }, player);
    }
    
    public void onGroupDestroy(final Group group) {
        group.doOnEachPlayer(new Function<Player>() {
            @Override
            public void call(final Player player) {
                player.getConnection().sendPacket(new SM_GROUP_LEAVE());
                player.getConnection().sendPacket(SystemMessages.GROUP_HAS_BEEN_DESTROYED());
                player.setGroup(null);
            }
        });
    }
    
    public void onPlayerRequestVote(final Player player, final GroupVoteRequestEnum groupVoteRequest, final int playerId) {
        final Group group = player.getGroup();
        if (group == null) {
            return;
        }
        
        if (group.size() == 2) {
            this.onGroupDestroy(group);
            return;
        }
        
        final Player targetPlayer = this.getGroupMemberById(group, playerId);
        if (targetPlayer == null) {
            return;
        }
        
        switch (groupVoteRequest) {
            case KICK:
                player.getConnection().sendPacket(SystemMessages.GROUP_CONFIRM_KICK(targetPlayer.getName()));
            break;
        }
    }

    public void onPlayerConfirmRequestVote(final Player player, final GroupVoteRequestEnum groupVoteRequest, final int playerId) {
        final Group group = player.getGroup();

        final Player targetPlayer = this.getGroupMemberById(group, playerId);
        if (targetPlayer == null) {
            return;
        }
        
        group.doOnEachPlayerExcept(new Function<Player>() {
            @Override
            public void call(final Player groupPlayer) {
                
            }
        }, player, targetPlayer);
    }
    
    public void onPlayerLeaveGroup(final Player player) {
        final Group group = player.getGroup();
        if (group == null) {
            return;
        }

        if (group.size() == 2) {
            this.onGroupDestroy(group);
            return;
        }

        player.getConnection().sendPacket(new SM_GROUP_LEAVE());
        group.removePlayer(player);
        group.doOnEachPlayerExcept(new Function<Player>() {
            @Override
            public void call(final Player groupPlayer) {
                groupPlayer.getConnection().sendPacket(new SM_GROUP_LEADER_CHANGED(group.getLeader()));
            }
        }, player);
    }
    
    public void onGroupUpdateExperience(final Group group, final Creature creature, final int totalExperience) {
        final int experience = totalExperience / group.size();
        final int bonusExperience = (group.size() > 2) ? experience * (group.size()-2)*10/100 : 0;

        group.doOnEachPlayer(new Function<Player>() {
            @Override
            public void call(final Player player) {
                PlayerService.getInstance().onPlayerUpdateExperience(player, creature, experience+bonusExperience);
            }
        });
    }
    
    public void onGroupRequestDestroy(final Player activePlayer) {
        // TODO Auto-generated method stub
        
    }
    
    public void onPlayerDie(final Player player) {
        player.getGroup().doOnEachPlayerExcept(new Function<Player>() {
            @Override
            public void call(final Player groupPlayer) {
                groupPlayer.getConnection().sendPacket(SystemMessages.GROUP_MEMBER_IS_DEAD(player.getName()));
                groupPlayer.getConnection().sendPacket(new SM_GROUP_MEMBER_STATS(player));
            }
        }, player);
    }
    
    public void onPlayerRevive(final Player player) {
        player.getGroup().doOnEachPlayerExcept(new Function<Player>() {
            @Override
            public void call(final Player groupPlayer) {
                groupPlayer.getConnection().sendPacket(SystemMessages.GROUP_MEMBER_HAS_REVIVE(player.getName()));
                groupPlayer.getConnection().sendPacket(new SM_GROUP_MEMBER_STATS(player));
            }
        }, player);
    }

    public void sendGroupMemberList(final Group group) {
        group.doOnEachPlayer(new Function<Player>() {
            @Override
            public void call(final Player player) {
                final TeraGameConnection connection = player.getConnection();
                connection.sendPacket(new SM_GROUP_MEMBER_LIST(group));
                for (final Player groupPlayer : group.getPlayers()) {
                    if (player.equals(groupPlayer)) {
                        continue;
                    }
                    
                    connection.sendPacket(new SM_CREATURE_SHOW_HP(groupPlayer, true));
                    connection.sendPacket(new SM_GROUP_MEMBER_STATS(groupPlayer));
                    connection.sendPacket(new SM_GROUP_ABNORMALS(groupPlayer));
                    connection.sendPacket(new SM_GROUP_MEMBER_MOVE(groupPlayer));
                }
            }
        });
    }
    
    private Player getGroupMemberById(final Group group, final int playerId) {
        Player player = null;
        for (final Player groupPlayer : group.getPlayers()) {
            if (groupPlayer.getId() == playerId) {
                player = groupPlayer;
                break;
            }
        }
        return player;
    }
    
    /** SINGLETON */
    public static GroupService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final GroupService instance = new GroupService();
    }
}

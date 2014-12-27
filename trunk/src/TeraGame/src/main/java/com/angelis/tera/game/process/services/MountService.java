package com.angelis.tera.game.process.services;

import java.util.Set;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.mounts.MountEntity;
import com.angelis.tera.game.domain.entity.xml.mounts.MountEntityHolder;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_MOUNT;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATS_UPDATE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_UNMOUNT;
import com.angelis.tera.game.process.model.mount.Mount;
import com.angelis.tera.game.process.model.player.Player;

public class MountService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(MountService.class.getName());

    private final Set<Mount> mounts = new FastSet<>();

    @Override
    public void onInit() {
        for (final MountEntity mountEntity : XMLService.getInstance().getEntity(MountEntityHolder.class).getMounts()) {
            final Mount mount = new Mount(mountEntity.getId());
            mount.setSkillId(mountEntity.getSkillId());
            mount.setSpeed(mountEntity.getSpeed());
            mounts.add(mount);
        }
        XMLService.getInstance().clearEntity(MountEntityHolder.class);

        log.info("MountService started");
    }

    @Override
    public void onDestroy() {
        mounts.clear();
        log.info("MountService stopped");
    }

    public void onPlayerMount(final Player player, final Mount mount) {
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PLAYER_MOUNT(player, mount));
        player.setActiveMount(mount);
        BaseStatService.getInstance().affectCreatureCurrentStats(player);
        BaseStatService.getInstance().affectCreatureBonusStats(player);
        player.getConnection().sendPacket(new SM_PLAYER_STATS_UPDATE(player));
    }

    public void onPlayerUnMount(final Player player) {
        if (player.getActiveMount() == null) {
            return;
        }

        VisibleService.getInstance().sendPacketForVisible(player, new SM_PLAYER_UNMOUNT(player, player.getActiveMount()));
        player.setActiveMount(null);
        BaseStatService.getInstance().affectCreatureCurrentStats(player);
        BaseStatService.getInstance().affectCreatureBonusStats(player);
        player.getConnection().sendPacket(new SM_PLAYER_STATS_UPDATE(player));
    }
    
    public void onPlayerDisconnect(final Player player) {
        this.onPlayerUnMount(player);
    }
    
    public void processMount(final Player player, final Mount mount) {
        if (player.getActiveMount() != null) {
            this.onPlayerUnMount(player);
        } else {
            this.onPlayerMount(player, mount);
        }
    }

    public Mount getMountByMountIdAndSkillId(final int mountId, final int skillId) {
        for (final Mount mount : this.mounts) {
            if (mount.getId() == mountId) {
                if (mount.getSkillId() == skillId) {
                    return mount;
                }
            }
        }
        return null;
    }
    
    public Mount getMountBySkillId(final int skillId) {
        for (final Mount mount : this.mounts) {
            if (mount.getSkillId() == skillId) {
                return mount;
            }
        }
        return null;
    }

    /** SINGLETON */
    public static MountService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final MountService instance = new MountService();
    }
}

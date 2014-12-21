package com.angelis.tera.game.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.enums.VisibleTypeEnum;

public class VisibleService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(VisibleService.class.getName());

    @Override
    public void onInit() {
        log.info("VisibleService started");
    }

    @Override
    public void onDestroy() {
        log.info("VisibleService stopped");
    }

    public void onPlayerDisconnect(final Player player) {
        player.getKnownList().clear();
    }

    public void onPlayerMove(final Player player) {
        player.getKnownList().update();
    }

    public void sendPacketForVisible(final VisibleTeraObject visibleTeraObject, final TeraServerPacket packet) {
        this.sendPacketForVisible(visibleTeraObject, packet, true);
    }
    
    public void sendPacketsForVisible(final VisibleTeraObject visibleTeraObject, final List<TeraServerPacket> packets) {
        for (final TeraServerPacket packet : packets) {
            this.sendPacketForVisible(visibleTeraObject, packet, true);
        }
    }

    public void sendPacketForVisible(final VisibleTeraObject visibleTeraObject, final TeraServerPacket packet, final boolean includeMe) {
        for (final VisibleTeraObject visible : visibleTeraObject.getKnownList().getVisible(VisibleTypeEnum.PLAYER)) {
            ((Player) visible).getConnection().sendPacket(packet);
        }

        if (includeMe && visibleTeraObject instanceof Player) {
            ((Player) visibleTeraObject).getConnection().sendPacket(packet);
        }
    }

    /** SINGLETON */
    public static VisibleService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final VisibleService instance = new VisibleService();
    }
}

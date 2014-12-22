package com.angelis.tera.game.process.services;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.domain.entity.xml.zones.ZoneEntity;
import com.angelis.tera.game.domain.entity.xml.zones.ZoneEntityHolder;
import com.angelis.tera.game.domain.mapper.xml.ZoneMapper;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_ZONE_CHANGE;
import com.angelis.tera.game.process.model.Zone;
import com.angelis.tera.game.process.model.player.Player;

public class ZoneService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ZoneService.class.getName());

    private final Map<String, Zone> zones = new FastMap<>();

    private ZoneService() {
    }

    @Override
    public void onInit() {
        final ZoneMapper zoneMapper = MapperManager.getXMLMapper(ZoneMapper.class);
        for (final ZoneEntity zoneEntity : XMLService.getInstance().getEntity(ZoneEntityHolder.class).getZones()) {
            zones.put(zoneEntity.getDatas(), zoneMapper.map(zoneEntity));
        }
        XMLService.getInstance().clearEntity(ZoneEntityHolder.class);

        log.info("ZoneService started");
    }

    @Override
    public void onDestroy() {
        zones.clear();
        log.info("ZoneService stopped");
    }

    public void onPlayerZoneChange(final Player player, final byte[] datas) {
        player.getVisitedZones().add(this.getZoneByData(datas));
        player.setCurrentZoneData(datas);
        player.getConnection().sendPacket(new SM_PLAYER_ZONE_CHANGE(datas));

        QuestService.getInstance().onPlayerZoneChange(player, datas);
    }

    public Zone getZoneByData(final byte[] datas) {
        final String datasStringifyed = PrintUtils.bytes2hex(datas);

        Zone zone = zones.get(datasStringifyed);
        if (zone == null) {
            log.error("Missing zone :"+datasStringifyed);

            // fake the zone
            zone = new Zone();
            zone.setDatas(datas);
        }

        return zone;
    }

    /** SINGLETON */
    public static ZoneService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ZoneService instance = new ZoneService();
    }
}

package com.angelis.tera.game.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.network.TeleportLocations;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_ZONE_CHANGE;
import com.angelis.tera.game.process.delegate.database.ZoneDelegate;
import com.angelis.tera.game.process.model.Zone;
import com.angelis.tera.game.process.model.channel.Channel;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerModeEnum;

public class WorldService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(WorldService.class.getName());

    /** DELEGATES */
    private final ZoneDelegate zoneDelegate = new ZoneDelegate();
    
    private final Map<String, Player> allPlayers = new FastMap<>();
    private final Map<String, Player> onlinePlayers = new FastMap<>();
    
    private final Map<Integer, Channel> channels = new FastMap<>();
    
    private final Map<Byte[], Zone> zones = new FastMap();

    @Override
    public void onInit() {
        log.info("WorldService started");
    }

    @Override
    public void onDestroy() {
    }

    public void onPlayerConnect(final Player player) {
        this.onlinePlayers.put(player.getName().toUpperCase(), player);
        this.allPlayers.put(player.getName().toUpperCase(), player);
        
        Channel channel = this.channels.get(player.getWorldPosition().getMapId());
        if (channel == null) {
            channel = new Channel(1);
            this.channels.put(player.getWorldPosition().getMapId(), channel);
        }
        
        player.setChannel(channel);
        channel.addPlayer(player);
    }
    
    public void onPlayerEnterWorld(final Player player) {
        QuestService.getInstance().onPlayerEnterWorld(player);
    }

    public void onPlayerDisconnect(final Player player) {
        this.onlinePlayers.remove(player);
        
        final Channel channel = this.channels.get(player.getWorldPosition().getMapId());
        if (channel == null) {
            return;
        }
        
        player.setChannel(null);
        channel.removePlayer(player);
    }
    
    public void onPlayerCreate(final Player player) {
        // TODO startZoneData for REAPER
        final byte[] startZoneData = new byte[] { 1, 0, 0, 0, 2, 0, 0, 0, 7, 0, 0, 0 };
        final Set<Zone> zones = new FastSet<>();
        
        player.setCreationTime(new Date());
        player.setDeletionTime(null);
        player.setLastOnlineTime(null);
        player.setPlayerMode(PlayerModeEnum.NORMAL);
        player.setCurrentZoneData(startZoneData);
        player.setVisitedZones(zones);
        player.setWorldPosition(TeleportLocations.getStandardStartingPoint());
        
        int level = 1;
        if (player.getPlayerClass() == PlayerClassEnum.REAPER) {
            level = 50;
            player.setWorldPosition(TeleportLocations.getReaperStartingPoint());
        } else {
            player.setWorldPosition(TeleportLocations.getStandardStartingPoint());
        }
        player.setLevel(level);
    }

    public void onPlayerZoneChange(final Player player, final byte[] datas) {
        player.getVisitedZones().add(this.getZoneByData(datas));
        player.setCurrentZoneData(datas);
        player.getConnection().sendPacket(new SM_PLAYER_ZONE_CHANGE(datas));
        
        QuestService.getInstance().onPlayerZoneChange(player, datas);
    }

    public Zone getZoneByData(final byte[] datas) {
        Zone zone = zones.get(datas);
        if (zone == null) {
            zone = zoneDelegate.findByDatas(datas);
            
            // TODO we should already have all possible zone datas in xml !
            // We must create the zone if we havent already it in database
            if (zone == null) {
                zone = new Zone();
                zone.setDatas(datas);

                zoneDelegate.create(zone);
            }

            // TODO
            //zones.put(datas, zone);
        }

        return zone;
    }
    
    public Player getOnlinePlayerByName(final String name) {
        return this.onlinePlayers.get(name.toUpperCase());
    }
    
    public Player getPlayerByName(final String name) {
        Player player = this.getOnlinePlayerByName(name);
        if (player == null) {
            player = this.allPlayers.get(name.toUpperCase());
        }
        return player;
    }

    public Set<TeraGameConnection> getAllOnlineConnections() {
        final Set<TeraGameConnection> allConnections = new FastSet<TeraGameConnection>();
        for (final Player player : this.onlinePlayers.values()) {
            allConnections.add(player.getConnection());
        }
        return allConnections;
    }

    public Collection<Player> getAllOnlinePlayers() {
        return this.onlinePlayers.values();
    }

    public List<Player> getPlayersByMap(final int mapId) {
        final List<Player> playersInMap = new FastList<>();

        for (final Player player : this.onlinePlayers.values()) {
            if (player.getWorldPosition().getMapId() == mapId) {
                playersInMap.add(player);
            }
        }

        return playersInMap;
    }

    public List<Player> getPlayersByArea(final int areaId) {
        final List<Player> playersInArea = new FastList<>();

        for (final Player player : this.onlinePlayers.values()) {
            if (this.getAreaByMapId(player.getWorldPosition().getMapId()) == areaId) {
                playersInArea.add(player);
            }
        }

        return playersInArea;
    }

    public int getAreaByMapId(final int mapId) {
        // TODO
        return 0;
    }

    public void doOnAllOnlinePlayer(final Function<Player> method) {
        for (final Player player : this.onlinePlayers.values()) {
            method.call(player);
        }
    }

    /** SINGLETON */
    public static WorldService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final WorldService instance = new WorldService();
    }
}

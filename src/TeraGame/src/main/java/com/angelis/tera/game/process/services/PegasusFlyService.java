package com.angelis.tera.game.process.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.common.utils.collection.CollectionUtils;
import com.angelis.tera.game.domain.entity.xml.pegasus.FlyEntity;
import com.angelis.tera.game.domain.entity.xml.pegasus.PegasusFliesEntityHolder;
import com.angelis.tera.game.domain.entity.xml.pegasus.PegasusFlyEntity;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.presentation.network.packet.server.SM_F2P_PREMIUM_USER_PERMISSION;
import com.angelis.tera.game.presentation.network.packet.server.SM_FESTIVAL_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_INVENTORY;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOAD_HINT;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOAD_TOPO;
import com.angelis.tera.game.presentation.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.presentation.network.packet.server.SM_PEGASUS_END;
import com.angelis.tera.game.presentation.network.packet.server.SM_PEGASUS_START;
import com.angelis.tera.game.presentation.network.packet.server.SM_PEGASUS_UPDATE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_GATHER_STATS;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATE;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_STATS_UPDATE;
import com.angelis.tera.game.process.model.pegasus.PegasusFly;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerModeEnum;
import com.angelis.tera.game.process.tasks.pegasus.StartPegasusFlyTask;

public class PegasusFlyService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(PegasusFlyService.class.getName());
    
    private final Map<Integer, List<PegasusFly>> pegasusFlies = new FastMap<>();
    
    private PegasusFlyService() {
    }
    
    @Override
    public void onInit() {
        final Set<PegasusFlyEntity> pegasusFlyEntities = XMLService.getInstance().getEntity(PegasusFliesEntityHolder.class).getPegasusFlies();
        for (final PegasusFlyEntity pegasusFlyEntity : pegasusFlyEntities) {
            final List<PegasusFly> pegasusFlies = new FastList<>();
            for (final FlyEntity fly : pegasusFlyEntity.getPegasusFlies()) {
                final PegasusFly pegasusFly = new PegasusFly(fly.getId());
                pegasusFly.setCost(fly.getCost());
                pegasusFly.setFromNameId(pegasusFlyEntity.getFromNameId());
                pegasusFly.setLevel(fly.getLevel());
                pegasusFly.setToNameId(fly.getToNameId());
                pegasusFly.setChangeMapTickCount(29); // TODO
                pegasusFly.setEndFlyTickCount(27); // TODO
                pegasusFlies.add(pegasusFly);
            }
            
            CollectionUtils.addAllToMapOfList(this.pegasusFlies, pegasusFlyEntity.getCreatureFullId(), pegasusFlies);
        }

        XMLService.getInstance().clearEntity(PegasusFliesEntityHolder.class);
        log.info("PegasusFlyService started");
    }

    @Override
    public void onDestroy() {
        log.info("PegasusFlyService stopped");
    }
    
    public void onPlayerStartPegasusFly(final Player player, final int flyId) {
        final TeraGameConnection connection = player.getConnection();
        
        player.setPlayerMode(PlayerModeEnum.FLYING);
        player.setActivePegasus(getPegasusFlyById(flyId));
        
        connection.sendPacket(new SM_INVENTORY(player, false));
        connection.sendPacket(new SM_PLAYER_GATHER_STATS(player.getGatherStats()));
        connection.sendPacket(new SM_F2P_PREMIUM_USER_PERMISSION());
        this.sendRequiredPackets(player);
        
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PEGASUS_START(player, 1));
        ThreadPoolService.getInstance().scheduleRepeatableTask(new StartPegasusFlyTask(player), 0, 3000, TimeUnit.MILLISECONDS);
    }
    
    public void onPlayerRunPegasusFly(final Player player, final int state, final int elapsedTime) {
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PEGASUS_UPDATE(player, player.getActivePegasus().getId(), state, elapsedTime));
    }
    
    public void onPlayerHalfPegasusFly(final Player player) {
        // TODO
        player.getWorldPosition().setMapId(1);
        player.getWorldPosition().setXYZ(-8893.452148f, -26884.871094f, 1482.114624f);
        
        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(new SM_PEGASUS_UPDATE(player, player.getActivePegasus().getId(), 2, 0));

        connection.sendPacket(new SM_OPCODE_LESS_PACKET("B78C"));
        connection.sendPacket(new SM_FESTIVAL_LIST());
        connection.sendPacket(new SM_LOAD_TOPO(player.getWorldPosition()));
        connection.sendPacket(new SM_LOAD_HINT());
        connection.sendPacket(new SM_INVENTORY(player, true));
    }
    
    public void onPlayerEndPegasusFly(final Player player) {
        player.setPlayerMode(PlayerModeEnum.NORMAL);
        player.getWorldPosition().setXYZ(1604.2233F, 3048.875F, 1743.7358F);
        
        this.sendRequiredPackets(player);
        VisibleService.getInstance().sendPacketForVisible(player, new SM_PEGASUS_END(player));
        player.setActivePegasus(null);
    }

    public List<PegasusFly> getPegasusFliesByCreatureFullId(final int creatureFullId) {
        return this.pegasusFlies.get(creatureFullId);
    }
    
    public PegasusFly getPegasusFlyById(final int id) {
        for (final List<PegasusFly> pegasusFlies : this.pegasusFlies.values()) {
            for (final PegasusFly fly : pegasusFlies) {
                if (fly.getId() == id) {
                    return fly;
                }
            }
        }
        return null;
    }
    
    private void sendRequiredPackets(final Player player) {
        final List<TeraServerPacket> packets = new FastList<>();
        packets.add(new SM_PLAYER_STATS_UPDATE(player));
        packets.add(new SM_PLAYER_STATE(player));
        VisibleService.getInstance().sendPacketsForVisible(player, packets);
    }
    
    /** SINGLETON */
    public static PegasusFlyService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final PegasusFlyService instance = new PegasusFlyService();
    }
}

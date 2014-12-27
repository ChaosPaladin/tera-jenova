package com.angelis.tera.game.process.services;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_CHANNEL_INFO;
import com.angelis.tera.game.presentation.network.packet.server.player.SM_PLAYER_CHANNEL_LIST;
import com.angelis.tera.game.process.model.channel.Channel;
import com.angelis.tera.game.process.model.player.Player;

public class ChannelService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ChannelService.class.getName());
    
    private final Map<Integer, List<Channel>> channels = new FastMap<>();
    
    @Override
    public void onInit() {
        log.info("ChannelService started");
    }

    @Override
    public void onDestroy() {
        log.info("ChannelService stopped");
    }

    public void onPlayerRequestChannelList(final Player player, final int channelId, final int mapId) {
        player.getConnection().sendPacket(new SM_PLAYER_CHANNEL_INFO(this.getChannelByChannelIdAndMapId(mapId, channelId), mapId));
        player.getConnection().sendPacket(new SM_PLAYER_CHANNEL_LIST(mapId, this.getChannelsByMapId(mapId)));
    }
    
    public Channel getChannelByChannelIdAndMapId(final int mapId, final int channelId) {
        final List<Channel> channels = this.getChannelsByMapId(mapId);
        for (final Channel channel : channels) {
            if (channel.getId() == channelId) {
                return channel;
            }
        }
        return null;
    }
    
    public List<Channel> getChannelsByMapId(final int mapId) {
        List<Channel> channels = this.channels.get(mapId);
        if (channels == null) {
            channels = new FastList<>();
            channels.add(new Channel(1));
            this.channels.put(mapId, channels);
        }
        
        return channels;
    }
    
    /** SINGLETON */
    public static ChannelService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ChannelService instance = new ChannelService();
    }
}

package com.angelis.tera.game.process.services;

import java.util.List;
import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.domain.entity.xml.tradelist.TradelistEntity;
import com.angelis.tera.game.domain.entity.xml.tradelist.TradelistEntityHolder;
import com.angelis.tera.game.domain.entity.xml.tradelist.TradelistTabEntity;
import com.angelis.tera.game.domain.entity.xml.tradelist.TradelistTabItemEntity;
import com.angelis.tera.game.process.model.tradelist.Tradelist;

public class TradelistService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(UserService.class.getName());
    
    private final Map<Integer, Tradelist> tradelists = new FastMap<>();
    
    @Override
    public void onInit() {
        final List<TradelistEntity> tradelists = XMLService.getInstance().getEntity(TradelistEntityHolder.class).getTradelists();
        for (final TradelistEntity tradelistEntity : tradelists) {
            final Tradelist tradelist = new Tradelist();
            tradelist.setCreatureFullId(tradelistEntity.getCreatureFullId());
            
            for (final TradelistTabEntity tab : tradelistEntity.getTabs()) {
                final List<Integer> tabItems = new FastList<>();
                for (final TradelistTabItemEntity item : tab.getItems()) {
                    tabItems.add(item.getId());
                }
                
                tradelist.getItemTabs().put(tab.getTradelistTabName(), tabItems);
            }
            this.tradelists.put(tradelistEntity.getCreatureFullId(), tradelist);
        }
        XMLService.getInstance().clearEntity(TradelistEntityHolder.class);
        log.info("TradelistService started");
    }

    @Override
    public void onDestroy() {
        log.info("TradelistService stopped");
    }

    public Tradelist getTradelistByCreatureFullId(final int creatureFullId) {
        return this.tradelists.get(creatureFullId);
    }
    
    /** SINGLETON */
    public static TradelistService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final TradelistService instance = new TradelistService();
    }
}

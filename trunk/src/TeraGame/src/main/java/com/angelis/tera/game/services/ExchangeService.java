package com.angelis.tera.game.services;

import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.network.packet.server.SM_EXCHANGE_WINDOW_UPDATE;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;
import com.angelis.tera.game.process.model.player.request.NpcTradeRequest;
import com.angelis.tera.game.process.model.storage.Storage;

public class ExchangeService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ExchangeService.class.getName());
    
    @Override
    public void onInit() {
        log.info("ExchangeService started");
    }

    @Override
    public void onDestroy() {
        log.info("ExchangeService stopped");
    }
    
    public void addBuyItem(final Player player, final int itemId, final int amount) {
        final AbstractRequest<?, ?> request = player.getController().getRequest();
        if (request == null || !(request instanceof NpcTradeRequest)) {
            return;
        }
        
        final NpcTradeRequest exchangeRequest = (NpcTradeRequest) request;
        exchangeRequest.addBuyItem(itemId, amount);
        
        player.getConnection().sendPacket(new SM_EXCHANGE_WINDOW_UPDATE(exchangeRequest));
    }
    
    public void removeBuyItem(final Player player, final int itemId, final int amount) {
        final AbstractRequest<?, ?> request = player.getController().getRequest();
        if (request == null || !(request instanceof NpcTradeRequest)) {
            return;
        }
        
        final NpcTradeRequest exchangeRequest = (NpcTradeRequest) request;
        exchangeRequest.removeBuyItem(itemId, amount);
        
        player.getConnection().sendPacket(new SM_EXCHANGE_WINDOW_UPDATE(exchangeRequest));
    }
    
    public void addSellItem(final Player player, final int itemId, final int amount, final int fromSlot) {
        final AbstractRequest<?, ?> request = player.getController().getRequest();
        if (request == null || !(request instanceof NpcTradeRequest)) {
            return;
        }
        
        final NpcTradeRequest exchangeRequest = (NpcTradeRequest) request;
        exchangeRequest.addSellItem(itemId, amount);
        
        player.getConnection().sendPacket(new SM_EXCHANGE_WINDOW_UPDATE(exchangeRequest));
    }
    
    public void removeSellItem(final Player player, final int itemId, final int amount) {
        final AbstractRequest<?, ?> request = player.getController().getRequest();
        if (request == null || !(request instanceof NpcTradeRequest)) {
            return;
        }
        
        final NpcTradeRequest exchangeRequest = (NpcTradeRequest) request;
        exchangeRequest.removeSellItem(itemId, amount);
        
        player.getConnection().sendPacket(new SM_EXCHANGE_WINDOW_UPDATE(exchangeRequest));
    }
    
    public void completeExchange(final Player player) {
        final AbstractRequest<?, ?> request = player.getController().getRequest();
        if (request == null || !(request instanceof NpcTradeRequest)) {
            return;
        }
        
        final NpcTradeRequest exchangeRequest = (NpcTradeRequest) request;
        final Storage inventory = player.getStorage(StorageTypeEnum.INVENTORY);
        for (final Entry<Integer, Integer> entry : exchangeRequest.getBuyItems().entrySet()) {
            StorageService.getInstance().addItem(player, inventory, entry.getKey(), entry.getValue());
        }
        for (final Entry<Integer, Integer> entry : exchangeRequest.getSellItems().entrySet()) {
            StorageService.getInstance().removeItem(player, inventory, entry.getKey(), entry.getValue());
        }
    }
    
    public void cancelExchange(final Player player) {
        final AbstractRequest<?, ?> request = player.getController().getRequest();
        if (request == null || !(request instanceof NpcTradeRequest)) {
            return;
        }
        
        final NpcTradeRequest exchangeRequest = (NpcTradeRequest) request;
        ObjectIDService.getInstance().releaseId(ObjectFamilyEnum.REQUEST, exchangeRequest.getUid());
        player.getController().setRequest(null);
    }
    
    /** SINGLETON */
    public static ExchangeService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ExchangeService instance = new ExchangeService();
    }
}

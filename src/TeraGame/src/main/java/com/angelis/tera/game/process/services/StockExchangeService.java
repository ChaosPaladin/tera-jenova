package com.angelis.tera.game.process.services;

import java.util.Date;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_ACCOUNT_ADD;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_ACCOUNT_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_HINT;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_INFO;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_UNIQUE_ADD;
import com.angelis.tera.game.presentation.network.packet.server.SM_STOCK_EXCHANGE_ITEM_UNIQUE_LIST;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.StockExchangeItem;
import com.angelis.tera.game.utils.DateUtils;

public class StockExchangeService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(StockExchangeService.class.getName());

    @Override
    public void onInit() {
        log.info("StockExchangeService started");
    }

    @Override
    public void onDestroy() {
        log.info("StockExchangeService stopped");
    }

    public void onStockExchangeItemUniqueList(final Player player) {
        player.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_UNIQUE_LIST(player.getStockExchangeItems()));
    }

    public void onStockExchangeItemAccountList(final Player player) {
        player.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_ACCOUNT_LIST(player.getAccount().getStockExchangeItems()));
    }

    public void onStockExchangeItemInfo(final Player player, final StockExchangeItem stockExchangeItem) {
        if (stockExchangeItem == null) {
            return;
        }
        player.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_INFO(stockExchangeItem));
    }

    public void onStockExchangeUniqueItemRequest(final Player player, final Date requestTimestamp, final StockExchangeItem stockExchangeItem) {
        if (requestTimestamp.after(stockExchangeItem.getExpirationDate())) {
            // TODO find the error to return to client
            return;
        }

        StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), stockExchangeItem.getItemId(), stockExchangeItem.getAmount());
        player.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_UNIQUE_ADD(stockExchangeItem));
    }

    public void onStockExchangeAccountItemRequest(final Player player, final int itemId, final StockExchangeItem stockExchangeItem) {
        if (stockExchangeItem.getItemId() != itemId) {
            // Cheat
            return;
        }

        StorageService.getInstance().addItem(player, player.getStorage(StorageTypeEnum.INVENTORY), itemId, stockExchangeItem.getAmount());
        player.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_ACCOUNT_ADD(stockExchangeItem));
    }

    public void onPlayerLoadTopoFin(final Player player) {
        if (!player.isLoadTopoFin()) { // check if we need this (avoid spam each time we change map)
            if (!player.getStockExchangeItems().isEmpty() || !player.getAccount().getStockExchangeItems().isEmpty()) {
                sendPlayerItemAwaiting(player);
            }
        }
    }

    // METHODS
    public void addPlayerAccountStockEchangeItem(final Account account, final int itemId, final int itemAmount) {
        final StockExchangeItem stockExchangeItem = new StockExchangeItem();
        stockExchangeItem.setItemId(itemId);
        stockExchangeItem.setAmount(itemAmount);
        fillStockExchangeItem(stockExchangeItem);
        account.getStockExchangeItems().add(stockExchangeItem);
        sendPlayerItemAwaiting(account.getConnection().getActivePlayer());
    }

    public void addPlayerUniqueStockEchangeItem(final Player player, final int itemId, final int itemAmount) {
        final StockExchangeItem stockExchangeItem = new StockExchangeItem();
        stockExchangeItem.setItemId(itemId);
        stockExchangeItem.setAmount(itemAmount);
        fillStockExchangeItem(stockExchangeItem);
        player.getStockExchangeItems().add(stockExchangeItem);
        sendPlayerItemAwaiting(player);
    }

    private void fillStockExchangeItem(final StockExchangeItem stockExchangeItem) {
        stockExchangeItem.setTitle("GIVE BY ADMIN");
        stockExchangeItem.setDescription("This item has been given by an admin. Thanks him");
        stockExchangeItem.setReceptionDate(new Date());
        stockExchangeItem.setExpirationDate(DateUtils.getInfiniteDate());
    }
    
    private void sendPlayerItemAwaiting(final Player player) {
        player.getConnection().sendPacket(SystemMessages.AN_ITEM_IS_AWAITING_IN_CLAME());
        player.getConnection().sendPacket(new SM_STOCK_EXCHANGE_ITEM_HINT());
    }

    /** SINGLETON */
    public static StockExchangeService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final StockExchangeService instance = new StockExchangeService();
    }
}

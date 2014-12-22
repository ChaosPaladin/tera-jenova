package com.angelis.tera.game.process.model.player.request;

import java.util.Map;

import javolution.util.FastMap;

import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.enums.RequestTypeEnum;

public class NpcTradeRequest extends AbstractRequest<AbstractCreature, Player> {

    private final Map<Integer, Integer> buyItems = new FastMap<>();
    private final Map<Integer, Integer> sellItems = new FastMap<>();

    public NpcTradeRequest(final AbstractCreature initiator, final Player target) {
        super(initiator, target, RequestTypeEnum.PLAYER_TRADE);
        target.getController().setRequest(this);
    }

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onAction() {
    }

    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
    }

    public void addBuyItem(final int itemId, final int itemAmount) {
        Integer oldItemAmount = this.buyItems.get(itemId);
        if (oldItemAmount == null) {
            oldItemAmount = 0;
        }

        oldItemAmount += itemAmount;
        this.buyItems.put(itemId, oldItemAmount);
    }

    public void removeBuyItem(final int itemId, final int amount) {
        Integer oldItemAmount = this.buyItems.get(itemId);
        if (oldItemAmount == null || oldItemAmount == 0) {
            return;
        }

        oldItemAmount -= amount;
        if (oldItemAmount < 0) {
            return;
        }

        this.buyItems.put(itemId, oldItemAmount);
        if (oldItemAmount == 0) {
            this.buyItems.remove(itemId);
        }
    }

    public void addSellItem(final int itemId, final int itemAmount) {
        Integer oldItemAmount = this.sellItems.get(itemId);
        if (oldItemAmount == null) {
            oldItemAmount = 0;
        }

        oldItemAmount += itemAmount;
        this.sellItems.put(itemId, oldItemAmount);
    }

    public void removeSellItem(final int itemId, final int amount) {
        Integer oldItemAmount = this.sellItems.get(itemId);
        if (oldItemAmount == null || oldItemAmount == 0) {
            return;
        }

        oldItemAmount -= amount;
        if (oldItemAmount < 0) {
            return;
        }

        this.sellItems.put(itemId, oldItemAmount);
        if (oldItemAmount == 0) {
            this.sellItems.remove(itemId);
        }
    }

    public Map<Integer, Integer> getBuyItems() {
        return buyItems;
    }

    public Map<Integer, Integer> getSellItems() {
        return sellItems;
    }
}

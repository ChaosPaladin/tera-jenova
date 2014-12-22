package com.angelis.tera.game.process.model.tradelist;

import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import com.angelis.tera.game.process.model.tradelist.enums.TradelistTabNameEnum;

public class Tradelist {

    private int creatureFullId;
    private final Map<TradelistTabNameEnum, List<Integer>> itemTabs = new FastMap<>();

    public int getCreatureFullId() {
        return creatureFullId;
    }

    public void setCreatureFullId(final int creatureFullId) {
        this.creatureFullId = creatureFullId;
    }

    public Map<TradelistTabNameEnum, List<Integer>> getItemTabs() {
        return itemTabs;
    }
}

package com.angelis.tera.game.domain.entity.database;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "player_stock_exchange_items")
public class StockExchangeItemEntity extends AbstractDatabaseEntity  {

    private static final long serialVersionUID = 1320445959855805944L;

}

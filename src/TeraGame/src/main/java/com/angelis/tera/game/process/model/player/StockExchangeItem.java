package com.angelis.tera.game.process.model.player;

import java.util.Date;

import com.angelis.tera.game.process.model.AbstractUniqueTeraModel;

public class StockExchangeItem extends AbstractUniqueTeraModel {

    private int itemId;
    private int amount;
    private String title;
    private String description;
    private Date receptionDate;
    private Date expirationDate;

    public StockExchangeItem() {
        super(null);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(final int itemId) {
        this.itemId = itemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(final Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

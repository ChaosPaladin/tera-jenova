package com.angelis.tera.game.process.model.account;

import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.process.model.BaseAccountModel;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.StockExchangeItem;

public class Account extends BaseAccountModel {

    private TeraGameConnection connection;
    private List<Player> players;
    private Options options;
    private List<StockExchangeItem> stockExchangeItems;

    public Account(final Integer id) {
        super(id);
    }

    public Account() {
        super(null);
    }

    public TeraGameConnection getConnection() {
        return connection;
    }

    public void setConnection(final TeraGameConnection connection) {
        this.connection = connection;
    }

    public List<Player> getPlayers() {
        if (players == null) {
            players = new FastList<>();
        }
        return players;
    }

    public Player getPlayerById(final int playerId) {
        for (final Player player : this.getPlayers()) {
            if (player.getId() == playerId) {
                return player;
            }
        }

        return null;
    }

    public void setPlayers(final List<Player> players) {
        this.players = players;
    }

    public void addPlayer(final Player player) {
        this.getPlayers().add(player);
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(final Options option) {
        this.options = option;
    }

    public List<StockExchangeItem> getStockExchangeItems() {
        return stockExchangeItems;
    }

    public void setStockExchangeItems(final List<StockExchangeItem> stockExchangeItems) {
        this.stockExchangeItems = stockExchangeItems;
    }

    public final boolean haveCharacterWithLevel(final int level) {
        for (final Player player : this.getPlayers()) {
            if (player.getLevel() >= level) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((connection == null) ? 0 : connection.hashCode());
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Account)) {
            return false;
        }
        final Account other = (Account) obj;
        if (connection == null) {
            if (other.connection != null) {
                return false;
            }
        }
        else if (!connection.equals(other.connection)) {
            return false;
        }
        if (players == null) {
            if (other.players != null) {
                return false;
            }
        }
        else if (!players.equals(other.players)) {
            return false;
        }
        return true;
    }
}

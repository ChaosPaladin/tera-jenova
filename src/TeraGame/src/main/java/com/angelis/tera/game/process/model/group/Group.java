package com.angelis.tera.game.process.model.group;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.process.model.player.Player;

public final class Group {

    private final List<Player> players = new FastList<>();
    private Player leader;

    public final void addPlayer(final Player player) {
        if (this.players.contains(player)) {
            return;
        }

        if (this.players.isEmpty()) {
            this.leader = player;
        }
        this.players.add(player);
    }

    public void removePlayer(final Player player) {
        this.players.remove(player);
        if (this.players.size() > 1) {
            if (player.equals(this.leader)) {
                this.leader = this.players.get(0);
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getPlayersWithoutLeader() {
        final List<Player> players = new FastList<>();
        for (final Player player : this.players) {
            if (!this.getLeader().equals(player)) {
                players.add(player);
            }
        }
        return players;
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(final Player leader) {
        this.leader = leader;
    }

    public final int size() {
        return this.players.size();
    }

    public void doOnEachPlayer(final Function<Player> function) {
        this.doOnEachPlayerExcept(function, new FastList<Player>(0));
    }

    public void doOnEachPlayerExcept(final Function<Player> function, final Player... exceptions) {
        this.doOnEachPlayerExcept(function, Arrays.asList(exceptions));
    }

    public void doOnEachPlayerExcept(final Function<Player> function, final Collection<Player> exceptions) {
        for (final Player player : this.players) {
            if (!player.isOnline() || exceptions.contains(player)) {
                continue;
            }

            function.call(player);
        }
    }

    public final int getLowestLevel() {
        int lowestLevel = 60;
        for (final Player player : this.players) {
            if (player.getLevel() < lowestLevel) {
                lowestLevel = player.getLevel();
            }
        }

        return lowestLevel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        final Group other = (Group) obj;
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

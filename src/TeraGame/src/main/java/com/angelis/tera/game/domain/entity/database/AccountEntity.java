package com.angelis.tera.game.domain.entity.database;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.angelis.tera.common.domain.entity.database.BaseAccountEntity;

@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class AccountEntity extends BaseAccountEntity {

    private static final long serialVersionUID = -4034792019245322102L;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
    private Set<PlayerEntity> players;

    public AccountEntity(final Integer id) {
        super(id);
    }

    public AccountEntity() {
        super();
    }

    public Set<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(final Set<PlayerEntity> players) {
        this.players = players;
    }

    public void addPlayer(final PlayerEntity player) {
        this.getPlayers().add(player);
        player.setAccount(this);
    }
}

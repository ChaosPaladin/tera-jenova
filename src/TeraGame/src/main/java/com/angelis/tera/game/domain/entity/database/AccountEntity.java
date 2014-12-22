package com.angelis.tera.game.domain.entity.database;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.BaseAccountEntity;
import com.angelis.tera.game.process.model.account.enums.AccountTypeEnum;
import com.angelis.tera.game.process.model.account.enums.DisplayRangeEnum;

@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseAccountEntity {

    private static final long serialVersionUID = -4034792019245322102L;

    private int extraCharacterSlotCount;
    
    @Column(name = "displayRange")
    private DisplayRangeEnum displayRange;

    @Column(name = "accountType")
    private AccountTypeEnum accountType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
    private Set<PlayerEntity> players;

    public AccountEntity(final Integer id) {
        super(id);
    }

    public AccountEntity() {
        super();
    }

    public int getExtraCharacterSlotCount() {
        return extraCharacterSlotCount;
    }

    public void setExtraCharacterSlotCount(final int extraCharacterSlotCount) {
        this.extraCharacterSlotCount = extraCharacterSlotCount;
    }

    public DisplayRangeEnum getDisplayRange() {
        return displayRange;
    }

    public void setDisplayRange(final DisplayRangeEnum displayRange) {
        this.displayRange = displayRange;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(final AccountTypeEnum accountType) {
        this.accountType = accountType;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((accountType == null) ? 0 : accountType.hashCode());
        result = prime * result + ((displayRange == null) ? 0 : displayRange.hashCode());
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccountEntity)) {
            return false;
        }
        final AccountEntity other = (AccountEntity) obj;
        if (accountType != other.accountType) {
            return false;
        }
        if (displayRange != other.displayRange) {
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

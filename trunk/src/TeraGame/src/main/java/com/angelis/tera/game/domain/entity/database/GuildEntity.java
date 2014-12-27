package com.angelis.tera.game.domain.entity.database;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;

@Entity
@Table(name = "guilds")
public class GuildEntity extends AbstractDatabaseEntity {

    private static final long serialVersionUID = -1375100480239094846L;

    @Column()
    private String name;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<StorageEntity> storages;
    
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<StorageEntity> getStorages() {
        return storages;
    }

    public void setStorages(final Set<StorageEntity> storages) {
        this.storages = storages;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((storages == null) ? 0 : storages.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GuildEntity)) {
            return false;
        }
        final GuildEntity other = (GuildEntity) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (storages == null) {
            if (other.storages != null) {
                return false;
            }
        }
        else if (!storages.equals(other.storages)) {
            return false;
        }
        return true;
    }
}

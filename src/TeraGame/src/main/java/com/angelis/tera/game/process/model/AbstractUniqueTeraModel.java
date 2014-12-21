package com.angelis.tera.game.process.model;

import com.angelis.tera.common.process.model.HasUid;
import com.angelis.tera.game.process.services.ObjectIDService;

public class AbstractUniqueTeraModel extends AbstractTeraModel implements HasUid {

    private final int uid;
    
    public AbstractUniqueTeraModel(final Integer id) {
        super(id);
        this.uid = ObjectIDService.getInstance().registerObject(this);
    }

    @Override
    public final int getUid() {
        return this.uid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = prime * uid;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractUniqueTeraModel)) {
            return false;
        }
        final AbstractUniqueTeraModel other = (AbstractUniqueTeraModel) obj;
        if (uid != other.uid) {
            return false;
        }
        return true;
    }
}

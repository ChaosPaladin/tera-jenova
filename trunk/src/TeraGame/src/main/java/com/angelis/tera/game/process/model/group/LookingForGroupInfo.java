package com.angelis.tera.game.process.model.group;

import com.angelis.tera.game.process.model.AbstractUniqueTeraModel;

public class LookingForGroupInfo extends AbstractUniqueTeraModel {

    private final Group group;
    
    public LookingForGroupInfo(final Group group) {
        super(null);
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

}

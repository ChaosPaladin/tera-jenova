package com.angelis.tera.game.process.model;

import com.angelis.tera.game.process.controllers.Controller;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;

public abstract class TeraObject extends AbstractUniqueTeraModel {
    
    protected final Controller<? extends TeraObject> controller;
    
    public TeraObject(final Integer id, final Controller<? extends TeraObject> controller) {
        super(id);
        this.controller = controller;
    }
    
    public TeraObject(final VisibleTeraObject visibleTeraObject, final Controller<? extends TeraObject> controller) {
        super(visibleTeraObject.getId());
        this.controller = controller;
    }

    public abstract Controller<? extends TeraObject> getController();
}

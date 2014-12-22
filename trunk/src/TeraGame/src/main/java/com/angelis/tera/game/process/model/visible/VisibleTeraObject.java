package com.angelis.tera.game.process.model.visible;

import com.angelis.tera.game.ai.AI;
import com.angelis.tera.game.process.controllers.Controller;
import com.angelis.tera.game.process.model.TeraObject;
import com.angelis.tera.game.process.model.template.Template;

public abstract class VisibleTeraObject extends TeraObject {
    
    private final KnownList knownList;
    private WorldPosition worldPosition;
    protected AI<? extends VisibleTeraObject> ai;
    protected final Template template;

    public VisibleTeraObject(final Integer id, final Controller<? extends VisibleTeraObject> controller, final Template template) {
        super(id, controller);
        this.knownList = new KnownList(this);
        this.template = template;
    }

    public VisibleTeraObject(final VisibleTeraObject visibleTeraObject, final Controller<? extends VisibleTeraObject> controller, final Template template) {
        super(visibleTeraObject, controller);
        this.knownList = new KnownList(this);
        this.template = template;
    }

    public KnownList getKnownList() {
        return knownList;
    }
    
    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(final WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public AI<? extends VisibleTeraObject> getAi() {
        return ai;
    }
    
    public Template getTemplate() {
        return this.template;
    }
    
    public abstract void initializeAi();
}

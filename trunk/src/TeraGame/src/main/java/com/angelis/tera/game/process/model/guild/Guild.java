package com.angelis.tera.game.process.model.guild;

import com.angelis.tera.game.controllers.GuildController;
import com.angelis.tera.game.process.model.TeraObject;


public class Guild extends TeraObject {

    private String name;
    
    public Guild(final Integer id) {
        super(id, new GuildController());
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public GuildController getController() {
        return (GuildController) this.controller;
    }
}

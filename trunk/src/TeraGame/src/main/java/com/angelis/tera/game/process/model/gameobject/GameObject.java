package com.angelis.tera.game.process.model.gameobject;

import com.angelis.tera.game.process.controllers.GameObjectController;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class GameObject extends VisibleTeraObject {
    
    private Player owner;
    private WorldPosition worldPosition;
    private String data;

    public GameObject(final Integer id, final Player owner) {
        super(id, new GameObjectController(), null);
        this.owner = owner;
    }
    
    public Player getOwner() {
        return owner;
    }

    public void setOwner(final Player owner) {
        this.owner = owner;
    }

    @Override
    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

    @Override
    public void setWorldPosition(final WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    @Override
    public GameObjectController getController() {
        return (GameObjectController) this.controller;
    }

    @Override
    public void initializeAi() {
        // Empty
    }
}

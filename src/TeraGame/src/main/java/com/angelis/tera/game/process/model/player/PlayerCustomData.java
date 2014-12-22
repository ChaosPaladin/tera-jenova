package com.angelis.tera.game.process.model.player;

import java.util.Map;

import javolution.util.FastMap;

import com.angelis.tera.game.process.model.visible.WorldPosition;

public class PlayerCustomData {
    private final Map<String, WorldPosition> recalls = new FastMap<>();
    private float size = 1.0F;

    public Map<String, WorldPosition> getRecalls() {
        return recalls;
    }
    
    public float getSize() {
        return size;
    }

    public void setSize(final float size) {
        this.size = size;
    }
}

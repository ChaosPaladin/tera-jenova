package com.angelis.tera.game.network;

import com.angelis.tera.game.process.model.visible.WorldPosition;

public class TeleportLocations {
    
    public static WorldPosition getStandardStartingPoint() {
        return new WorldPosition(13, 93492.742188F, -88216.640625F, -4523.007324F, (short) -32767);
    }
    
    public static WorldPosition getReaperStartingPoint() {
        return new WorldPosition(7087, -48077.00F, -52002.00F, 642.00F, (short) 45330);
    }
}
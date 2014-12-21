package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.services.PlayerService;

public class GoToCommand extends AbstractAdminCommand {
    
    private enum Location {
        ISLANDOFDAWN(13, 93492.742188F, -88216.640625F, -4523.007324F),
        VELIKA(1, 1604.2233F, 3048.875F, 1743.7358F),
        LUMBERTOWN(7001, 91838.16F, -2433.5671F, 713.78705F),
        PORAELINU(7002, -43190.56F, 33669.105F, 1864.0157F),
        
        ALLEMANTHEIA(2, -4598.4497F, -9970.233F, 6352.685F),
        CRESCENTIA(7001, 95599.8F, 17994.027F, 2946.0F),
        POPOLION(7002, -16638.12F, 50258.9F, 3003.0F),
        
        ASHENHOPE(7087, -48077.00F, -52002.00F, 642.00F),
        
        DRAGONFORT(9123, -99973.414F, -83918.734F, 4637.0F),
        // TODO
        ;
        
        public final int mapId;
        public final float x;
        public final float y;
        public final float z;
        
        private Location(final int mapId, final float x, final float y, final float z) {
            this.mapId = mapId;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        if (connection.getActivePlayer().getActivePegasus() != null) {
            this.sendErrorMessage(connection, "error");
            return;
        }
        
        final Location location = Location.valueOf(arguments[0].toUpperCase());
        PlayerService.getInstance().teleportPlayer(connection.getActivePlayer(), location.mapId, location.x, location.y, location.z);
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        try {
            Location.valueOf(arguments[0].toUpperCase());
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "goto {ISLANDOFDAWN | VELIKA | LUMBERTOWN | PORAELINU | ALLEMANTHEIA, | ASHENHOPE | DRAGONFORT}";
    }
}

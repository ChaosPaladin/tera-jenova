package com.angelis.tera.game.command.admin;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.server.SM_MOVIE_PLAY;

public class MovieCommand extends AbstractAdminCommand {

    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        connection.sendPacket(new SM_MOVIE_PLAY(Integer.parseInt(arguments[0].toUpperCase())));
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
            Integer.parseInt(arguments[0].toUpperCase());
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    @Override
    public String getSyntax() {
        return "movie {movieId}";
    }
}

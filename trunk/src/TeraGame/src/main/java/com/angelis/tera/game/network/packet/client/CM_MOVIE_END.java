package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.services.MovieService;

public class CM_MOVIE_END extends TeraClientPacket {

    private int movieId;
    private boolean skipped;

    public CM_MOVIE_END(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.movieId = readD();
        this.skipped = readC() == 1;
    }

    @Override
    protected void runImpl() {
        MovieService.getInstance().onPlayerMovieStop(this.getConnection().getActivePlayer(), this.movieId, this.skipped);
    }
}

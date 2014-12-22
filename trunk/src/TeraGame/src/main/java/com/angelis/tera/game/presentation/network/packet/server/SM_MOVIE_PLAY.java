package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_MOVIE_PLAY extends TeraServerPacket {

    private final int movieId;

    public SM_MOVIE_PLAY(final int movieId) {
        this.movieId = movieId;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, this.movieId);
    }
}

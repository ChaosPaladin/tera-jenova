package com.angelis.tera.game.presentation.network.packet.client.guild;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.GuildService;

public class CM_GUILD_APPLICATION extends TeraClientPacket {

    public CM_GUILD_APPLICATION(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        GuildService.getInstance().onPlayerRequestGuildApplication(this.getConnection().getActivePlayer());
    }

}

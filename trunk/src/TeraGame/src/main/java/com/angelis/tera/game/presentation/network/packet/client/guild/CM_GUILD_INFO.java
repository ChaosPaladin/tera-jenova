package com.angelis.tera.game.presentation.network.packet.client.guild;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.GuildService;

public class CM_GUILD_INFO extends TeraClientPacket {

    public CM_GUILD_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // 01000000
    }

    @Override
    protected void runImpl() {
        GuildService.getInstance().onPlayerRequestGuildInfo(this.getConnection().getActivePlayer());
    }

}

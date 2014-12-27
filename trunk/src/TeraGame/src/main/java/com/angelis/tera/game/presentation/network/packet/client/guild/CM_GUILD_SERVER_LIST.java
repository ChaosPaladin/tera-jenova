package com.angelis.tera.game.presentation.network.packet.client.guild;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.GuildService;

public class CM_GUILD_SERVER_LIST extends TeraClientPacket {

    public CM_GUILD_SERVER_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readD(); // 0x00000006
    }

    @Override
    protected void runImpl() {
        GuildService.getInstance().onPlayerRequestGuildServerList(this.getConnection().getActivePlayer());
    }

}

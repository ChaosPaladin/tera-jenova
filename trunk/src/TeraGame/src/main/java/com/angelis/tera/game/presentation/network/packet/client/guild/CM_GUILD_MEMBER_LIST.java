package com.angelis.tera.game.presentation.network.packet.client.guild;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.guild.SM_GUILD_MEMBER_LIST;

public class CM_GUILD_MEMBER_LIST extends TeraClientPacket {

    public CM_GUILD_MEMBER_LIST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // Empty packet
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_GUILD_MEMBER_LIST());
    }
}

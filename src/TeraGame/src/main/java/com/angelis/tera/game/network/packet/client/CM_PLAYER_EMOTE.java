package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.player.enums.EmoteEnum;
import com.angelis.tera.game.services.PlayerService;

public class CM_PLAYER_EMOTE extends TeraClientPacket {

    private EmoteEnum emotion;
    private int duration;
    
    public CM_PLAYER_EMOTE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.emotion = EmoteEnum.fromValue(readD());
        this.duration = readC();
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().onPlayerEmote(this.getConnection().getActivePlayer(), this.emotion, this.duration);
    }
}

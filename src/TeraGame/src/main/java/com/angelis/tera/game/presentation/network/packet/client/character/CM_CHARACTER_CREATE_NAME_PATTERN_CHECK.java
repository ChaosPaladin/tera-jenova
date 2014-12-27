package com.angelis.tera.game.presentation.network.packet.client.character;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.character.SM_CHARACTER_CREATE_NAME_PATTERN_CHECK;
import com.angelis.tera.game.process.model.enums.CheckNameTypeEnum;
import com.angelis.tera.game.process.services.PlayerService;

public class CM_CHARACTER_CREATE_NAME_PATTERN_CHECK extends TeraClientPacket {

    private CheckNameTypeEnum type;
    private String name;
    
    public CM_CHARACTER_CREATE_NAME_PATTERN_CHECK(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        readB(14);
        this.type = CheckNameTypeEnum.fromCode(readH());
        readH();
        name = readS();
    }

    @Override
    protected void runImpl() {
        this.getConnection().sendPacket(new SM_CHARACTER_CREATE_NAME_PATTERN_CHECK(type, PlayerService.getInstance().checkNamePattern(type, name), name));
    }
}

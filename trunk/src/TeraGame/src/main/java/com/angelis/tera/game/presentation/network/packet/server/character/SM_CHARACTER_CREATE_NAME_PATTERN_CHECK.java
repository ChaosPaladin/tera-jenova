package com.angelis.tera.game.presentation.network.packet.server.character;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.enums.CheckCharacterNameResponse;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.enums.CheckNameTypeEnum;

public class SM_CHARACTER_CREATE_NAME_PATTERN_CHECK extends TeraServerPacket {

    private final CheckNameTypeEnum type;
    private final CheckCharacterNameResponse response;
    private final String name;
    
    public SM_CHARACTER_CREATE_NAME_PATTERN_CHECK(final CheckNameTypeEnum type, final CheckCharacterNameResponse response, final String name) {
        this.type = type;
        this.response = response;
        this.name = name;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeB(byteBuffer, "01000800080000001600");
        writeD(byteBuffer, this.type.code);
        writeD(byteBuffer, this.response.value);
        writeS(byteBuffer, this.name);
    }
}

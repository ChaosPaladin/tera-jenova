package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.gameobject.GameObject;

public class SM_GAMEOBJECT_DESPAWN extends TeraServerPacket{

    private final GameObject gameObject;
    
    public SM_GAMEOBJECT_DESPAWN(final GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeUid(byteBuffer, this.gameObject);
    }

}

package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_GAMEOBJECT_SPAWN extends TeraServerPacket {

    private final GameObject gameObject;
    
    public SM_GAMEOBJECT_SPAWN(final GameObject gameObject) {
        this.gameObject = gameObject;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final WorldPosition worldPosition = this.gameObject.getWorldPosition();

        final int ownerNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int dataShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeUid(byteBuffer, this.gameObject);
        writeD(byteBuffer, 3); // probably object type
        
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeH(byteBuffer, worldPosition.getHeading());
        writeC(byteBuffer, 0);
        
        this.writeBufferPosition(byteBuffer, ownerNameShift);
        writeS(byteBuffer, this.gameObject.getOwner().getName());
        
        this.writeBufferPosition(byteBuffer, dataShift);
        writeS(byteBuffer, this.gameObject.getData());
    }

}

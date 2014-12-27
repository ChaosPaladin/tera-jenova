package com.angelis.tera.game.presentation.network.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.log4j.Logger;

import com.angelis.tera.common.presentation.network.crypt.CryptSession;
import com.angelis.tera.common.presentation.network.packet.AbstractServerPacket;
import com.angelis.tera.common.process.model.HasUid;
import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public abstract class TeraServerPacket extends AbstractServerPacket<TeraGameConnection> {

    /** LOGGER */
    private static Logger log = Logger.getLogger(TeraServerPacket.class.getName());
    
    private Short opcode;
    
    protected Integer bufferSavePosition;
    
    public TeraServerPacket() {}
    
    @Override
    public void write(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // Packet length reservation
        writePacketLength(byteBuffer, (short)0);
        byteBuffer.position(2);
        
        // Opcode
        writeOpcode(byteBuffer);
        
        // Implementation of this packet
        this.writeImpl(connection, byteBuffer);
        byteBuffer.flip();
        
        // Packet length
        final int length = byteBuffer.limit();
        writePacketLength(byteBuffer, (short) length);
        
        if (this.showInDebug()) {
            log.info(PrintUtils.toHex(byteBuffer));
        }
        
        this.completeByteBuffer(connection, byteBuffer, length);
    }
    
    public void writePacketLength(final ByteBuffer byteBuffer, final short length) {
        byteBuffer.putShort(0, length);
    }
    
    public void writeOpcode(final ByteBuffer byteBuffer) {
        writeH(byteBuffer, this.opcode);
    }
    
    protected final void writeUid(final ByteBuffer byteBuffer, final HasUid uid) {
        if (uid == null) {
            byteBuffer.putLong(0L);
            return;
        }

        byteBuffer.putInt(uid.getUid());
        byteBuffer.put(PrintUtils.hex2bytes(ObjectFamilyEnum.fromClass(uid.getClass()).value));
    }
    
    protected final void writeWorldPosition(final ByteBuffer byteBuffer, final WorldPosition worldPosition) {
        this.writeF(byteBuffer, worldPosition != null ? worldPosition.getX() : 0);
        this.writeF(byteBuffer, worldPosition != null ? worldPosition.getY() : 0);
        this.writeF(byteBuffer, worldPosition != null ? worldPosition.getZ() : 0);
        this.writeH(byteBuffer, worldPosition != null ? worldPosition.getHeading() : 0);
    }
    
    protected final void writeBufferPosition(final ByteBuffer byteBuffer, final int newPosition) {
        final int position = byteBuffer.position();
        
        byteBuffer.position(newPosition);
        writeH(byteBuffer, position);
        byteBuffer.position(position);
    }
    
    protected final void writeBufferPosition(final ByteBuffer byteBuffer, final int newPosition, final int overrideValue) {
        final int position = byteBuffer.position();
        writeH(byteBuffer, overrideValue);
        byteBuffer.position(position);
    }
    
    public void completeByteBuffer(final TeraGameConnection connection, final ByteBuffer byteBuffer, final int length) {
        // Reset packet
        final ByteBuffer b = (ByteBuffer) byteBuffer.slice().limit(length);
        b.order(ByteOrder.LITTLE_ENDIAN);
        
        // Crypt
        final CryptSession session = connection.getCryptSession(); 
        if (session.isCryptEnabled()) {
            session.encrypt(b);
        }
    }
    
    @Override
    public final void setOpcode(final Short opcode) {
        if (this.opcode == null) {
            this.opcode = opcode;
        }
    }
    
    @Override
    public boolean showInDebug() {
        return false;
    }
}

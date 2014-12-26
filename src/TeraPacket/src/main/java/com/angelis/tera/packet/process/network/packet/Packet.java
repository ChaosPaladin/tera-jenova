package com.angelis.tera.packet.process.network.packet;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.packet.process.network.packet.enums.PacketDirectionEnum;

public class Packet {
    private final short opcode;
    private final PacketDirectionEnum packetDirection;
    private final byte[] datas;

    public Packet(final short opcode, final PacketDirectionEnum packetDirection, final byte[] datas) {
        this.opcode = opcode;
        this.packetDirection = packetDirection;
        this.datas = datas;
    }

    public short getOpcode() {
        return opcode;
    }

    public PacketDirectionEnum getPacketDirection() {
        return packetDirection;
    }

    public byte[] getDatas() {
        return datas;
    }
    
    public String toDebugString() {
        final StringBuilder sb = new StringBuilder();
        switch (this.packetDirection) {
            case CLIENT_TO_SERVER:
                sb.append("C->S");
            break;

            case SERVER_TO_CLIENT:
                sb.append("S->C");
            break;
        }

        sb.append(" ["+String.format("0x%02X", this.opcode)+"] ["+this.datas.length+"]");
        sb.append(System.getProperty("line.separator"));
        sb.append(PrintUtils.toHex(this.datas));
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));
        
        sb.append("Hex:");
        sb.append(System.getProperty("line.separator"));
        sb.append(PrintUtils.toLineHex(this.datas));
        sb.append(System.getProperty("line.separator"));
        sb.append(System.getProperty("line.separator"));

        return sb.toString();
    }
    
    @Override
    public String toString() {
        return PrintUtils.toHex(this.datas);
    }
}

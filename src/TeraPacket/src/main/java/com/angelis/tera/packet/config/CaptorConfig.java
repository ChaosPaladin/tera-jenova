package com.angelis.tera.packet.config;

import com.angelis.tera.common.config.Property;
import com.angelis.tera.packet.process.network.packet.handlers.AbstractPacketHandler;

public class CaptorConfig {
    @Property(key="captor.packet.handlers", defaultValue="")
    public static Class<? extends AbstractPacketHandler>[] CAPTOR_PACKET_HANDLERS;
}

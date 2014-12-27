package com.angelis.tera.packet.process.network.packet.handlers;

import org.apache.log4j.Logger;

import com.angelis.tera.packet.process.network.packet.Packet;

public abstract class AbstractPacketHandler {

    /** LOGGER */
    protected static Logger log = Logger.getLogger(AbstractPacketHandler.class.getName());
    
    public abstract void handle(final Packet packet);
}

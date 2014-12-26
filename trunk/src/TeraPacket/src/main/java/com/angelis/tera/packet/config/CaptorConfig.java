package com.angelis.tera.packet.config;

import com.angelis.tera.common.config.Property;
import com.angelis.tera.packet.process.captors.AbstractCaptor;

public class CaptorConfig {
    @Property(key="captor.class", defaultValue="")
    public static Class<? extends AbstractCaptor> CAPTOR_CLASS;
}

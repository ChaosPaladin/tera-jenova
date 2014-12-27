package com.angelis.tera.packet.di.injector;

import com.angelis.tera.packet.process.network.packet.protocols.IProtocol;
import com.angelis.tera.packet.process.network.packet.protocols.impl.TeraProtocol;
import com.google.inject.AbstractModule;

public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(IProtocol.class).to(TeraProtocol.class);
    }

}

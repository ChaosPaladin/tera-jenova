package com.angelis.tera.packet;

import com.angelis.tera.common.utils.PrintUtils;
import com.angelis.tera.packet.process.services.ConfigService;
import com.angelis.tera.packet.process.services.NetworkService;

public class MainPacket {
    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();

        PrintUtils.printSection("Starting services");
        ConfigService.getInstance().start();
        NetworkService.getInstance().start();
        
        PrintUtils.printSection("Launching");
        System.out.println("Server started in " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
    }
}

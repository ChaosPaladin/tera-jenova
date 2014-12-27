package com.angelis.tera.packet.process.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacketHandler;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.packet.config.NetworkConfig;
import com.angelis.tera.packet.di.injector.AppInjector;
import com.angelis.tera.packet.process.network.Captor;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class NetworkService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(NetworkService.class.getName());

    private NetworkService() {
        System.loadLibrary("/natives/jnetpcap");

        final List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        final StringBuilder errbuf = new StringBuilder(); // For any error msgs

        /***************************************************************************
         * First get a list of devices on this system
         **************************************************************************/
        final int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r != Pcap.OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
            return;
        }

        final PcapIf device = alldevs.get(0);

        final int snaplen = 131072*8; // Capture all packets, no trucation
        final int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        final int timeout = 1;
        final Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

        if (pcap == null) {
            System.err.printf("Error while opening device for capture: " + errbuf.toString());
            return;
        }

        final PcapBpfProgram program = new PcapBpfProgram();
        final String expression = "host " + NetworkConfig.NETWORK_IP;
        final int optimize = 1;
        final int netmask = 0;

        if (pcap.compile(program, expression, optimize, netmask) != Pcap.OK || pcap.setFilter(program) != Pcap.OK) {
            System.err.println(pcap.getErr());
            return;
        }

        final Injector injector = Guice.createInjector(new AppInjector());
        final PcapPacketHandler<String> jpacketHandler = injector.getInstance(Captor.class);

        new Thread() {
            @Override
            public void run() {
                log.info("Listening on " + NetworkConfig.NETWORK_IP);
                pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, null);
            }
        }.start();
    }

    @Override
    public void onInit() {
        log.info("NetworkService started");
    }

    @Override
    public void onDestroy() {
        log.info("NetworkService stopped");
    }

    /** SINGLETON */
    public static NetworkService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final NetworkService instance = new NetworkService();
    }
}

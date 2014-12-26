package com.angelis.tera.login.http;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class ServerRunner {

    public static void executeInstance(final NanoHTTPD server) {
        new Thread() {
            @Override
            public void run() {
                try {
                    server.start();
                } catch (final IOException ioe) {
                    System.exit(-1);
                }

                try {
                    System.in.read();
                } catch (final Throwable ignored) {
                }

                server.stop();
            }
        }.start();
    }
}

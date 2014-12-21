package com.angelis.tera.launcher.presentation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.angelis.tera.common.utils.Function;

public class HTTPUtils {

    public static final void doGet(final URL url, final Function<InputStream> callback, final String... parameters) {
        try {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            
            callback.call(connection.getInputStream());
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}

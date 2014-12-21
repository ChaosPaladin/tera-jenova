package com.angelis.tera.common.services;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BaseI18nService extends AbstractService {

    public String translate(final Locale locale, final String key, final Object... args) {
        String message = null;
        try {
            final ResourceBundle messages = ResourceBundle.getBundle(getBundleName(), locale);
            message = messages.getString(key);
        } catch (final Exception e) {
        }
        
        return message;
    }

    public abstract String getBundleName();
}

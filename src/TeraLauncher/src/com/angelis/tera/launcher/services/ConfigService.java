package com.angelis.tera.launcher.services;

import java.util.Properties;

import com.angelis.tera.common.config.ConfigurableProcessor;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.PropertiesUtils;
import com.angelis.tera.launcher.config.FileConfig;
import com.angelis.tera.launcher.config.LoginConfig;

public class ConfigService extends AbstractService {

    private ConfigService() {
    }

    @Override
    public void onInit() {
        try {
            final Properties[] properties = PropertiesUtils.loadAllFromDirectory("conf");
            ConfigurableProcessor.process(LoginConfig.class, properties);
            ConfigurableProcessor.process(FileConfig.class, properties);
        }
        catch (final Exception e) {
            throw new Error("Can't load gameserver configurations", e);
        }
    }

    @Override
    public void onDestroy() {
    }

    /** SINGLETON */
    public static ConfigService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ConfigService instance = new ConfigService();
    }
}

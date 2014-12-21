package com.angelis.tera.login.services;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.angelis.tera.common.config.ConfigurableProcessor;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.PropertiesUtils;
import com.angelis.tera.login.config.NetworkConfig;

public class ConfigService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ConfigService.class.getName());

    private ConfigService() {
    }

    @Override
    public void onInit() {
        PropertyConfigurator.configure("./conf/log4j.properties");

        try {
            final Properties[] properties = PropertiesUtils.loadAllFromDirectory("conf");
            ConfigurableProcessor.process(NetworkConfig.class, properties);
        }
        catch (final Exception e) {
            log.fatal("Can't load loginserver configurations", e);
            throw new Error("Can't load loginserver configurations", e);
        }
        log.info("ConfigService started");
    }

    @Override
    public void onDestroy() {
        log.info("ConfigService stopped");
    }

    /** SINGLETON */
    public static ConfigService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ConfigService instance = new ConfigService();
    }
}

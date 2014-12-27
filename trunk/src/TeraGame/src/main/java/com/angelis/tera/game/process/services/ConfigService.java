package com.angelis.tera.game.process.services;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.angelis.tera.common.config.ConfigurableProcessor;
import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.common.utils.PropertiesUtils;
import com.angelis.tera.game.config.AccountConfig;
import com.angelis.tera.game.config.AdminConfig;
import com.angelis.tera.game.config.DropConfig;
import com.angelis.tera.game.config.GlobalConfig;
import com.angelis.tera.game.config.NetworkConfig;
import com.angelis.tera.game.config.PlayerConfig;
import com.angelis.tera.game.config.QuestConfig;
import com.angelis.tera.game.config.SpawnConfig;
import com.angelis.tera.game.config.UserConfig;

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
            ConfigurableProcessor.process(AccountConfig.class, properties);
            ConfigurableProcessor.process(AdminConfig.class, properties);
            ConfigurableProcessor.process(GlobalConfig.class, properties);
            ConfigurableProcessor.process(NetworkConfig.class, properties);
            ConfigurableProcessor.process(SpawnConfig.class, properties);
            ConfigurableProcessor.process(PlayerConfig.class, properties);
            ConfigurableProcessor.process(UserConfig.class, properties);
            ConfigurableProcessor.process(DropConfig.class, properties);
            ConfigurableProcessor.process(QuestConfig.class, properties);
        }
        catch (final Exception e) {
            log.fatal("Can't load gameserver configurations", e);
            throw new Error("Can't load gameserver configurations", e);
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

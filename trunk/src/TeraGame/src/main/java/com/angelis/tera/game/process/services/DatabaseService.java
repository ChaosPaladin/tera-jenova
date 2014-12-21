package com.angelis.tera.game.process.services;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.domain.entity.database.AccountEntity;
import com.angelis.tera.game.domain.entity.database.CraftEntity;
import com.angelis.tera.game.domain.entity.database.GatherEntity;
import com.angelis.tera.game.domain.entity.database.GuildEntity;
import com.angelis.tera.game.domain.entity.database.PlayerEntity;
import com.angelis.tera.game.domain.entity.database.PlayerRelationEntity;
import com.angelis.tera.game.domain.entity.database.QuestEntity;
import com.angelis.tera.game.domain.entity.database.SkillEntity;
import com.angelis.tera.game.domain.entity.database.StorageEntity;
import com.angelis.tera.game.domain.entity.database.StorageItemEntity;
import com.angelis.tera.game.domain.entity.database.ZoneEntity;

public class DatabaseService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DatabaseService.class.getName());

    private AnnotationConfiguration annotationConfiguration;
    private SessionFactory sessionFactory;

    private DatabaseService() {
        try {
            this.annotationConfiguration = new AnnotationConfiguration().configure(new File("conf/hibernate.cfg.xml"))
                .addPackage("com.angelis.game.database.entity")
                .addPackage("com.angelis.common.database.entity");

            this.annotationConfiguration
                .addAnnotatedClass(AccountEntity.class)
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(PlayerRelationEntity.class)
                .addAnnotatedClass(StorageEntity.class)
                .addAnnotatedClass(GuildEntity.class)
                .addAnnotatedClass(StorageItemEntity.class)
                .addAnnotatedClass(CraftEntity.class)
                .addAnnotatedClass(GatherEntity.class)
                .addAnnotatedClass(SkillEntity.class)
                .addAnnotatedClass(QuestEntity.class)
                .addAnnotatedClass(ZoneEntity.class)
            ;

            this.sessionFactory = this.annotationConfiguration.buildSessionFactory();
        }
        catch (final Throwable ex) {
            System.err.println("Initial SessionFactory creation failed : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void onInit() {
        log.info("DatabaseService started");
    }

    @Override
    public void onDestroy() {
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /** SINGLETON */
    public static DatabaseService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final DatabaseService instance = new DatabaseService();
    }
}

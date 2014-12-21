package com.angelis.tera.login.services;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.login.domain.entity.database.AccountEntity;
import com.angelis.tera.login.domain.entity.database.ServerEntity;

public class DatabaseService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(DatabaseService.class.getName());

    private AnnotationConfiguration annotationConfiguration;
    private SessionFactory sessionFactory;

    private DatabaseService() {
        try {
            this.annotationConfiguration = new AnnotationConfiguration().configure(new File("conf/hibernate.cfg.xml")).addPackage("com.angelis.login.database.entity");

            this.annotationConfiguration
                .addAnnotatedClass(AccountEntity.class)
                .addAnnotatedClass(ServerEntity.class)
            ;

            this.sessionFactory = this.annotationConfiguration.buildSessionFactory();
        }
        catch (final Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
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

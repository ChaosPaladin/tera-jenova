package com.angelis.tera.common.domain.dao;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.dao.database.AbstractDatabaseDAO;
import com.angelis.tera.common.domain.dao.database.exception.DAONotFoundException;
import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;


public class DAOManager {
    
    /** LOGGER */
    private static final Logger log = Logger.getLogger(DAOManager.class.getName());
    
    /** Collection of registered DAOs */
    private static final Map<String, AbstractDatabaseDAO<? extends AbstractDatabaseEntity>> databaseDAOMap = new FastMap<String, AbstractDatabaseDAO<? extends AbstractDatabaseEntity>>();
    
    private DAOManager() {
        // empty
    }

    
    public static <T extends AbstractDatabaseDAO<? extends AbstractDatabaseEntity>> T getDatabaseDAO(final Class<T> clazz) {
        DAOManager.ensureDatabaseDAO(clazz);
        
        @SuppressWarnings("unchecked")
        final
        T result = (T) databaseDAOMap.get(clazz.getName());
        
        if (result == null) {
            throw new DAONotFoundException("DAO Not found "+clazz.getName());
        }
        
        return result;
    }
    
    private static final <T extends AbstractDatabaseDAO<? extends AbstractDatabaseEntity>> void ensureDatabaseDAO(final Class<T> clazz) {
        final AbstractDatabaseDAO<? extends AbstractDatabaseEntity> result = databaseDAOMap.get(clazz.getName());
        if (result == null) {
            try {
                databaseDAOMap.put(clazz.getName(), clazz.newInstance());
                log.info("Initiated DatabaseDAO: "+clazz.getName());
            }
            catch (final Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}

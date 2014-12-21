package com.angelis.tera.common.domain.mapper;

import java.util.Map;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.domain.dao.database.exception.DAONotFoundException;
import com.angelis.tera.common.domain.mapper.database.AbstractDatabaseMapper;
import com.angelis.tera.common.domain.mapper.xml.AbstractXMLMapper;

public class MapperManager {
    
    /** LOGGER */
    private static Logger log = Logger.getLogger(MapperManager.class.getName());
    
    /** Collection of registered DAOs */
    private static final Map<String, AbstractDatabaseMapper<?,?>> databaseMappersMap = new FastMap<>();
    private static final Map<String, AbstractXMLMapper<?,?>> xmlMappersMap = new FastMap<>();
    
    private MapperManager() {
        // empty
    }
    
    public static <T extends AbstractDatabaseMapper<?,?>> T getDatabaseMapper(final Class<T> clazz) {
        MapperManager.ensureDatabaseMapper(clazz);
        
        @SuppressWarnings("unchecked")
        final
        T result = (T) databaseMappersMap.get(clazz.getName());
        
        if (result == null) {
            throw new DAONotFoundException("Mapper Not found "+clazz.getName());
        }
        
        return result;
    }
    
    public static <T extends AbstractXMLMapper<?,?>> T getXMLMapper(final Class<T> clazz) {
        MapperManager.ensureXMLMapper(clazz);
        
        @SuppressWarnings("unchecked")
        final
        T result = (T) xmlMappersMap.get(clazz.getName());
        
        if (result == null) {
            throw new DAONotFoundException("Mapper Not found "+clazz.getName());
        }
        
        return result;
    }

    private static final <T extends AbstractDatabaseMapper<?,?>> void ensureDatabaseMapper(final Class<T> clazz) {
        final AbstractDatabaseMapper<?,?> result = databaseMappersMap.get(clazz.getName());
        if (result == null) {
            try {
                databaseMappersMap.put(clazz.getName(), clazz.newInstance());
                log.debug("Initiated DatabaseMapper: "+clazz.getName());
            }
            catch (final Exception e) {}
        }
    }
    
    private static final <T extends AbstractXMLMapper<?,?>> void ensureXMLMapper(final Class<T> clazz) {
        final AbstractXMLMapper<?,?> result = xmlMappersMap.get(clazz.getName());
        if (result == null) {
            try {
                xmlMappersMap.put(clazz.getName(), clazz.newInstance());
                log.debug("Initiated XMLMapper: "+clazz.getName());
            }
            catch (final Exception e) {}
        }
    }
}

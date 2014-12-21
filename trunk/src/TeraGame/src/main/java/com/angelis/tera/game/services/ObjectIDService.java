package com.angelis.tera.game.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.model.HasUid;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;

public class ObjectIDService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(ObjectIDService.class.getName());

    private final Map<ObjectFamilyEnum, AtomicInteger> generators = Collections.synchronizedMap(new FastMap<ObjectFamilyEnum, AtomicInteger>());
    private final Map<ObjectFamilyEnum, Map<Integer, HasUid>> uids = Collections.synchronizedMap(new FastMap<ObjectFamilyEnum, Map<Integer, HasUid>>());

    @Override
    public void onInit() {
        log.info("ObjectIDService started");
    }

    @Override
    public void onDestroy() {
        log.info("ObjectIDService stopped");
    }

    public final int registerObject(final HasUid object) {
        final ObjectFamilyEnum objectFamily = ObjectFamilyEnum.fromClass(object.getClass());
        if (objectFamily == null) {
            throw new RuntimeException();
        }
        
        AtomicInteger generator = this.generators.get(objectFamily);
        if (generator == null) {
            generator = new AtomicInteger(1);
            this.generators.put(objectFamily, generator);
        }

        final int uid = generator.getAndIncrement();
        Map<Integer, HasUid> registered = this.uids.get(objectFamily);
        if (registered == null) {
            registered = new FastMap<>();
            this.uids.put(objectFamily, registered);
        }
        
        registered.put(uid, object);
        return uid;
    }

    public void releaseId(final ObjectFamilyEnum objectFamilly, final Integer uid) {
        final Map<Integer, HasUid> registered = this.uids.get(objectFamilly);
        if (registered != null) {
            this.uids.remove(uid);
        }
    }
    
    public <O extends HasUid> O getObjectByUId(final ObjectFamilyEnum objectFamily, final Integer uid) {
        final Map<Integer, HasUid> registered = this.uids.get(objectFamily);
        if (registered == null) {
            return null;
        }
        
        return (O) registered.get(uid);
    }
    
    public <H extends HasUid> Collection<H> getObjectsByFamily(final ObjectFamilyEnum objectFamily) {
        final Map<Integer, HasUid> registered = this.uids.get(objectFamily);
        if (registered == null) {
            return null;
        }
        
        return (Collection<H>) registered.values();
    }

    /** SINGLETON */
    public static ObjectIDService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final ObjectIDService instance = new ObjectIDService();
    }
}

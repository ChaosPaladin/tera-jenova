package com.angelis.tera.common.utils;

import java.util.Map;

import javolution.util.FastMap;

public abstract class HasCache<K, E> {

    private final Map<K, E> cache = new FastMap<K, E>(0);
    
    public void storeInCache(K key, E element) {
        this.cache.put(key,  element);
    }
    
    public E getFromCache(K key) {
        return this.cache.get(key);
    }
}

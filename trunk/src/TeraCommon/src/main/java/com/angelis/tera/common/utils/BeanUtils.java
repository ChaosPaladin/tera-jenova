package com.angelis.tera.common.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

public class BeanUtils {

    private static final Map<Class<?>, PropertyDescriptor[]> CACHE = new FastMap<>();
    
    public static final <T> void fill(final Class<T> type, final T source, final T destination) {
        BeanUtils.fill(type, source, destination, null);
    }
    
    public static final <T> void fill(final Class<T> type, final T source, final T destination, final List<String> excludedProperty) {
        try {
            final BeanInfo info = Introspector.getBeanInfo(type);
            final PropertyDescriptor[] propertyDescriptors = checkCache(type, info);
            for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final String name = propertyDescriptor.getName();
                if ("class".equals(name) || (excludedProperty != null && excludedProperty.contains(name))) {
                    continue;
                }
                
                final Method getter = propertyDescriptor.getReadMethod();
                if (getter == null) {
                    continue;
                }

                final Method setter = propertyDescriptor.getWriteMethod();
                if (setter == null) {
                    continue;
                }
                
                setter.invoke(destination, getter.invoke(source));
            }
        }
        catch (final IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }  
    }

    private static final <T> PropertyDescriptor[] checkCache(final Class<T> type, final BeanInfo info) {
        PropertyDescriptor[] propertyDescriptors = CACHE.get(type);
        if (propertyDescriptors == null) {
            propertyDescriptors = info.getPropertyDescriptors();
            CACHE.put(type, propertyDescriptors);
        }
        return propertyDescriptors;
    }
}

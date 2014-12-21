package com.angelis.tera.common.config.transformers;

import java.lang.reflect.Field;

import com.angelis.tera.common.config.PropertyTransformer;
import com.angelis.tera.common.config.exception.TransformationException;

public class ClassTransformer implements PropertyTransformer<Class<?>> {
    /**
     * Shared instance.
     */
    public static final ClassTransformer SHARED_INSTANCE = new ClassTransformer();

    public Class<?> transform(String value, Field field) throws TransformationException {
        try {
            return Class.forName(value, false, getClass().getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw new TransformationException("Cannot find class with name '" + value + "'");
        }
    }
}

package com.angelis.tera.common.config.transformers;

import java.lang.reflect.Field;
import java.util.List;

import javolution.util.FastList;

import com.angelis.tera.common.config.PropertyTransformer;
import com.angelis.tera.common.config.exception.TransformationException;

public class ClassArrayTransformer implements PropertyTransformer<Class<?>[]> {

    /**
     * Shared instance. 
     */
    public static final ClassArrayTransformer SHARED_INSTANCE = new ClassArrayTransformer();
    
    @Override
    public Class<?>[] transform(final String value, final Field field) throws TransformationException {
        final String[] classNames = value.split(",");
        final List<Class<?>> classes = new FastList<>();
        for (final String className : classNames) {
            try {
                classes.add(Class.forName(className, false, getClass().getClassLoader()));
            }
            catch (final ClassNotFoundException e) {
                throw new TransformationException("Cannot find class with name '" + className + "'");
            }
        }
        return classes.toArray(new Class<?>[classes.size()]);
    }
}

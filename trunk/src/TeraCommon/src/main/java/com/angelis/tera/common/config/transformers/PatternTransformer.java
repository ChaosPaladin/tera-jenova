package com.angelis.tera.common.config.transformers;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import com.angelis.tera.common.config.PropertyTransformer;
import com.angelis.tera.common.config.exception.TransformationException;

public class PatternTransformer implements PropertyTransformer<Pattern> {
    /**
     * Shared instance of this transformer
     */
    public static final PatternTransformer SHARED_INSTANCE = new PatternTransformer();

    /**
     * Transforms String to Pattern object
     *
     * @param value value that will be transformed
     * @param field value will be assigned to this field
     * @return Pattern Object
     * @throws TransformationException if pattern is not valid
     */
    public Pattern transform(String value, Field field) throws TransformationException {
        try {
            return Pattern.compile(value);
        }
        catch (Exception e) {
            throw new TransformationException("Not valid RegExp: " + value, e);
        }
    }
}


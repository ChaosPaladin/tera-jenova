package com.angelis.tera.login.process.exceptions;

public class MissingRequiredFieldException extends Exception {

    private static final long serialVersionUID = 2544427872823778158L;
    
    private final String[] fieldNames;

    public MissingRequiredFieldException(final String... fieldNames) {
        this.fieldNames = fieldNames;
    }
    
    @Override
    public String getMessage() {
        final StringBuilder sb = new StringBuilder();
        for (final String fieldName : fieldNames) {
            sb.append(fieldName);
        }

        return "Missing required fields : "+sb.toString();
    }
}

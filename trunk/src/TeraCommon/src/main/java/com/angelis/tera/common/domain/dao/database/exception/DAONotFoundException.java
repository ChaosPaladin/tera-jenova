package com.angelis.tera.common.domain.dao.database.exception;

public class DAONotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6835686733723787299L;
    
    public DAONotFoundException(String message) {
        super(message);
    }
}

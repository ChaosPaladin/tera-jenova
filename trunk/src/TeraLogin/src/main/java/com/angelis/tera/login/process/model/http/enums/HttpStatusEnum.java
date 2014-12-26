package com.angelis.tera.login.process.model.http.enums;

public enum HttpStatusEnum {
    OK(200),
    FORBIDDEN(403),
    NOT_FOUND(404),
    ;
    
    public final int code;
    
    HttpStatusEnum(final int code) {
        this.code = code;
    }
}

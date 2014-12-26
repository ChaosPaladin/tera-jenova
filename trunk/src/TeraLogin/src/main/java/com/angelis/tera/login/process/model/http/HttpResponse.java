package com.angelis.tera.login.process.model.http;

import com.angelis.tera.login.process.model.http.enums.HttpStatusEnum;

public class HttpResponse {
    private final StringBuilder contentBuilder = new StringBuilder();
    private HttpStatusEnum httpStatus;
    private String mimeType = "text/html";

    public String getContent() {
        return this.contentBuilder.toString();
    }
    
    public void appendContent(final String content) {
        this.contentBuilder.append(content);
    }
    
    public HttpStatusEnum getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(final HttpStatusEnum httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }
}

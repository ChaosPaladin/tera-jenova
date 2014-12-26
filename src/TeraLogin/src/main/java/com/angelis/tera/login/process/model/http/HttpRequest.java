package com.angelis.tera.login.process.model.http;

import com.angelis.tera.login.process.model.session.Session;
import com.angelis.tera.login.process.model.session.SessionManager;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Method;


public class HttpRequest {
    
    private final IHTTPSession httpSession;
    
    public HttpRequest(final IHTTPSession httpSession) {
        this.httpSession = httpSession;
    }

    public Session getSession() {
        return SessionManager.getSession(httpSession);
    }

    public String getUri() {
        return httpSession.getUri();
    }
    
    public boolean isPost() {
        return httpSession.getMethod() == Method.POST;
    }

    public String getParameter(final String parameterKey) {
        return httpSession.getParms().get(parameterKey);
    }
    
    public String getQueryString() {
        return httpSession.getQueryParameterString();
    }
}

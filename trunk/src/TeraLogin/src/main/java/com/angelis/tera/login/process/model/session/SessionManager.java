package com.angelis.tera.login.process.model.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.angelis.tera.common.utils.Rnd;

import fi.iki.elonen.NanoHTTPD.CookieHandler;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;

public class SessionManager {
    private final static String SESSION_COOKIE_STRING = "HTTP_SESSION";
    private final static Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<String, Session>());
    
    public static Session getSession(final IHTTPSession httpSession) {
        final CookieHandler cookieHandler = httpSession.getCookies();
        Session session = SessionManager.sessions.get(cookieHandler.read(SESSION_COOKIE_STRING));
        if (session == null) {
            final String uniqueId = Rnd.nextString(15);
            cookieHandler.set(SESSION_COOKIE_STRING, uniqueId, (int) ((System.currentTimeMillis()/1000)+3600));
            session = new Session();
            
            sessions.put(uniqueId, session);
        }
        return session;
    }
}

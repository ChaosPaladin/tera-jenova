package com.angelis.tera.login.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.angelis.tera.common.network.AbstractHttpServer;
import com.angelis.tera.common.network.config.ServerConfig;
import com.angelis.tera.common.process.exceptions.AccountNotFoundException;
import com.angelis.tera.common.process.exceptions.AccountWithIncorrectPasswordException;
import com.angelis.tera.login.config.AccountConfig;
import com.angelis.tera.login.domain.entity.xml.ServerListEntity;
import com.angelis.tera.login.process.dto.Server;
import com.angelis.tera.login.process.exceptions.MissingRequiredFieldException;
import com.angelis.tera.login.process.model.Account;
import com.angelis.tera.login.process.model.http.HttpRequest;
import com.angelis.tera.login.process.model.http.HttpResponse;
import com.angelis.tera.login.process.model.http.enums.HttpStatusEnum;
import com.angelis.tera.login.process.model.session.Session;
import com.angelis.tera.login.process.services.AccountService;
import com.angelis.tera.login.process.services.ServerService;
import com.angelis.tera.login.utils.xml.XMLUtils;
import com.angelis.tera.login.utils.xml.XmlCharacterHandler;

public class TeraLoginServer extends AbstractHttpServer {

    public TeraLoginServer(final ServerConfig serverConfig) throws IOException {
        super(serverConfig);
    }

    @Override
    public Response serve(final IHTTPSession httpSession) {
        final HttpRequest httpRequest = new HttpRequest(httpSession);
        final Session session = httpRequest.getSession();

        final HttpResponse httpResponse = new HttpResponse();
        
        switch (httpRequest.getUri()) {
            case "/servers.fr":
            case "/servers.en":
            case "/servers.de":
            case "/servers.es":
                doServers(httpRequest, httpResponse);
            break;

            case "/login":
                if (!session.isLoggedIn()) {
                    doLogin(httpRequest, httpResponse);
                } else {
                    doRestricted(httpRequest, httpResponse);
                }
            break;

            case "/register":
                if (!session.isLoggedIn()) {
                    doRegister(httpRequest, httpResponse);
                } else {
                    doRestricted(httpRequest, httpResponse);
                }
            break;
            
            case "/welcome":
                doWelcome(httpRequest, httpResponse);
            break;

            default:
                if (httpRequest.getUri().startsWith("/admin")) {
                    if (session.isAdmin()) {
                        doAdmin(httpRequest, httpResponse);
                    } else {
                        doRestricted(httpRequest, httpResponse);
                    }
                } else {
                    doDefault(httpRequest, httpResponse);
                }
        }
        
        // Build response
        final Response response = new Response(httpResponse.getContent());
        response.setMimeType(httpResponse.getMimeType());
        return response;
    }

    private void doWelcome(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.appendContent("This will contains news");
    }

    private void doAdmin(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        switch (httpRequest.getUri()) {
            case "/admin/server/create":
                final String name = httpRequest.getParameter("name");
                final String port = httpRequest.getParameter("port");
                final String ip = httpRequest.getParameter("ip");

                try {
                    ServerService.getInstance().createServer(name, port, ip);
                }
                catch (final MissingRequiredFieldException e) {
                    doRestricted(httpRequest, httpResponse);
                    return;
                }
                
                httpResponse.appendContent("Server created with success");
            break;
        }
    }

    private void doServers(final HttpRequest httpRequest, final HttpResponse response) {
        /*final String uri = httpRequest.getUri();
        final String lang = uri.substring(uri.length()-2);*/

        final List<Server> servers = ServerService.getInstance().getAll();
        if (servers == null || servers.isEmpty()) {
            return;
        }

        // We should have a dedicated mapper for this
        final ServerListEntity serverListEntity = XMLUtils.convertServers(servers);
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(ServerListEntity.class);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty("com.sun.xml.bind.characterEscapeHandler", new XmlCharacterHandler());
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            
            final StringWriter stringWriter = new StringWriter();
            stringWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            final PrintWriter printWriter = new PrintWriter(stringWriter);
            marshaller.marshal(serverListEntity, printWriter);
            response.appendContent(stringWriter.toString());
            response.setMimeType("text/xml");
        }
        catch (final JAXBException e) {
            e.printStackTrace();
        }
    }

    private void doLogin(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.setMimeType("application/json");

        final String login = httpRequest.getParameter("login");
        final String password = httpRequest.getParameter("password");

        Account account = null;
        try {
            account = AccountService.getInstance().getAccountByLoginAndPassword(login, password);
        }
        catch (final MissingRequiredFieldException e) {
            httpResponse.appendContent("{result: \"error\", message: \"need login and password param\"}");
            return;
        }
        catch (final AccountNotFoundException e) {
            if (AccountConfig.ACCOUNT_CREATE_IF_LOGIN_NOT_FOUND) {
                doRegister(httpRequest, httpResponse);
            } else {
                httpResponse.appendContent("{result: \"error\", message: \"user not found\"}");
            }
            return;
        }
        catch (final AccountWithIncorrectPasswordException e) {
            httpResponse.appendContent("{result: \"error\", message: \"wrong password\"}");
            return;
        }

        account.setAuthenticated(true);
        AccountService.getInstance().updateAccount(account);
        
        httpRequest.getSession().setAccount(account);
        httpResponse.appendContent("{result: \"success\"}");
    }

    private void doRegister(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.setMimeType("application/json");
        
        final String login = httpRequest.getParameter("login");
        final String password = httpRequest.getParameter("password");
        final String locale = httpRequest.getParameter("locale");

        try {
            AccountService.getInstance().createAccount(login, password, locale);
        }
        catch (final MissingRequiredFieldException e) {
            httpResponse.appendContent("{result: error, message: need login, password and locale param}");
            return;
        }

        httpResponse.appendContent("{result: success}");
    }

    private void doRestricted(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.appendContent("RESTRICTED AREA");
        httpResponse.setHttpStatus(HttpStatusEnum.FORBIDDEN);
    }

    private void doDefault(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.appendContent("Unknow uri : "+httpRequest.getUri());
    }
}

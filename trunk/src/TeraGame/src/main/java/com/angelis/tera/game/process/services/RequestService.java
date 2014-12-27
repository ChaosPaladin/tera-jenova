package com.angelis.tera.game.process.services;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.request.AbstractRequest;

public class RequestService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(RequestService.class.getName());

    private RequestService() {
    }

    @Override
    public void onInit() {
        log.info("RequestService started");
    }

    @Override
    public void onDestroy() {
        log.info("RequestService stopped");
    }

    public void onPayerDisconnect(final Player player) {
        final Collection<AbstractRequest<?, ?>> requests = ObjectIDService.getInstance().getObjectsByFamily(ObjectFamilyEnum.REQUEST);
        if (requests == null) {
            return;
        }

        for (final AbstractRequest<?, ?> request : requests) {
            if (request.getInitiator() instanceof Player && request.getInitiator().equals(player)) {
                request.doCancel();
            }

            if (request.getTarget() instanceof Player && request.getTarget().equals(player)) {
                request.doCancel();
            }
        }
    }
    
    public void onPlayerRequest(final Player player, final AbstractRequest<?, ?> request) {
        if (request == null) {
            return;
        }

        request.doStart();
    }
    
    public void onPlayerResponse(final Player player, final int requestUid, final boolean accept) {
        final AbstractRequest<?, ?> request = (AbstractRequest<?, ?>) ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.REQUEST, requestUid);
        if (request == null) {
            return;
        }
        
        if (accept) {
            request.doAction();
        } else {
            request.doCancel();
        }
    }

    public void onPlayerCancel(final int requestUid) {
        final AbstractRequest<?, ?> request = (AbstractRequest<?, ?>) ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.REQUEST, requestUid);
        if (request == null) {
            return;
        }

        request.doCancel();
    }

    public void cancelRequest(final AbstractRequest<?, ?> request) {
        request.doCancel();
    }

    /** SINGLETON */
    public static RequestService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final RequestService instance = new RequestService();
    }
}

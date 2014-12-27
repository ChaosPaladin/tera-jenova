package com.angelis.tera.game.process.services;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.common.utils.Function;
import com.angelis.tera.game.presentation.network.packet.server.SM_WELCOME_MESSAGE;
import com.angelis.tera.game.process.delegate.database.WelcomeMessageDelegate;
import com.angelis.tera.game.process.model.WelcomeMessage;
import com.angelis.tera.game.process.model.player.Player;

public class WelcomeMessageService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(WelcomeMessageService.class.getName());

    /** DELEGATES */
    private final WelcomeMessageDelegate welcomeMessageDelegate = new WelcomeMessageDelegate();

    private List<WelcomeMessage> welcomeMessages;

    // SERVICE EVENTS
    @Override
    public void onInit() {
        this.welcomeMessages = welcomeMessageDelegate.findAll();
        log.info("WelcomeMessageService started");
    }

    @Override
    public void onDestroy() {
        this.welcomeMessages.clear();
        log.info("WelcomeMessageService stopped");
    }

    public void onPlayerEnterWorld(final Player player) {
        final WelcomeMessage welcomeMessage = getLastWelcomeMessage();
        if (player.getLastOnlineTime().before(welcomeMessage.getCreationDate())) {
            this.sendPlayerWelcomeMessage(player, welcomeMessage);
        }
    }

    public void onPlayerRequestWelcomeMessage(final Player player) {
        this.sendPlayerWelcomeMessage(player, getLastWelcomeMessage());
    }

    // SERVICE METHODS
    public void createNewWelcomeMessage(final String title, final String content) {
        final WelcomeMessage welcomeMessage = new WelcomeMessage();
        welcomeMessage.setTitle(title);
        welcomeMessage.setContent(content);
        welcomeMessage.setCreationDate(new Date());

        this.welcomeMessages.add(welcomeMessage);
        welcomeMessageDelegate.create(welcomeMessage);
        
        this.sendLastWelcomeMessageToAllPlayers();
    }

    public WelcomeMessage getLastWelcomeMessage() {
        WelcomeMessage lastWelcomeMessage = new WelcomeMessage(new Date(0));

        for (final WelcomeMessage welcomeMessage : this.welcomeMessages) {
            if (welcomeMessage.getCreationDate().after(lastWelcomeMessage.getCreationDate())) {
                lastWelcomeMessage = welcomeMessage;
            }
        }

        return lastWelcomeMessage;
    }

    public void sendLastWelcomeMessageToAllPlayers() {
        final WelcomeMessage welcomeMessage = getLastWelcomeMessage();
        WorldService.getInstance().doOnAllOnlinePlayer(new Function<Player>() {
            @Override
            public void call(final Player player) {
                sendPlayerWelcomeMessage(player, welcomeMessage);
            }
        });
    }
    
    private void sendPlayerWelcomeMessage(final Player player, final WelcomeMessage welcomeMessage) {
        player.getConnection().sendPacket(new SM_WELCOME_MESSAGE(welcomeMessage));
    }

    /** SINGLETON */
    public static WelcomeMessageService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final WelcomeMessageService instance = new WelcomeMessageService();
    }
}

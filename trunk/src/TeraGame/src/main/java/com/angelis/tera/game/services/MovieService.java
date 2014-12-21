package com.angelis.tera.game.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.network.packet.server.SM_MOVIE_PLAY;
import com.angelis.tera.game.process.model.player.Player;

public class MovieService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(MovieService.class.getName());

    private MovieService() {
    }

    @Override
    public void onInit() {
        log.info("MovieService started");
    }

    @Override
    public void onDestroy() {
        log.info("MovieService stopped");
    }

    public void onPlayerMovieStart(final Player player, final int movieId) {
        player.getConnection().sendPacket(new SM_MOVIE_PLAY(movieId));
    }
    
    public void onPlayerMovieStop(final Player player, final int movieId, final boolean skipped) {
    }
    
    /** SINGLETON */
    public static MovieService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final MovieService instance = new MovieService();
    }
}

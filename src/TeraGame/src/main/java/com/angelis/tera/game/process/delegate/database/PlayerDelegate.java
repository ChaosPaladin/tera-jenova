package com.angelis.tera.game.process.delegate.database;

import com.angelis.tera.common.process.delegate.AbstractDatabaseDelegate;
import com.angelis.tera.game.domain.dao.database.PlayerDAO;
import com.angelis.tera.game.domain.entity.database.PlayerEntity;
import com.angelis.tera.game.domain.mapper.database.PlayerMapper;
import com.angelis.tera.game.process.model.player.Player;

public class PlayerDelegate extends AbstractDatabaseDelegate<PlayerEntity, Player> {

    public PlayerDelegate() {
        super(PlayerMapper.class, PlayerDAO.class);
    }

    public Player findByName(final String name) {
        final PlayerEntity entity = getDAO().findByName(name);
        if (entity == null) {
            return null;
        }
        
        return getMapper().map(entity);
    }

    @Override
    public PlayerDAO getDAO() {
        return (PlayerDAO) super.getDAO();
    }
}

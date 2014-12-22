package com.angelis.tera.game.domain.mapper.database;

import java.util.List;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import com.angelis.tera.common.domain.entity.database.AbstractDatabaseEntity;
import com.angelis.tera.common.domain.mapper.MapperManager;
import com.angelis.tera.common.domain.mapper.database.BaseAccountMapper;
import com.angelis.tera.common.process.model.AbstractModel;
import com.angelis.tera.common.utils.BeanUtils;
import com.angelis.tera.common.utils.CollectionUtils;
import com.angelis.tera.game.domain.entity.database.AccountEntity;
import com.angelis.tera.game.domain.entity.database.PlayerEntity;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.account.Options;
import com.angelis.tera.game.process.model.player.Player;

public class AccountMapper extends BaseAccountMapper<AccountEntity, Account> {
    
    // MODEL -> ENTITY
    @Override
    protected AccountEntity createNewEmptyEntity() {
        return new AccountEntity();
    }
    
    @Override
    public void map(final Account model, final AccountEntity entity) {
        // DIRECT
        super.map(model, entity);
        entity.setAccountType(model.getAccountType());
        
        // OPTION
        final Options option = model.getOptions();
        entity.setDisplayRange(option.getDisplayRange());
    }

    @Override
    protected void finalizeDependencies(final Account model, final AccountEntity entity, final List<Class<? extends AbstractModel>> excludedDependencies) {
        final List<Class<? extends AbstractModel>> dependenciesToExclude = CollectionUtils.createListFrom(Account.class);

        // PLAYER
        if (!excludedDependencies.contains(Player.class)) {
            final PlayerMapper playerMapper = MapperManager.getDatabaseMapper(PlayerMapper.class);
            final Set<PlayerEntity> playerEntities = new FastSet<>();
            for (final Player player : model.getPlayers()) {
                final PlayerEntity playerEntity = playerMapper.map(player, dependenciesToExclude);
                playerEntity.setAccount(entity);
                playerEntities.add(playerEntity);
            }
            entity.setPlayers(playerEntities);
        }
    }

    // ENTITY -> MODEL
    @Override
    protected Account createNewEmptyModel() {
        return new Account();
    }

    @Override
    public void map(final AccountEntity entity, final Account model) {
        // DIRECT
        super.map(entity, model);
        model.setAccountType(entity.getAccountType());
        
        // OPTION
        final Options option = new Options();
        option.setDisplayRange(entity.getDisplayRange());
        model.setOptions(option);
    }

    @Override
    protected void finalizeDependencies(final AccountEntity entity, final Account model, final List<Class<? extends AbstractDatabaseEntity>> excludedDependencies) {
        final List<Class<? extends AbstractDatabaseEntity>> dependenciesToExclude = CollectionUtils.createListFrom(AccountEntity.class);
        
        // PLAYER
        if (!excludedDependencies.contains(PlayerEntity.class)) {
            final PlayerMapper playerMapper = MapperManager.getDatabaseMapper(PlayerMapper.class);
            final List<Player> players = new FastList<>();
            for (final PlayerEntity playerEntity : entity.getPlayers()) {
                final Player player = playerMapper.map(playerEntity, dependenciesToExclude);
                player.setAccount(model);
                players.add(player);
            }
            model.setPlayers(players);
        }
    }

    @Override
    public void merge(final AccountEntity currentEntity, final AccountEntity existingEntity) {
        BeanUtils.fill(AccountEntity.class, currentEntity, existingEntity);
    }
}

package com.angelis.tera.game.process.services;

import org.apache.log4j.Logger;

import com.angelis.tera.common.process.exceptions.AccountBannedException;
import com.angelis.tera.common.process.exceptions.AccountNotFoundException;
import com.angelis.tera.common.process.exceptions.AccountWithIncorrectPasswordException;
import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.game.config.AccountConfig;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_ACCOUNT_PACKAGE_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOADING_SCREEN_CONTROL_INFO;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOGIN_ACCOUNT_INFO;
import com.angelis.tera.game.presentation.network.packet.server.SM_LOGIN_ARBITER;
import com.angelis.tera.game.presentation.network.packet.server.SM_REMAIN_PLAY_TIME;
import com.angelis.tera.game.presentation.network.packet.server.SM_SYSTEM_INFO;
import com.angelis.tera.game.process.delegate.database.AccountDelegate;
import com.angelis.tera.game.process.delegate.database.PlayerDelegate;
import com.angelis.tera.game.process.model.account.Account;
import com.angelis.tera.game.process.model.account.Options;
import com.angelis.tera.game.process.model.account.enums.AccountTypeEnum;
import com.angelis.tera.game.process.model.player.Player;

public class AccountService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AccountService.class.getName());
    
    /** DELEGATES */
    private final AccountDelegate accountDelegate = new AccountDelegate();
    private final PlayerDelegate playerDelegate = new PlayerDelegate();

    private AccountService() {
    }

    @Override
    public void onInit() {
        log.info("AccountService started");
    }

    @Override
    public void onDestroy() {
        log.info("AccountService stopped");
    }

    public void onAccountConnect(final Account account) {
        final TeraGameConnection connection = account.getConnection();

        connection.sendPacket(new SM_LOADING_SCREEN_CONTROL_INFO());
        connection.sendPacket(new SM_REMAIN_PLAY_TIME());
        connection.sendPacket(new SM_LOGIN_ARBITER());
        connection.sendPacket(new SM_LOGIN_ACCOUNT_INFO());
    }

    public void onAccountDisconnect(final Account account) {
        accountDelegate.update(account);
        for (final Player player : account.getPlayers()) {
            playerDelegate.update(player);
        }
    }

    public void authorizeAccount(final TeraGameConnection connection, final String login, final String password)
    throws AccountNotFoundException, AccountWithIncorrectPasswordException, AccountBannedException {
        log.info("Try authorizing " + login);

        Account account = accountDelegate.findByLogin(login);
        if (account == null && AccountConfig.ACCOUNT_AUTOCREATE) {
            account = new Account();
            account.setLogin(login);
            account.setPassword(password);
            account.setAccess(0);
            account.setAccountType(AccountTypeEnum.VETERAN);
            account.setOptions(new Options());
            account.setLocale(AccountConfig.ACCOUNT_DEFAULT_LOCALE);
            accountDelegate.create(account);
        }

        if (account == null) {
            throw new AccountNotFoundException(login);
        }

        if (!account.getPassword().equals(password)) {
            throw new AccountWithIncorrectPasswordException(login);
        }

        if (account.isBanned()) {
            throw new AccountBannedException(login);
        }

        connection.setAccount(account);
        this.onAccountConnect(account);
    }
    
    public void registerHardwareInfo(final Account account, final String osName, final String cpuName, final String gpuName) {
        account.getConnection().sendPacket(new SM_SYSTEM_INFO());
        account.getConnection().sendPacket(new SM_ACCOUNT_PACKAGE_LIST());
    }

    /** SINGLETON */
    public static AccountService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final AccountService instance = new AccountService();
    }
}

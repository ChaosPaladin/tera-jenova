package com.angelis.tera.login.process.services;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.angelis.tera.common.process.exceptions.account.AccountNotFoundException;
import com.angelis.tera.common.process.exceptions.account.AccountWithIncorrectPasswordException;
import com.angelis.tera.common.process.model.account.enums.AccountTypeEnum;
import com.angelis.tera.common.process.services.AbstractService;
import com.angelis.tera.login.process.delegate.database.AccountDelegate;
import com.angelis.tera.login.process.exceptions.MissingRequiredFieldException;
import com.angelis.tera.login.process.model.Account;

public class AccountService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(AccountService.class.getName());

    /** DELEGATES */
    private final AccountDelegate accountDelegate = new AccountDelegate();

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

    public Account getAccountByLoginAndPassword(final String login, final String password) throws MissingRequiredFieldException, AccountNotFoundException, AccountWithIncorrectPasswordException {
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            throw new MissingRequiredFieldException(login, password);
        }

        final Account account = accountDelegate.findByLogin(login);
        if (account == null) {
            throw new AccountNotFoundException(login);
        }

        if (!account.getPassword().equals(password)) {
            throw new AccountWithIncorrectPasswordException(login);
        }

        return account;
    }

    public void createAccount(final String login, final String password, final String locale) throws MissingRequiredFieldException {
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(locale)) {
            throw new MissingRequiredFieldException(login, password, locale);
        }

        final Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setBanned(false);
        account.setLocale(LocaleUtils.toLocale(locale.toLowerCase()));
        account.setAccountType(AccountTypeEnum.NORMAL);
        account.setExtraCharacterSlotCount(0);

        accountDelegate.create(account);
    }

    public void updateAccount(final Account account) {
        accountDelegate.update(account);
    }

    /** SINGLETON */
    public static AccountService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final AccountService instance = new AccountService();
    }
}

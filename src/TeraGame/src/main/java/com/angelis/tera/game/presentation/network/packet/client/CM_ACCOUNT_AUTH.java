package com.angelis.tera.game.presentation.network.packet.client;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.angelis.tera.common.process.exceptions.account.AccountBannedException;
import com.angelis.tera.common.process.exceptions.account.AccountNotFoundException;
import com.angelis.tera.common.process.exceptions.account.AccountWithIncorrectPasswordException;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.AccountService;

public class CM_ACCOUNT_AUTH extends TeraClientPacket {

    private String login;
    private String password;
    
    public CM_ACCOUNT_AUTH(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.readH(); // unk1
        this.readH(); // unk2
        final int length = this.readH(); // length
        this.readB(5); // unk3
        this.readD(); // unk4
        this.login = this.readS();
        try {
            this.password = new String(this.readB(length), "UTF-8");
        }
        catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void runImpl() {
        try {
            AccountService.getInstance().authorizeAccount(this.getConnection(), login, password);
        } catch (final AccountNotFoundException | AccountWithIncorrectPasswordException | AccountBannedException e) {
            log.error(e.getMessage());
            this.getConnection().close();
        }
    }
}

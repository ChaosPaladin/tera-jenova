package com.angelis.tera.game.presentation.network.packet.client.character;

import java.nio.ByteBuffer;

import com.angelis.tera.common.process.model.account.enums.AccountTypeEnum;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.presentation.network.packet.server.character.SM_CHARACTER_CREATE_ALLOWED;
import com.angelis.tera.game.process.model.account.Account;

/**
 * 
 * @author Angelis
 * 
 * Client ask server if he can create more characters
 */
public class CM_CHARACTER_CREATE_ALLOWED extends TeraClientPacket {

    public CM_CHARACTER_CREATE_ALLOWED(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        // empty
    }

    @Override
    protected void runImpl() {
        final TeraGameConnection connection = this.getConnection();
        final Account account = connection.getAccount();
        final AccountTypeEnum accountType = account.getAccountType();
        
        final int playerCount = account.getPlayers().size();
        final int maxAllowed = accountType.maxPlayerCount + account.getExtraCharacterSlotCount();
        connection.sendPacket(new SM_CHARACTER_CREATE_ALLOWED(playerCount < maxAllowed));
    }
}

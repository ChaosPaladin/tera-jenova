package com.angelis.tera.game.process.command.user;

import java.util.EnumSet;

import com.angelis.tera.common.process.model.account.enums.AccountTypeEnum;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class GpsCommand extends AbstractUserCommand {
    
    @Override
    public void execute(final TeraGameConnection connection, final String[] arguments) {
        final WorldPosition worldPosition = connection.getActivePlayer().getWorldPosition();
        final String message = "mapId: "+worldPosition.getMapId()+" | h: "+worldPosition.getHeading()+" | x: "+worldPosition.getX()+" | y: "+worldPosition.getY()+" | z: "+worldPosition.getZ();
        this.sendErrorMessage(connection, message);
    }

    @Override
    public EnumSet<? extends AccountTypeEnum> getAllowedAccountTypes() {
        return EnumSet.allOf(AccountTypeEnum.class);
    }
    
    @Override
    public int getArgumentCount() {
        return 0;
    }

    @Override
    public boolean checkArguments(final String[] arguments) {
        return true;
    }

    @Override
    public String getSyntax() {
        return "gps";
    }
}

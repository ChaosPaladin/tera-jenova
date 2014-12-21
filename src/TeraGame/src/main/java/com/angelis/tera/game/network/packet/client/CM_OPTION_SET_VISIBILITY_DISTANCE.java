package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.account.enums.DisplayRangeEnum;

public class CM_OPTION_SET_VISIBILITY_DISTANCE extends TeraClientPacket {

    private DisplayRangeEnum displayRange;

    public CM_OPTION_SET_VISIBILITY_DISTANCE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.displayRange = DisplayRangeEnum.fromValue(readD());
    }

    @Override
    protected void runImpl() {
        this.getConnection().getAccount().getOptions().setDisplayRange(this.displayRange);
    }
}
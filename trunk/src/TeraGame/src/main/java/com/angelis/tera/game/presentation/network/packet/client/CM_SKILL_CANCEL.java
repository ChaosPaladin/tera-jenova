package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.services.SkillService;

public class CM_SKILL_CANCEL extends TeraClientPacket {

    protected int skillId;
    protected int type;
    
    public CM_SKILL_CANCEL(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.skillId = readD();
        this.type = readD();
    }

    @Override
    protected void runImpl() {
        SkillService.getInstance().onPlayerSkillCancel(this.getConnection().getActivePlayer(), this.skillId, this.type);
    }
}

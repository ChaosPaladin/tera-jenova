package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.common.utils.Point3D;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.skill.SkillArgs;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.process.services.SkillService;

public class CM_SKILL_START extends TeraClientPacket {

    private final SkillArgs skillArgs = new SkillArgs();
    
    public CM_SKILL_START(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.skillArgs.setSkillId(readD() - 0x4000000);
        
        final WorldPosition startPosition = new WorldPosition();
        startPosition.setHeading(readH());
        startPosition.setX(readF());
        startPosition.setY(readF());
        startPosition.setZ(readF());
        skillArgs.setStartPosition(startPosition);
        
        final Point3D endPosition = new Point3D();
        endPosition.setX(readF());
        endPosition.setY(readF());
        endPosition.setZ(readF());
        this.skillArgs.setEndPosition(endPosition);
        
        readC(); //mb target count
        readC(); //mb target count
        readC(); //???
        readQ(); //mb targetid
        
        readB(new byte[12]);
    }

    @Override
    protected void runImpl() {
        SkillService.getInstance().onPlayerSkillUse(this.getConnection().getActivePlayer(), this.skillArgs);
    }
}

package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.skill.Skill;

public class SM_PLAYER_SKILL_LIST extends TeraServerPacket {

    private final Player player;
    
    public SM_PLAYER_SKILL_LIST(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int skillListSize = this.player.getSkillList().size();
        short shift = 8;
        
        writeH(byteBuffer, (short) skillListSize);
        writeH(byteBuffer, shift);

        int counter = 1;
        for (final Skill skill : this.player.getSkillList().getSkills()) {
            writeH(byteBuffer, shift);
            shift += 10;
            writeH(byteBuffer, counter++ != skillListSize ? shift : 0);
            writeD(byteBuffer, skill.getSkillId());

            // TODO
            writeH(byteBuffer, 1);
            /*writeC(byteBuffer, (byte)
               (!global::Data.Data.UserSkills[Player.TemplateId].ContainsKey(skill) ||
                global::Data.Data.UserSkills[Player.TemplateId][skill].IsActive
                ? 1 : 0));*/
        }
    }
}

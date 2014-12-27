package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.List;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.player.Achievement;
import com.angelis.tera.game.process.model.player.Player;

public class SM_ACHIEVEMENT_PROGRESS_UPDATE extends TeraServerPacket {

    private final Player player;

    public SM_ACHIEVEMENT_PROGRESS_UPDATE(final Player player) {
        this.player = player;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final List<Achievement> achievements = this.player.getAchievements();
        
        writeH(byteBuffer, achievements.size());
        
        final int firstAchievementShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        if (!achievements.isEmpty()) {
            this.writeBufferPosition(byteBuffer, firstAchievementShift);
            
            int i = 0;
            for (final Achievement achievement : achievements) {
                writeH(byteBuffer, byteBuffer.position());
                
                final int nextAchievementShift = byteBuffer.position();
                writeH(byteBuffer, 0);
                
                writeH(byteBuffer, 1); // unk
                
                final int achievementDataShift = byteBuffer.position(); // not sure if it's really data shift
                writeH(byteBuffer, 0);
                
                writeD(byteBuffer, achievement.getId());
                this.writeBufferPosition(byteBuffer, achievementDataShift);
                
                writeH(byteBuffer, byteBuffer.position());
                
                writeD(byteBuffer, 0);
                writeH(byteBuffer, 0);
                
                writeD(byteBuffer, 1);
                writeD(byteBuffer, 12); // UNK can have many values 6, 1
                writeD(byteBuffer, 1);
                
                if (++i < achievements.size()) {
                    this.writeBufferPosition(byteBuffer, nextAchievementShift);
                }
            }
        }
    }
}

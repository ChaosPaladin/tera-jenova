package com.angelis.tera.game.presentation.network.packet.server.player;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.controllers.PlayerController;
import com.angelis.tera.game.process.controllers.enums.RightEnum;
import com.angelis.tera.game.process.model.creature.BaseStats;
import com.angelis.tera.game.process.model.creature.CreatureBonusStats;
import com.angelis.tera.game.process.model.creature.CurrentStats;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;

public class SM_PLAYER_STATS_UPDATE extends TeraServerPacket {

    private final Player player;

    public SM_PLAYER_STATS_UPDATE(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final CurrentStats currentStats = this.player.getCurrentStats();
        final BaseStats baseStats = this.player.getTemplate().getBaseStats();
        final CreatureBonusStats bonusStats = this.player.getCreatureBonusStats();
        final PlayerController controller = this.player.getController();
        final Storage storage = this.player.getStorage(StorageTypeEnum.INVENTORY);
        
        /** LIFE & MANA BARS */
        writeD(byteBuffer, currentStats.getHp()); // current hp
        writeD(byteBuffer, currentStats.getMp()); // current mp
        writeD(byteBuffer, 0); // unk
        writeD(byteBuffer, baseStats.getBaseHp()); // max hp TODO
        writeD(byteBuffer, baseStats.getBaseMp()); // max mp TODO
        
        /** STATS */
        writeD(byteBuffer, baseStats.getPower()+10);
        writeD(byteBuffer, baseStats.getEndurance());
        writeD(byteBuffer, baseStats.getImpactFactor());
        writeD(byteBuffer, baseStats.getBalanceFactor());
        writeH(byteBuffer, controller.can(RightEnum.WALK) ? currentStats.getSpeed() : 0);
        writeH(byteBuffer, 52);
        writeH(byteBuffer, baseStats.getAttackSpeed());
        

        /** Critical stats */
        writeF(byteBuffer, baseStats.getCritRate());
        writeF(byteBuffer, baseStats.getCritResistance());
        writeF(byteBuffer, baseStats.getCritPower());
        writeD(byteBuffer, baseStats.getMinAttack());
        writeD(byteBuffer, baseStats.getMaxAttack());
        writeD(byteBuffer, baseStats.getDefense());
        writeD(byteBuffer, baseStats.getImpact());
        writeD(byteBuffer, baseStats.getBalance());
        
        /** Resists */
        writeF(byteBuffer, baseStats.getWeakeningResistance());
        writeF(byteBuffer, baseStats.getPeriodicResistance());
        writeF(byteBuffer, baseStats.getStunResistance());

        /** Region additional stats */
        writeD(byteBuffer, 0); // power bonus
        writeD(byteBuffer, 0); // endurance bonus
        writeD(byteBuffer, 0); // impact bonus
        writeD(byteBuffer, 0); // balance bonus
        writeD(byteBuffer, bonusStats.getSpeed()); // speed bonus
        writeD(byteBuffer, 0); // attack speed bonus
        
        writeH(byteBuffer, 0);
        
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);

        writeD(byteBuffer, 0); // min attack bonus
        writeD(byteBuffer, 0); // max attack bonus
        writeD(byteBuffer, 0); // defense bonus
        writeD(byteBuffer, 0); // impact bonus
        writeD(byteBuffer, 0); // balance bonus

        writeF(byteBuffer, 0); // weakening resistance bonus
        writeF(byteBuffer, 0); // periodic resistance bonus
        writeF(byteBuffer, 0); // stun resistance bonus

        writeH(byteBuffer, this.player.getLevel());
        writeH(byteBuffer, this.player.getPlayerMode().value);

        if (this.player.getCurrentStats().getStamina() > 100) {
            writeC(byteBuffer, 4); // Abundant stamina
        }
        else if (this.player.getCurrentStats().getStamina() > 20) {
            writeC(byteBuffer, 3); // Normal stamina
        }
        else {
            writeC(byteBuffer, 2); // Poor stamina
        }

        writeB(byteBuffer, "0001");

        writeD(byteBuffer, (int) (baseStats.getHpStamina() * (currentStats.getStamina() / 120.0)));
        writeD(byteBuffer, (int) (baseStats.getMpStamina() * (currentStats.getStamina() / 120.0)));
        
        writeD(byteBuffer, currentStats.getStamina());
        writeD(byteBuffer, 120);
        writeD(byteBuffer, currentStats.getRe()); // current resolve
        writeD(byteBuffer, baseStats.getBaseRe()); // max resolve
        writeB(byteBuffer, "0000000000000000");
        
        writeD(byteBuffer, storage.getMaxEquipedLevel(false));
        writeD(byteBuffer, storage.getMaxEquipedLevel(true));
        
        writeB(byteBuffer, "0000000000000000401F000003000000");
    }
}

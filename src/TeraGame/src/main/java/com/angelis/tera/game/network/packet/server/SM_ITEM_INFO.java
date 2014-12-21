package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.storage.StorageItem;

public class SM_ITEM_INFO extends TeraServerPacket {

    private final StorageItem storageItem;
    private final String soulboundName = null;
    
    public SM_ITEM_INFO(final StorageItem storageItem) {
        this.storageItem = storageItem;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeD(byteBuffer, 0);
        
        final int creatorShift = byteBuffer.position();
        writeH(byteBuffer, 0); //Creator name shift?
        
        final int soulboundShift = byteBuffer.position();
        writeH(byteBuffer, 0); //Soulbound name shift
        
        writeB(byteBuffer, "23012501");
        writeH(byteBuffer, 20); //20
        writeH(byteBuffer, 0);

        writeQ(byteBuffer, storageItem.getItem().getUid()); //ItemUniqueId
        writeD(byteBuffer, storageItem.getItem().getId()); //ItemId
        writeQ(byteBuffer, storageItem.getItem().getUid()); //ItemUniqueId

        writeD(byteBuffer, 0); //???
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0); //???

        writeD(byteBuffer, 0);
        writeD(byteBuffer, 1);
        writeD(byteBuffer, storageItem.getCount()); //Count
        writeD(byteBuffer, 0); //Enchant level
        writeD(byteBuffer, 0);
        writeC(byteBuffer, 1); //Can't trade
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0); //EffectId
        writeD(byteBuffer, 0); //EffectId on enchant +3
        writeD(byteBuffer, 0); //EffectId on enchant +6
        writeD(byteBuffer, 0); //EffectId on enchant +9
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeH(byteBuffer, 0);
        writeC(byteBuffer, 0);
        writeC(byteBuffer, 0); //Requires Identification Scroll to remove
        writeC(byteBuffer, 0); //Masterwork
        writeD(byteBuffer, 0);


        writeC(byteBuffer, 0); //Show "Stats Totals when Equipped"

        //"Stats Totals when Equipped"
        writeD(byteBuffer, 0); //Attack
        writeD(byteBuffer, 0); //Defence
        writeD(byteBuffer, 0); //Knockdown
        writeD(byteBuffer, 0); //Resist to knockdown
        writeF(byteBuffer, 0); //Crit chanse (float)
        writeF(byteBuffer, 0); //Crit resist (float)
        writeF(byteBuffer, 0); //Crit power (float)
        writeD(byteBuffer, 0); //Damage
        writeD(byteBuffer, 0); //Balance
        writeD(byteBuffer, 0); //Attack speed
        writeD(byteBuffer, 0); //Movement
        writeD(byteBuffer, 0); //Binding (float)
        writeD(byteBuffer, 0); //Poison (float)
        writeD(byteBuffer, 0); //Stun (float)

        writeD(byteBuffer, 0); //Add attack
        writeD(byteBuffer, 0); //Add defence
        writeD(byteBuffer, 0); //Add knockdown
        writeD(byteBuffer, 0); //Add resist to knockdown
        writeD(byteBuffer, 0); //Add crit chanse (float)
        writeD(byteBuffer, 0); //Add crit resist (float)
        writeD(byteBuffer, 0); //Add crit power (float)
        writeD(byteBuffer, 0); //Add damage
        writeD(byteBuffer, 0); //Add Balance
        writeD(byteBuffer, 0); //Add attack speed
        writeD(byteBuffer, 0); //Add movement
        writeD(byteBuffer, 0); //Add Binding (float)
        writeD(byteBuffer, 0); //Add poison (float)
        writeD(byteBuffer, 0); //Add stun (float)

        writeD(byteBuffer, 0); //Min attack
        writeD(byteBuffer, 0); //Add min attack

        writeD(byteBuffer, 3);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 1); //Item level
        writeD(byteBuffer, 0);

        this.writeBufferPosition(byteBuffer, creatorShift);
        writeS(byteBuffer, this.storageItem.getCreatorName()); //Creator name

        this.writeBufferPosition(byteBuffer, soulboundShift);
        writeS(byteBuffer, this.soulboundName); //Soulbound name
    }

}

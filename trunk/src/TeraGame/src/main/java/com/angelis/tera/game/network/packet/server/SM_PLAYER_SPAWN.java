package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.enums.PlayerRelationEnum;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.enums.InventorySlotEnum;
import com.angelis.tera.game.process.model.visible.WorldPosition;

public class SM_PLAYER_SPAWN extends TeraServerPacket {

    private final Player player;
    private final PlayerRelationEnum playerRelation;
    
    public SM_PLAYER_SPAWN(final Player player, final PlayerRelationEnum playerRelation) {
        this.player = player;
        this.playerRelation = playerRelation;
    }
    
    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final int playerNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int guildNameShift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int unkShift = byteBuffer.position();
        writeH(byteBuffer, 0); 
        
        final int detailShift = byteBuffer.position();
        writeH(byteBuffer, 0);
 
        writeH(byteBuffer, (short) this.player.getPlayerAppearance().getDetails1().length); // 2000
        
        final int unk2Shift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int unk3Shift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        final int unk4Shift = byteBuffer.position();
        writeH(byteBuffer, 0);
        
        writeH(byteBuffer, (short) this.player.getPlayerAppearance().getDetails2().length); // 4000

        writeD(byteBuffer, 14);
        writeD(byteBuffer, this.player.getId());

        writeUid(byteBuffer, this.player);

        final WorldPosition worldPosition = this.player.getWorldPosition();
        writeF(byteBuffer, worldPosition.getX());
        writeF(byteBuffer, worldPosition.getY());
        writeF(byteBuffer, worldPosition.getZ());
        writeH(byteBuffer, worldPosition.getHeading());

        writeD(byteBuffer, this.playerRelation.getValue());
        writeD(byteBuffer, this.player.getRaceGenderClassValue());

        writeB(byteBuffer, "00008700AA00000000000101"); //???

        writeB(byteBuffer, this.player.getPlayerAppearance().getData());

        final Storage storage = this.player.getStorage(StorageTypeEnum.INVENTORY);
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.GLOVES, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FOOT, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.HAIR, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FACE, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, 0);
        
        writeC(byteBuffer, 1);
        
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
        
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.HAIR_DECORATION, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.MASK, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.BACK, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON_APPEARANCE, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR_APPEARANCE, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, 0); //Unk possible Item?
        
        writeD(byteBuffer, this.player.getLevel());
        writeB(byteBuffer, "0000000000000000010000000000000000000000000000000000000000000000009B220000DE010E000000000000000000000000000001000000000000000064000000");
        writeF(byteBuffer, this.player.getPlayerCustomData().getSize());

        this.writeBufferPosition(byteBuffer, playerNameShift);
        writeS(byteBuffer, this.player.getName()); //Name

        this.writeBufferPosition(byteBuffer, guildNameShift);
        writeS(byteBuffer, this.player.getGuild() != null ? this.player.getGuild().getName() : "");

        this.writeBufferPosition(byteBuffer, unkShift);
        writeS(byteBuffer, ""); //Unk1

        this.writeBufferPosition(byteBuffer, detailShift);
        writeB(byteBuffer, this.player.getPlayerAppearance().getDetails1());
        writeB(byteBuffer, this.player.getPlayerAppearance().getDetails2());

        this.writeBufferPosition(byteBuffer, unk2Shift);
        writeS(byteBuffer, ""); //Unk2

        this.writeBufferPosition(byteBuffer, unk3Shift);
        writeS(byteBuffer, ""); // TODO guild title
        
        this.writeBufferPosition(byteBuffer, unk4Shift);
    }
}

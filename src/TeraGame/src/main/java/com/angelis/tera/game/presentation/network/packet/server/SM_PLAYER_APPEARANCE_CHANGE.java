package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.enums.InventorySlotEnum;

public class SM_PLAYER_APPEARANCE_CHANGE extends TeraServerPacket {

    private final Player player;
    
    public SM_PLAYER_APPEARANCE_CHANGE(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Storage storage = this.player.getStorage(StorageTypeEnum.INVENTORY);

        writeUid(byteBuffer, this.player);
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.GLOVES, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FOOT, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.HAIR, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FACE, Storage.NEW_STORAGE_ITEM).getItemId());
        
        writeB(byteBuffer, new byte[44]);
        
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.HAIR_DECORATION, Storage.NEW_STORAGE_ITEM).getItemId()); //Item (Skin Head)
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.MASK, Storage.NEW_STORAGE_ITEM).getItemId()); //Item (Skin Face)
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.BACK, Storage.NEW_STORAGE_ITEM).getItemId()); //Item (Skin Backpack)?
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON_APPEARANCE, Storage.NEW_STORAGE_ITEM).getItemId()); //Item (Skin Weapon)
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR_APPEARANCE, Storage.NEW_STORAGE_ITEM).getItemId()); //Item (Skin Armor)
        writeD(byteBuffer, 0); //Unk possible Item?
        
        writeC(byteBuffer, 1);
    }

}

package com.angelis.tera.game.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.enums.InventorySlotEnum;
import com.angelis.tera.game.services.PlayerService;

/**
 * @author Angelis
 *
 * This packet is sent to this client and hold information about his character (after he request enter world)
 */
public class SM_LOGIN extends TeraServerPacket {

    private final Player player;

    public SM_LOGIN(final Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        writeH(byteBuffer, 0); //Name shift
        
        writeH(byteBuffer, 0); // Details1 shift
        writeH(byteBuffer, this.player.getPlayerAppearance().getDetails1().length);
        
        writeH(byteBuffer, 0); // Details2 shift
        writeH(byteBuffer, this.player.getPlayerAppearance().getDetails2().length);

        writeD(byteBuffer, this.player.getRaceGenderClassValue());
       
        writeUid(byteBuffer, this.player);
        
        writeD(byteBuffer, 15);
        
        writeD(byteBuffer, this.player.getId());

        writeB(byteBuffer, "000000000100000000460000006E000000");

        writeB(byteBuffer, this.player.getPlayerAppearance().getData()); //this.playerData

        writeH(byteBuffer, 1); //Online?    
        writeH(byteBuffer, this.player.getLevel()); //Level
        
        writeB(byteBuffer, "000000000000000001000000000000000000");

        writeQ(byteBuffer, this.player.getExperience());
        writeQ(byteBuffer, PlayerService.getInstance().getExpShown(this.player));
        writeQ(byteBuffer, PlayerService.getInstance().getExpNeeded(this.player));

        writeD(byteBuffer, 0); //unk
        
        writeD(byteBuffer, this.player.getCurrentRestedExperience());
        writeD(byteBuffer, this.player.getMaxRestedExperience());

        writeB(byteBuffer, "0000803F00000000"); //unk
        
        final Storage storage = this.player.getStorage(StorageTypeEnum.INVENTORY);
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.WEAPON, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.ARMOR, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.GLOVES, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FOOT, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.HAIR, Storage.NEW_STORAGE_ITEM).getItemId());
        writeD(byteBuffer, storage.getStorageItemByInventorySlot(InventorySlotEnum.FACE, Storage.NEW_STORAGE_ITEM).getItemId());
        
        writeD(byteBuffer, 0);

        writeB(byteBuffer, "A228B7110000000001000000000000000000000000000000000000000000000000");

        // TODO item color
        /*writeD(byteBuffer, InventoryService.getInstance().getItemAtSlot(this.player, InventorySlot.WEAPON).getColor());
        writeD(byteBuffer, InventoryService.getInstance().getItemAtSlot(this.player, InventorySlot.ARMOR).getColor());
        writeD(byteBuffer, InventoryService.getInstance().getItemAtSlot(this.player, InventorySlot.GLOVES).getColor());
        writeD(byteBuffer, InventoryService.getInstance().getItemAtSlot(this.player, InventorySlot.FOOT).getColor());
        writeD(byteBuffer, InventoryService.getInstance().getItemAtSlot(this.player, InventorySlot.HAIR).getColor());
        writeD(byteBuffer, InventoryService.getInstance().getItemAtSlot(this.player, InventorySlot.FACE).getColor());*/
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);
        writeD(byteBuffer, 0);

        writeB(byteBuffer, "0001");//On/offline?

        writeD(byteBuffer, 0); //Item (Skin Head)
        writeD(byteBuffer, 0); //Item (Skin Face)
        writeD(byteBuffer, 0); //Item (Skin Backpack)?
        writeD(byteBuffer, 0); //Item (Skin Weapon)
        writeD(byteBuffer, 0); //Item (Skin Armor)
        writeD(byteBuffer, 0); //Unk possible Item?

        writeB(byteBuffer, "00000000000000000000000001000000000000000000000000"); //unk
        writeF(byteBuffer, this.player.getPlayerCustomData().getSize());
        
        this.writeBufferPosition(byteBuffer, 4);
        writeS(byteBuffer, this.player.getName());

        this.writeBufferPosition(byteBuffer, 6);
        writeB(byteBuffer, this.player.getPlayerAppearance().getDetails1());

        this.writeBufferPosition(byteBuffer, 10);
        writeB(byteBuffer, this.player.getPlayerAppearance().getDetails2());
    }
}

package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Map.Entry;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.enums.StorageTypeEnum;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.storage.Storage;
import com.angelis.tera.game.process.model.storage.StorageItem;

public class SM_INVENTORY extends TeraServerPacket {

    private final Player player;
    private final boolean isInventory;

    public SM_INVENTORY(final Player player, final boolean isInventory) {
        this.player = player;
        this.isInventory = isInventory;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Storage storage = player.getStorage(StorageTypeEnum.INVENTORY);
        final Map<Integer, StorageItem> storageItems = storage.getStorageItems();

        writeH(byteBuffer, storageItems.size()); // item count

        final int firstItemShift = byteBuffer.position();
        writeH(byteBuffer, 0); // first item shift

        writeUid(byteBuffer, player);
        writeQ(byteBuffer, storage.getMoney());

        writeC(byteBuffer, isInventory);

        writeH(byteBuffer, 1); // Unk
        writeD(byteBuffer, storage.getSize()); // storage max size

        writeD(byteBuffer, storage.getMaxEquipedLevel(false));
        writeD(byteBuffer, storage.getMaxEquipedLevel(true));
        writeB(byteBuffer, "000000000000000000000000");

        this.writeBufferPosition(byteBuffer, firstItemShift);

        int index = 0;
        for (final Entry<Integer, StorageItem> entry : storageItems.entrySet()) {
            final StorageItem storageItem = entry.getValue();
            final Item item = storageItem.getItem();

            // Now shift
            writeH(byteBuffer, byteBuffer.position());

            // next shift
            final int nextShift = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, 0);

            // next shift - 2
            final int nextShift2 = byteBuffer.position();
            writeH(byteBuffer, 0);

            writeD(byteBuffer, item.getId());
            writeQ(byteBuffer, item.getUid());
            writeQ(byteBuffer, this.player.getId());

            writeQ(byteBuffer, entry.getKey()); // item slot
            writeQ(byteBuffer, storageItem.getCount());

            writeD(byteBuffer, 30); // enchantment bonus
            writeB(byteBuffer, "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000FFFFFFFF00000000");

            this.writeBufferPosition(byteBuffer, byteBuffer.position(), nextShift2);

            writeH(byteBuffer, 0);

            if (++index < storageItems.size()) {
                this.writeBufferPosition(byteBuffer, byteBuffer.position(), nextShift);
            }
        }
    }
}

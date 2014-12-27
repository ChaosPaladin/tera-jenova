package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.services.ObjectIDService;
import com.angelis.tera.game.process.services.StockExchangeService;

public class CM_STOCK_EXCHANGE_ITEM_INFO extends TeraClientPacket {

    private Integer uid;

    public CM_STOCK_EXCHANGE_ITEM_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.uid = readD();
        readD(); // 0x00000000
    }

    @Override
    protected void runImpl() {
        StockExchangeService.getInstance().onStockExchangeItemInfo(this.getConnection().getActivePlayer(), ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.STOCK_EXCHANGE_ITEM, this.uid));
    }

}

package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.services.ObjectIDService;
import com.angelis.tera.game.process.services.StockExchangeService;

public class CM_STOCK_EXCHANGE_ITEM_ACCOUNT_REQUEST extends TeraClientPacket {

    private int uid;
    private int itemId;

    public CM_STOCK_EXCHANGE_ITEM_ACCOUNT_REQUEST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.uid = readD();
        this.itemId = readD();
    }

    @Override
    protected void runImpl() {
        StockExchangeService.getInstance().onStockExchangeAccountItemRequest(this.getConnection().getActivePlayer(), this.itemId, ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.STOCK_EXCHANGE_ITEM, this.uid));
    }
}

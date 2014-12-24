package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;
import java.util.Date;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.services.ObjectIDService;
import com.angelis.tera.game.process.services.StockExchangeService;

public class CM_STOCK_EXCHANGE_ITEM_UNIQUE_REQUEST extends TeraClientPacket {

    private int uid;
    private Date requestTimestamp;
    
    public CM_STOCK_EXCHANGE_ITEM_UNIQUE_REQUEST(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.uid = readD();
        this.requestTimestamp = new Date(readD()*1000);
    }

    @Override
    protected void runImpl() {
        StockExchangeService.getInstance().onStockExchangeUniqueItemRequest(this.getConnection().getActivePlayer(), this.requestTimestamp, ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.STOCK_EXCHANGE_ITEM, this.uid));
    }

}

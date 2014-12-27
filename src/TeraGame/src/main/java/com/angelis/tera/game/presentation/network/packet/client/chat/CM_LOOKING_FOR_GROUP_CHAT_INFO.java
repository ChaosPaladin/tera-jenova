package com.angelis.tera.game.presentation.network.packet.client.chat;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.services.ChatService;
import com.angelis.tera.game.process.services.ObjectIDService;

public class CM_LOOKING_FOR_GROUP_CHAT_INFO extends TeraClientPacket {

    private int uid;
    
    public CM_LOOKING_FOR_GROUP_CHAT_INFO(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.uid = readD();
    }

    @Override
    protected void runImpl() {
        ChatService.getInstance().onPlayerRequestLookingForGroupInfo(this.getConnection().getActivePlayer(), ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.LOOKING_FOR_GROUP, uid));
    }

}

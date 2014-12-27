package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.enums.ObjectFamilyEnum;
import com.angelis.tera.game.process.model.gameobject.GameObject;
import com.angelis.tera.game.process.services.ObjectIDService;
import com.angelis.tera.game.process.services.SpawnService;

public class CM_GAMEOBJECT_REMOVE extends TeraClientPacket {

    private int gameObjectUid;

    public CM_GAMEOBJECT_REMOVE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.gameObjectUid = readD();
        readD(); // familly
    }

    @Override
    protected void runImpl() {
        final GameObject gameObject = (GameObject) ObjectIDService.getInstance().getObjectByUId(ObjectFamilyEnum.GAME_OBJECT, this.gameObjectUid);
        if (gameObject == null) {
            return;
        }
        
        SpawnService.getInstance().despawnGameObject(gameObject);
    }

}

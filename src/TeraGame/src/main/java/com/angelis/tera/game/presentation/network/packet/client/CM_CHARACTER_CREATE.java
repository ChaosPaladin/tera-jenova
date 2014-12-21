package com.angelis.tera.game.presentation.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraClientPacket;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.PlayerAppearance;
import com.angelis.tera.game.process.model.player.enums.GenderEnum;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;
import com.angelis.tera.game.process.model.player.enums.RaceEnum;
import com.angelis.tera.game.process.services.PlayerService;

public class CM_CHARACTER_CREATE extends TeraClientPacket {

    private final Player player = new Player();

    public CM_CHARACTER_CREATE(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        final PlayerAppearance playerAppearance = new PlayerAppearance();

        readH(); // nameShift

        readH(); // detailsShift1
        final short detailsLength1 = readH();

        readH(); // detailsShift2
        final short detailsLength2 = readH();

        player.setGender(GenderEnum.fromValue(readD()));
        player.setRace(RaceEnum.fromValue(readD()));
        player.setPlayerClass(PlayerClassEnum.fromValue(readD()));

        playerAppearance.setData(readB(8));

        readC();
        readD();
        readC();

        player.setName(readS());

        playerAppearance.setDetails1(readB(detailsLength1));
        playerAppearance.setDetails2(readB(detailsLength2));

        player.setPlayerAppearance(playerAppearance);
    }

    @Override
    protected void runImpl() {
        PlayerService.getInstance().createPlayer(this.getConnection(), player);
    }
}

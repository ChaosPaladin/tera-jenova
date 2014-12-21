package com.angelis.tera.game.network.packet.client;

import java.nio.ByteBuffer;

import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.connection.TeraGameConnection;
import com.angelis.tera.game.network.packet.TeraClientPacket;
import com.angelis.tera.game.network.packet.server.SM_AVAILABLE_SOCIAL_LIST;
import com.angelis.tera.game.network.packet.server.SM_ENTER_WORLD;
import com.angelis.tera.game.network.packet.server.SM_F2P_PREMIUM_USER_PERMISSION;
import com.angelis.tera.game.network.packet.server.SM_FESTIVAL_LIST;
import com.angelis.tera.game.network.packet.server.SM_INVENTORY;
import com.angelis.tera.game.network.packet.server.SM_LOAD_HINT;
import com.angelis.tera.game.network.packet.server.SM_LOAD_TOPO;
import com.angelis.tera.game.network.packet.server.SM_LOGIN;
import com.angelis.tera.game.network.packet.server.SM_MASSTIGE_STATUS;
import com.angelis.tera.game.network.packet.server.SM_MOVE_DISTANCE_DELTA;
import com.angelis.tera.game.network.packet.server.SM_NPC_GUILD_LIST;
import com.angelis.tera.game.network.packet.server.SM_OPCODE_LESS_PACKET;
import com.angelis.tera.game.network.packet.server.SM_PET_INCUBATOR_INFO_CHANGE;
import com.angelis.tera.game.network.packet.server.SM_PET_INFO_CLEAR;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_APPEARANCE_CHANGE;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_DESCRIPTION;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_EQUIP_ITEM_CHANGER;
import com.angelis.tera.game.network.packet.server.SM_PLAYER_SKILL_LIST;
import com.angelis.tera.game.network.packet.server.SM_VIRTUAL_LATENCY;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.services.CraftService;
import com.angelis.tera.game.services.PlayerService;
import com.angelis.tera.game.services.QuestService;

public class CM_ENTER_WORLD extends TeraClientPacket {

    private int playerId;
    private boolean withProlog;

    public CM_ENTER_WORLD(final ByteBuffer byteBuffer, final TeraGameConnection connection) {
        super(byteBuffer, connection);
    }

    @Override
    protected void readImpl() {
        this.playerId = readD();
        this.withProlog = readC() == 1;
    }

    @Override
    protected void runImpl() {
        final TeraGameConnection connection = this.getConnection();
        final Player player = connection.getAccount().getPlayerById(this.playerId);

        if (player == null) {
            return;
        }

        PlayerService.getInstance().registerPlayer(connection, player);

        connection.sendPacket(new SM_ENTER_WORLD());

        try {
            Thread.sleep(3000);
        }
        catch (final InterruptedException e) {
            e.printStackTrace();
        }

      //connection.sendPacket(new SM_CURRENT_ELECTION_STATE());
        
        connection.sendPacket(new SM_LOGIN(player));
        connection.sendPacket(new SM_INVENTORY(player, false));
        connection.sendPacket(new SM_PLAYER_SKILL_LIST(player));

        connection.sendPacket(new SM_AVAILABLE_SOCIAL_LIST());
        
        
        QuestService.getInstance().onPlayerEnterWorld(player);
        
        // TODO HERE IF THE WELCOME MESSAGE IS SET
        
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("040008000800100015050000100018001705000018002000180500002000000019050000"));
        
        CraftService.getInstance().onPlayerEnterWorld(player);
        
        connection.sendPacket(new SM_NPC_GUILD_LIST());

        connection.sendPacket(new SM_PET_INCUBATOR_INFO_CHANGE());
        connection.sendPacket(new SM_PET_INFO_CLEAR());
        connection.sendPacket(new SM_VIRTUAL_LATENCY());
        connection.sendPacket(new SM_MOVE_DISTANCE_DELTA());
        connection.sendPacket(new SM_PLAYER_DESCRIPTION(player));
        connection.sendPacket(new SM_F2P_PREMIUM_USER_PERMISSION());
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("9E7B0000000000000000"));

        connection.sendPacket(SystemMessages.YOU_CAN_JOIN_GUILD_VIA_SOCIAL_LINK());

        connection.sendPacket(new SM_MASSTIGE_STATUS());
        connection.sendPacket(new SM_PLAYER_APPEARANCE_CHANGE(player));
        connection.sendPacket(new SM_PLAYER_EQUIP_ITEM_CHANGER());
        connection.sendPacket(new SM_FESTIVAL_LIST());

        connection.sendPacket(new SM_LOAD_TOPO(player.getWorldPosition()));
        connection.sendPacket(new SM_LOAD_HINT());
        
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("1388010009000009000000B201000036A4F82B00000000FFFFFF7F0000000000000000"));
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("599F0D00080008002C009918040000800C00411F0000FE0300000F270000AA804847089FE2C50000AAC32C005000C9B90F0000800C00411F0000FE0300000F27000027D55947AA1DCE4400006AC35000740010C20D0000800C00411F0000FE0300000F27000026180D4788FE3E4500403B44740098004F0F060000800C00411F0000FE0300000F2700003BC2704792EC1FC4008026C49800BC00FF920A0000800C00411F0000FE0300000F27000054C1714736951E4700303245BC00E0009FA7080000800C00411F0000FE0300000F270000FB0F12472463D24600807244E00004019F8F0C0000800C00411F0000FE0300000F270000AE248947CF42BE4600606F4504012801BD90060000800C00411F0000FE0300000F270000FA0D68474089C9460060344528014C01BDA1060000800C00411F0000FE0300000F270000615B8947D0BDF34600703E454C017001C7B1080000800C00411F0000FE0300000F27000037021747761F214700E08F44700194011172020000800C00411F0000FE0300000F27000088831647921C824700702E459401B8016D680E0000800C00411F0000FE0300000F2700001ACF4247ACEF804700409A45B80100001D40010000800C00411F0000FE0300000F2700000B098047F30A8D4700C09845"));
        connection.sendPacket(new SM_OPCODE_LESS_PACKET("648631CD0000C95B075400000000"));

        connection.sendPacket(new SM_INVENTORY(player, false));
    }
}

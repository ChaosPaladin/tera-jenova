package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;

public class SM_MISSION_COMPLETE_INFO extends TeraServerPacket {

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        // TODO
        writeB(byteBuffer, "4D00080008001000C900000010001800CA00000018002000CB000000200028002D010000280030002E010000300038009101000038004000920100004000480059020000480050005A020000500058005B020000580060005C020000600068005D02000068007000BD02000070007800BE02000078008000C00200008000880021030000880090002203000090009800230300009800A00024030000A000A80085030000A800B00086030000B000B80087030000B800C00088030000C000C80015050000C800D00017050000D000D80018050000D800E00019050000E000E8001D050000E800F0001F050000F000F80021050000F80000012305000000010801240500000801100125050000100118014605000018012001DD05000020012801DE05000028013001DF05000030013801E005000038014001E105000040014801D107000048015001D207000050015801D307000058016001FD08000060016801FE08000068017001FF080000700178010009000078018001C509000080018801C609000088019001C709000090019801C80900009801A001C9090000A001A8018D0A0000A801B0018E0A0000B001B801F10A0000B801C001F20A0000C001C8011D0C0000C801D0011E0C0000D001D8011F0C0000D801E001810C0000E001E801750E0000E801F001760E0000F001F8013D0F0000F8010002A10F00000002080205100000080210026910000010021802511400001802200252140000200228025314000028023002541400003002380255140000380240027D150000400248027E15000048025002E115000050025802E215000058026002A11800006002680295C000006802000096C00000");
    }
}

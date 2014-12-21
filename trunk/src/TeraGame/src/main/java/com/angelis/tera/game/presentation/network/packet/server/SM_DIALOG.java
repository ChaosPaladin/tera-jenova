package com.angelis.tera.game.presentation.network.packet.server;

import java.nio.ByteBuffer;

import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.TeraServerPacket;
import com.angelis.tera.game.process.model.dialog.Dialog;
import com.angelis.tera.game.process.model.dialog.DialogButton;
import com.angelis.tera.game.process.model.quest.Quest;
import com.angelis.tera.game.process.model.quest.QuestReward;

public class SM_DIALOG extends TeraServerPacket {

    private final Dialog dialog;
    
    public SM_DIALOG(final Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    protected void writeImpl(final TeraGameConnection connection, final ByteBuffer byteBuffer) {
        final Quest quest = this.dialog.getQuest();
        int buttonsShift = 0;
        int rewardShift = 0;
        int itemsShift = 0;

        writeH(byteBuffer, (short) this.dialog.getDialogButtons().size()); //Buttons count
        
        buttonsShift = byteBuffer.position();
        writeH(byteBuffer, 0); //First button shift
        
        writeH(byteBuffer, (short) (quest == null ? 0 : 1));
        
        rewardShift = byteBuffer.position();
        writeH(byteBuffer, 0);

        writeUid(byteBuffer, this.dialog.getNpc());
        writeD(byteBuffer, this.dialog.getPage()); // page
        writeD(byteBuffer, quest == null ? this.dialog.getNpc().getId() : quest.getId()); // dialogId
        writeD(byteBuffer, this.dialog.getSpecial1()); // special1
        writeD(byteBuffer, 0); // special2
        writeD(byteBuffer, this.dialog.getPage()); // page
        writeD(byteBuffer, this.dialog.getUid()); //DialogUid
        writeB(byteBuffer, new byte[5]);
        writeD(byteBuffer, 1);
        writeB(byteBuffer, new byte[8]);
        writeB(byteBuffer, "FFFFFFFF");

        int i = 1;
        for (final DialogButton dialogButton : this.dialog.getDialogButtons()) {
            this.writeBufferPosition(byteBuffer, buttonsShift);

            writeH(byteBuffer, (short) byteBuffer.position());
            buttonsShift = byteBuffer.position();
            
            writeH(byteBuffer, 0);

            writeH(byteBuffer, (short) (byteBuffer.position() + 10));
            writeD(byteBuffer, i++);
            writeD(byteBuffer, dialogButton.getDialogIcon().value);
            writeS(byteBuffer, dialogButton.getText());
        }
        
        if (quest != null) {
            this.writeBufferPosition(byteBuffer, rewardShift);
            
            writeH(byteBuffer, byteBuffer.position());
            writeH(byteBuffer, 0);

            if (quest.getQuestRewards() != null && !quest.getQuestRewards().isEmpty()) {
                writeH(byteBuffer, (short) quest.getQuestRewards().size());
                itemsShift = byteBuffer.position();
                writeH(byteBuffer, 0);
            } else {
                writeD(byteBuffer, 0);
            }
            writeH(byteBuffer, quest.getQuestRewardType().value);
            writeD(byteBuffer, 0);
            writeH(byteBuffer, 0);
            
            writeD(byteBuffer, quest.getExperienceReward());
            writeD(byteBuffer, quest.getMoneyReward());
            
            writeD(byteBuffer, 0);
            writeD(byteBuffer, quest.getPolicyPointsReward());
            writeD(byteBuffer, quest.getAllianceContributionPointsReward());

            writeD(byteBuffer, 0);
            writeD(byteBuffer, quest.getReputationPointsReward());
            writeD(byteBuffer, quest.getCreditPointsReward());
            
            if (quest.getQuestRewards() != null && !quest.getQuestRewards().isEmpty()) {
                for (final QuestReward questReward : quest.getQuestRewards()) {
                    this.writeBufferPosition(byteBuffer, itemsShift);

                    writeH(byteBuffer, byteBuffer.position());
                    itemsShift = byteBuffer.position();
                    writeH(byteBuffer, 0);

                    writeD(byteBuffer, questReward.getItemId());
                    writeD(byteBuffer, questReward.getAmount());
                }
            }
        }
/*
        if (this.quest != null) {
            int itemsShift = 0;

            byteBuffer.Seek(rewardShift, SeekOrigin.Begin);
            writeH(byteBuffer, (short)byteBuffer.BaseStream.Length);
            byteBuffer.Seek(0, SeekOrigin.End);

            writeH(byteBuffer, (short)byteBuffer.BaseStream.Position);
            writeH(byteBuffer, 0);

            if (Reward != null && Reward.Items != null)
            {
                writeH(byteBuffer, (short) Reward.Items.Count);
                itemsShift = (int) byteBuffer.BaseStream.Position;
                writeH(byteBuffer, 0);
            }
            else
                writeD(byteBuffer, 0);

            writeD(byteBuffer, 0);
            writeD(byteBuffer, Quest.QuestRewardType == QuestRewardType.Choice ? 1 : 3); //1 Selectable reward //2 Unspecified reward //3 All
            writeD(byteBuffer, Quest.RewardExp);
            writeD(byteBuffer, Quest.RewardMoney);
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0); //Polici points
            writeD(byteBuffer, 0);
            writeD(byteBuffer, 0); //Reputation levels [exp]
            writeD(byteBuffer, 0); //Reputation

            if (Reward == null || Reward.Items == null)
                return;

            for (int x = 0; x < Reward.Items.Count; x++)
            {
                byteBuffer.Seek(itemsShift, SeekOrigin.Begin);
                writeH(byteBuffer, (short) byteBuffer.BaseStream.Length);
                byteBuffer.Seek(0, SeekOrigin.End);

                writeH(byteBuffer, (short)byteBuffer.BaseStream.Position);
                itemsShift = (int) byteBuffer.BaseStream.Position;
                writeH(byteBuffer, 0);

                writeD(byteBuffer, Reward.Items[x].Key);
                writeD(byteBuffer, Reward.Items[x].Value);
            }
        }*/
    }
}

package com.angelis.tera.game.process.model.chainedaction;

import com.angelis.tera.common.utils.Rnd;
import com.angelis.tera.game.presentation.network.packet.server.gather.SM_GATHERCRAFT_POINT;
import com.angelis.tera.game.presentation.network.packet.server.gather.SM_GATHER_PROGRESS;
import com.angelis.tera.game.presentation.network.packet.server.gather.SM_GATHER_START;
import com.angelis.tera.game.process.model.gather.Gather;
import com.angelis.tera.game.process.model.gather.enums.GatherResultEnum;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.gather.enums.GatherTypeEnum;
import com.angelis.tera.game.process.services.GatherService;
import com.angelis.tera.game.process.services.VisibleService;

public class GatherChainedAction extends AbstractChainedAction {

    private final Gather gather;
    
    private int progress;
    
    protected final byte HardProbOver = 0;
    protected final byte HardProbUnder = 50;
    protected final byte NormalProbOver = 50;
    protected final byte NormalProbUnder = 75;
    protected final byte EasyProbOver = 75;
    protected final byte EasyProbUnder = 101;
    
    public GatherChainedAction(final Player owner, final Gather gather) {
        super(owner);
        this.gather = gather;
    }
    
    @Override
    public void onStart() {
        VisibleService.getInstance().sendPacketForVisible(this.owner, new SM_GATHER_START(this.owner, this.gather));
        this.owner.getConnection().sendPacket(new SM_GATHERCRAFT_POINT(this.owner));
        this.gather.setGatherer(this.owner);
    }

    @Override
    public void onStep() {
        // TODO
        this.progress += Rnd.get(0, 50/*(100-gather.getTemplate().getGrade())*/);
        if (this.progress > 100) {
            this.progress = 100;
            this.complete = true;
        }
        
        this.owner.getConnection().sendPacket(new SM_GATHER_PROGRESS(this.progress));
        
        long next = 1000;
        if (this.progress < 100) {
            next = Rnd.get(1000, 2000);
        }
        this.next(next);
    }

    @Override
    public void onEnd() {
        GatherResultEnum gatherResult = GatherResultEnum.FAILED;
        
        int chance = Rnd.get(100, 100);
        switch (this.gather.getTemplate().getGatherType()) {
            case QUEST:
                chance = 100;
            break;
            
            default:
                /*switch (this.gather.getTemplate().getGrade()) {
                    case 1:
                        // TODO
                    break;
                    
                    case 2:
                        // TODO
                    break;
                    
                    case 3:
                        // TODO
                    break;
                }*/
        }

        if (Rnd.chance(chance)) {
            gatherResult = GatherResultEnum.NORMAL;
            this.gather.getController().gather(this.owner);
            GatherService.getInstance().learnGather(this.owner, GatherTypeEnum.PLANT, 1);// TODO
        }
        
        this.complete(gatherResult);
    }
    
    @Override
    public void onCancel() {
        this.complete(GatherResultEnum.RUPTED);
    }
    
    @Override
    public final boolean onCheck() {
        if (this.owner.getGatherCraftPoints() < 10) {
            return false;
        }

        if (this.gather.getRemainingGather() == 0) {
            return false;
        }
        
        if (this.gather.getGatherer() == null) {
            return true;
        }
        
        if (this.gather.getGatherer().getGroup() == null) {
            return false;
        }
        
        return this.gather.getGatherer().getGroup().equals(this.owner.getGroup());
    }
    
    private final void complete(final GatherResultEnum gatherResult) {
        this.owner.addGatherCraftPoints(-this.gather.getTemplate().getGatherCostPoint());
        this.gather.getController().endGather(gatherResult);
    }
}

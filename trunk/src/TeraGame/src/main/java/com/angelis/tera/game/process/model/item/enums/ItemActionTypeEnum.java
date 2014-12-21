package com.angelis.tera.game.process.model.item.enums;

import com.angelis.tera.game.network.SystemMessages;
import com.angelis.tera.game.network.packet.server.SM_ITEM_START_COOLTIME;
import com.angelis.tera.game.process.model.abnormality.Abnormality;
import com.angelis.tera.game.process.model.action.Action;
import com.angelis.tera.game.process.model.campfire.CampFire;
import com.angelis.tera.game.process.model.campfire.enums.CampFireStatusEnum;
import com.angelis.tera.game.process.model.campfire.enums.CampFireTypeEnum;
import com.angelis.tera.game.process.model.item.Item;
import com.angelis.tera.game.process.model.item.ItemAction;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.visible.WorldPosition;
import com.angelis.tera.game.services.ActionService;
import com.angelis.tera.game.services.ItemService;
import com.angelis.tera.game.services.SkillService;
import com.angelis.tera.game.services.SpawnService;
import com.angelis.tera.game.utils.Geom;

public enum ItemActionTypeEnum {
    CAST_SKILL {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            final Action action = new Action();
            action.setStartPosition(player.getWorldPosition().clone());
            action.setTarget(player);
            action.setSkillId(itemAction.getSkillId());
            action.setStage(0);

            ActionService.getInstance().onActionStart(player, action);
            ActionService.getInstance().onActionResults(player, action);
            ActionService.getInstance().onActionEnd(player, action);
            return true;
        }
    },
    LEARN_SKILL {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            SkillService.getInstance().learnSkill(player, itemAction.getSkillId(), itemAction.getSkillLevel());
            return true;
        }
    },
    SPAWN_CAMPFIRE {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            if (!ItemService.getInstance().checkCanSpawnFireCamp(player, worldPosition)) {
                player.getConnection().sendPacket(SystemMessages.THERE_IS_ANOTHER_FIRECAMP_NEAR_HERE());
                return false;
            }

            final CampFire campFire = new CampFire();
            campFire.setCampFireType(CampFireTypeEnum.NORMAL);
            campFire.setCampFireStatus(CampFireStatusEnum.NORMAL);
            campFire.setRate(itemAction.getRate());
            campFire.setWorldPosition(new WorldPosition(Geom.getNormal(worldPosition.getHeading()).multiple(50).add(worldPosition.getPoint3D()), worldPosition.getMapId()));
            SpawnService.getInstance().spawnCampFire(campFire);

            return true;
        }
    },
    APPLY_EFFECT {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            // TODO Auto-generated method stub
            return false;
        }
    },
    READ {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            // TODO Auto-generated method stub
            return false;
        }
    },
    BURN_CHARM {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            if (player.getActiveCampFire() == null) {
                player.getConnection().sendPacket(SystemMessages.CHARM_CAN_ONLY_BE_USED_NEAR_CAMPFIRE());
                return false;
            }
            
            if (player.getActiveCampFire().getCharms().size() == 3) {
                player.getConnection().sendPacket(SystemMessages.TOO_MANY_ITEMS_ARE_BEING_USED_IN_THIS_CAMPFIRE_TRY_AGAIN_LATER());
                return false;
            }
            
            // TODO
            player.getActiveCampFire().addCharm(item.getId());
            
            final int cooltime = 10;
            player.getConnection().sendPacket(new SM_ITEM_START_COOLTIME(item.getId(), cooltime));
            
            return true;
        }
    },
    DRINK_POTION {
        @Override
        public boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition) {
            final Abnormality abnormality = new Abnormality(1000);
            abnormality.setHpModifier(itemAction.getHpGain());
            abnormality.setMpModifier(itemAction.getMpGain());
            abnormality.setSpeedModifier(itemAction.getSpeedGain());
            abnormality.setCaster(player);
            abnormality.setTarget(player);
            abnormality.setTimeLeft(itemAction.getDuration());
            SkillService.getInstance().applyAbnormality(player, abnormality);
            return true;
        }
    };

    public abstract boolean doAction(final Item item, final ItemAction itemAction, final Player player, final WorldPosition worldPosition);
}

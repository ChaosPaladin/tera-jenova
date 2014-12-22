package com.angelis.tera.game.process.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.log4j.Logger;

import com.angelis.tera.common.services.AbstractService;
import com.angelis.tera.common.utils.Pair;
import com.angelis.tera.game.domain.entity.xml.SkillEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerClassSkillsEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerClassSkillsEntityHolder;
import com.angelis.tera.game.domain.entity.xml.players.PlayerRaceSkillsEntity;
import com.angelis.tera.game.domain.entity.xml.players.PlayerRaceSkillsEntityHolder;
import com.angelis.tera.game.presentation.network.SystemMessages;
import com.angelis.tera.game.presentation.network.connection.TeraGameConnection;
import com.angelis.tera.game.presentation.network.packet.server.SM_ABNORMALITY_BEGIN;
import com.angelis.tera.game.presentation.network.packet.server.SM_CREATURE_INSTANCE_ARROW;
import com.angelis.tera.game.presentation.network.packet.server.SM_PLAYER_SKILL_LIST;
import com.angelis.tera.game.presentation.network.packet.server.SM_SKILL_START_COOLTIME;
import com.angelis.tera.game.process.model.abnormality.Abnormality;
import com.angelis.tera.game.process.model.creature.AbstractCreature;
import com.angelis.tera.game.process.model.mount.Mount;
import com.angelis.tera.game.process.model.player.Player;
import com.angelis.tera.game.process.model.player.SkillList;
import com.angelis.tera.game.process.model.player.enums.PlayerClassEnum;
import com.angelis.tera.game.process.model.player.enums.RaceEnum;
import com.angelis.tera.game.process.model.skill.Skill;
import com.angelis.tera.game.process.model.skill.SkillArgs;
import com.angelis.tera.game.process.tasks.CreatureEndAbnormality;

public class SkillService extends AbstractService {

    /** LOGGER */
    private static Logger log = Logger.getLogger(SkillService.class.getName());

    private final Map<PlayerClassEnum, List<Skill>> classSkills = new FastMap<>();
    private final Map<RaceEnum, List<Skill>> raceSkills = new FastMap<>();

    @Override
    public void onInit() {
        for (final PlayerClassSkillsEntity playerClassSkillsEntity : XMLService.getInstance().getEntity(PlayerClassSkillsEntityHolder.class).getPlayerClassSkills()) {
            final List<Skill> skills = new FastList<>();
            for (final SkillEntity skillEntity : playerClassSkillsEntity.getSkills()) {
                final Skill skill = new Skill();
                skill.setSkillId(skillEntity.getId());
                skill.setLevel(skillEntity.getRequiredLevel());

                skills.add(skill);
            }

            classSkills.put(playerClassSkillsEntity.getTargetClass(), skills);
        }
        XMLService.getInstance().clearEntity(PlayerClassSkillsEntityHolder.class);

        for (final PlayerRaceSkillsEntity playerRaceSkillsEntity : XMLService.getInstance().getEntity(PlayerRaceSkillsEntityHolder.class).getPlayerRaceSkills()) {
            final List<Skill> skills = new FastList<>();
            for (final SkillEntity skillEntity : playerRaceSkillsEntity.getSkills()) {
                final Skill skill = new Skill();
                skill.setSkillId(skillEntity.getId());
                skill.setLevel(skillEntity.getRequiredLevel());

                skills.add(skill);
            }

            raceSkills.put(playerRaceSkillsEntity.getTargetRace(), skills);
        }
        XMLService.getInstance().clearEntity(PlayerRaceSkillsEntityHolder.class);

        log.info("SkillService started");
    }

    @Override
    public void onDestroy() {
        classSkills.clear();
        raceSkills.clear();

        log.info("SkillService started");
    }

    public void onPlayerSkillUse(final Player player, final SkillArgs skillArgs) {
        final Mount mount = MountService.getInstance().getMountBySkillId(skillArgs.getSkillId());
        if (mount != null) {
            this.sendSkillCoolTime(player, skillArgs.getSkillId(), 1000);
            MountService.getInstance().processMount(player, mount);
        } else {
            BattleService.getInstance().onPlayerAttack(player, skillArgs);
        }
    }

    public void onPlayerSkillUse(final Player player, final int skillId) {

    }

    public void onPlayerSkillInstanceUse(final Player player, final SkillArgs skillArgs) {
        player.getConnection().sendPacket(new SM_CREATURE_INSTANCE_ARROW(player, skillArgs.getEndPosition(), skillArgs.getSkillId()));
        BattleService.getInstance().onPlayerAttack(player, skillArgs);
        // TODO send new mp
    }

    public void onPlayerSkillCancel(final Player player, final int skillId, final int type) {
        BattleService.getInstance().onPlayerAttackRelease(player, skillId, type);
    }

    public void onPlayerCreate(final Player player) {
        final SkillList skillList = new SkillList();
        skillList.addSkills(classSkills.get(player.getPlayerClass()));
        skillList.addSkills(raceSkills.get(player.getRace()));

        player.setSkillList(skillList);
    }

    public void learnSkill(final Player player, final Integer skillId, final Integer level) {
        if (player.getSkillList().getSkillById(skillId) != null) {
            return;
        }

        final Skill skill = new Skill();
        skill.setSkillId(skillId);
        skill.setLevel(1);
        player.getSkillList().learnSkill(skill);

        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(SystemMessages.YOU_HAVE_LEARNED_SKILL(String.valueOf(skillId), "112"));
        connection.sendPacket(new SM_PLAYER_SKILL_LIST(player));
    }

    public void unlearnSkill(final Player player, final Integer skillId) {
        final Skill skill = player.getSkillList().getSkillById(skillId);
        if (skill == null) {
            return;
        }

        player.getSkillList().unlearnSkill(skill);
        
        final TeraGameConnection connection = player.getConnection();
        connection.sendPacket(new SM_PLAYER_SKILL_LIST(player));
    }

    public void applyAbnormality(final AbstractCreature creature, final Abnormality abnormality) {
        VisibleService.getInstance().sendPacketForVisible(creature, new SM_ABNORMALITY_BEGIN(abnormality), false);
        ThreadPoolService.getInstance().scheduleTask(new CreatureEndAbnormality(new Pair<AbstractCreature, Abnormality>(creature, abnormality)), 0, TimeUnit.SECONDS);
    }

    private void sendSkillCoolTime(final Player player, final int skillId, final int cooltime) {
        player.getConnection().sendPacket(new SM_SKILL_START_COOLTIME(skillId, cooltime));
    }

    /** SINGLETON */
    public static SkillService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final SkillService instance = new SkillService();
    }
}

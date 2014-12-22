package com.angelis.tera.game.process.command;

public enum AdminErrorMessageEnum  {
    TARGET_NOT_FOUND("common.errors.admin.target.not_found"),
    TARGET_HAS_MORE_RIGHTS_THAN_YOU("common.errors.admin.target.more_right_than_you"),
    THIS_MOUNT_SKILL_DOES_NOT_EXIST("common.errors.admin.mount.skill.does_not_exist"),
    TARGET_HAS_NO_MOUNT("common.errors.admin.target.has_no_mount"),
    CREATURE_WITH_THIS_ID_NOT_FOUND("common.errors.admin.creature.with_this_id_not_found"),
    QUEST_WITH_THIS_ID_NOT_FOUND("common.errors.admin.quest.with_this_id_not_found")
    ;
    
    public final String key;
    
    AdminErrorMessageEnum(final String key) {
        this.key = key;
    }
}

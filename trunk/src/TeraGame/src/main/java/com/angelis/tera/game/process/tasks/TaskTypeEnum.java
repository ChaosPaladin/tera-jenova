package com.angelis.tera.game.process.tasks;

public enum TaskTypeEnum {
    PLAYER_QUIT,
    PLAYER_QUIT_TO_PLAYER_LIST,
    PLAYER_DELETE,
    PLAYER_AUTO_SAVE,
    PLAYER_CHECK_AGGRO,
    PLAYER_STATS_MODIFIER,
    PLAYER_ABNORMALITY_END,

    TERA_OBJECT_RESPAWN,
    TERA_OBJECT_DESPAWN,
    
    ITEM_FREE_LOOT,
    
    PEGASUS_FLY_START,
    PEGASUS_FLY_END,
    
    REQUEST_AUTO_CANCEL,
    ;
}

package com.angelis.tera.game.presentation.network.enums;

public enum CheckCharacterNameResponse {
    
    OK(0),
    UNAVAILABLE_LETTER(1),
    TOO_LONG(2),
    NOT_LONG_ENOUGH(3),
    MUST_BE_ALPHABETIC(4),
    CONTAINS_UNAPPROPRIATED_WORD(5),
    MUST_NOT_CONTAINS_SPACE(6),
    MUST_NOT_CONTAINS_BANNED_WORD(7),
    INVALID_NAME(8),
    //9 You cant use incompleted Korean in CHaracter name.
    //10 You cant use axpanded Korean in Character name.
    CHARACTER_NAME_CONTAINS_UNAVAILABLE_WORD(11);
    //12 You cant use Chinese characters in Character name.
    
    public final int value;
    
    CheckCharacterNameResponse(final int value) {
        this.value = value;
    }
}

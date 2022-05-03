package dev.rollczi.sandboy.config.composers;

import panda.std.Result;

public class CharacterComposer implements LiteComposer<Character> {

    @Override
    public Result<Character, Exception> deserialize(String character) {
        if (character.length() != 1) {
            return Result.error(new UnsupportedOperationException("Cant deserialize character! '" + character + "' isn't a character"));
        }

        return Result.ok(character.charAt(0));
    }

    @Override
    public Result<String, Exception> serialize(Character character) {
        return Result.ok(character.toString());
    }

}

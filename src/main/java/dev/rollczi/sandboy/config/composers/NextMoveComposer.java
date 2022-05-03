package dev.rollczi.sandboy.config.composers;

import dev.rollczi.sandboy.replacer.NextMove;
import panda.std.Result;
import panda.utilities.text.Joiner;

import java.lang.reflect.Field;

public class NextMoveComposer implements LiteComposer<NextMove> {

    @Override
    public Result<NextMove, Exception> deserialize(String name) {
        return Result.attempt(ReflectiveOperationException.class, () -> (NextMove) NextMove.class.getField(name).get(null))
                .mapErr(e -> new UnsupportedOperationException("Cant deserialize \"" + name + "\"! Use " + Joiner.on(", ").join(NextMove.class.getFields(), Field::getName)));
    }

    @Override
    public Result<String, Exception> serialize(NextMove nextMove) {
        for (Field field : NextMove.class.getFields()) {
            try {
                if (field.get(null).equals(nextMove)) {
                    return Result.ok(field.getName());
                }
            } catch (IllegalAccessException exception) {
                return Result.error(new RuntimeException("Internal error. Cant serialize NextMove: " + nextMove, exception));
            }
        }

        return Result.error(new RuntimeException("Internal error. Cant serialize NextMove: " + nextMove));
    }

}
package dev.rollczi.sandboy.config.composers;

import panda.std.Result;

public class ByteComposer implements LiteComposer<Byte> {

    @Override
    public Result<Byte, Exception> deserialize(String name) {
        return Result.attempt(NumberFormatException.class, () -> Byte.parseByte(name));
    }

    @Override
    public Result<String, Exception> serialize(Byte aByte) {
        return Result.ok(aByte.toString());
    }

}
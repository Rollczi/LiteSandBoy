package dev.rollczi.sandboy.config.composers;

import dev.rollczi.sandboy.replacer.validator.HeightRangeValidator;
import panda.std.Result;

public class HeightRangeValidatorComposer implements LiteComposer<HeightRangeValidator> {

    @Override
    public Result<HeightRangeValidator, Exception> deserialize(String text) {
        String[] split = text.split(" ");

        if (split.length != 3 || !split[1].equals("->")) {
            return Result.error(new UnsupportedOperationException("Cannot deserialize " + text));
        }

        int min = Integer.parseInt(split[0]);
        int max = Integer.parseInt(split[2]);

        return Result.ok(new HeightRangeValidator(min, max));
    }

    @Override
    public Result<String, Exception> serialize(HeightRangeValidator validator) {
        return Result.ok(validator.getMin() + " -> " + validator.getMax());
    }

}
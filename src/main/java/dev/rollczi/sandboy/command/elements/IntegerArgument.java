/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.sandboy.command.elements;

import dev.rollczi.litecommands.inject.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.sandboy.LiteSandBoy;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

public class IntegerArgument implements SingleArgumentHandler<Integer> {

    private final LiteSandBoy plugin;

    public IntegerArgument(LiteSandBoy plugin) {
        this.plugin = plugin;
    }

    @Override
    public Integer parse(String argument) throws ValidationCommandException {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
                .orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, plugin.getMessagesConfig().noFoundNumber));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.asList("1", "2", "4", "8", "16", "32", "64");
    }

}

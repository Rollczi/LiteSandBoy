/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.sandboy.command.elements;

import dev.rollczi.litecommands.inject.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.replacer.ReplacerData;

import java.util.List;
import java.util.stream.Collectors;

public class ReplacerDataArgument implements SingleArgumentHandler<ReplacerData> {

    private final LiteSandBoy plugin;

    public ReplacerDataArgument(LiteSandBoy plugin) {
        this.plugin = plugin;
    }

    @Override
    public ReplacerData parse(String argument) throws ValidationCommandException {
        return plugin.getReplacerService().getReplacerData(argument)
                .orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, plugin.getMessagesConfig().noFoundReplacer));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return plugin.getReplacerService().getReplacerDates()
                .stream()
                .map(ReplacerData::getName)
                .collect(Collectors.toList());
    }

}

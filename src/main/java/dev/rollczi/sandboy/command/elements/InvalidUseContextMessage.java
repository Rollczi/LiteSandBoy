package dev.rollczi.sandboy.command.elements;

import dev.rollczi.litecommands.component.ExecutionResult;
import dev.rollczi.litecommands.component.LiteComponent;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.placeholder.Placeholders;
import panda.utilities.text.Formatter;

public class InvalidUseContextMessage implements ValidationInfo.ContextMessage {

    private final LiteSandBoy liteSandBoy;

    public InvalidUseContextMessage(LiteSandBoy liteSandBoy) {
        this.liteSandBoy = liteSandBoy;
    }

    @Override
    public String message(LiteComponent.ContextOfResolving context, ExecutionResult result) {
        return liteSandBoy.getMessagesConfig().invalidUseOfCommand;
    }

}

package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import panda.utilities.text.Formatter;

public interface MessageContainer {

    Formatter getFormatter();

    String getMessage(MessagesConfig config, ReplacerContext context);

}

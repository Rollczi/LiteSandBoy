package dev.rollczi.sandboy.command;

import dev.rollczi.litecommands.LiteSender;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;

@Section(route = "sandboy", aliases = "farmer", priority = 0)
public class ReloadCommand {

    @Execute(route = "reload")
    @Permission("dev.rollczi.sandboy.reload")
    void executeReload(MessagesConfig config, LiteSender sender, LiteSandBoy sandBoy) {
        sandBoy.reloadConfigurations();
        sender.sendMessage(config.reloadMessage);
    }

}
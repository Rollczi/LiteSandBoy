package dev.rollczi.sandboy.command;

import dev.rollczi.litecommands.LiteSender;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.sandboy.chat.MicroChat;
import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.replacer.ReplacerData;
import dev.rollczi.sandboy.replacer.ReplacerService;
import org.bukkit.command.CommandSender;

import java.util.Collection;

import static dev.rollczi.sandboy.placeholder.Placeholders.REPLACER;

@Section(route = "sandboy", aliases = { "farmer", "${command.sandboy}" } , priority = 2)
@Permission("dev.rollczi.sandboy")
public class SandBoyInfoCommand {

    @Execute
    void executeInfo(MessagesConfig config, LiteSender sender) {
        config.infoMessage.forEach(sender::sendMessage);
    }

    @Execute(route = "help")
    @Permission("dev.rollczi.sandboy.help")
    void executeHelp(MessagesConfig config, LiteSender sender) {
        config.helpMessage.forEach(sender::sendMessage);
    }

    @Execute(route = "list")
    @Permission("dev.rollczi.sandboy.list")
    void executeList(MessagesConfig config, CommandSender sender, ReplacerService replacerService) {
        Collection<ReplacerData> dates = replacerService.getReplacerDates();
        MicroChat chat = new MicroChat(sender);

        config.listMessageHeader.forEach(chat::send);
        dates.forEach(replacerData -> chat.send(config.listMessageElement, REPLACER.formatter(replacerData)));
        config.listMessageFooter.forEach(chat::send);
    }

}
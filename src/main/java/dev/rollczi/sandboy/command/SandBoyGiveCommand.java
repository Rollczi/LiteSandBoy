package dev.rollczi.sandboy.command;

import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.sandboy.chat.MicroChat;
import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.replacer.ReplacerData;
import dev.rollczi.sandboy.utils.InventoryUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Triple;

import static dev.rollczi.sandboy.placeholder.Placeholders.GIVE;

@Section(route = "sandboy", aliases = { "farmer" }, priority = 1)
@Permission("dev.rollczi.sandboy")
public class SandBoyGiveCommand {

    @Execute(route = "give", required = 3)
    @Permission("dev.rollczi.sandboy.give")
    void execute(MessagesConfig config, CommandSender sender, @Arg(0) ReplacerData data, @Arg(1) Player player, @Arg(2) Integer amount) {
        InventoryUtils.giveOrDrop(player, data.getItem().setAmount(amount));

        MicroChat senderChat = new MicroChat(sender, GIVE.formatter(Triple.of(data, player, amount)));
        MicroChat playerChat = new MicroChat(player, GIVE.formatter(Triple.of(data, sender, amount)));

        if (sender.equals(player)) {
            senderChat.send(config.giveMessageSame);
            return;
        }

        playerChat.send(config.giveMessage);
        senderChat.send(config.giveMessageSender);
    }

}
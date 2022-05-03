package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.chat.MicroChat;
import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.hook.FunnyGuildsHook;
import dev.rollczi.sandboy.hook.FunnyGuildsHookConfiguration;
import dev.rollczi.sandboy.placeholder.Placeholders;
import dev.rollczi.sandboy.replacer.validator.ReplacerValidator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import panda.std.Option;
import panda.std.Result;
import panda.utilities.text.Formatter;

import static dev.rollczi.sandboy.placeholder.Placeholders.REPLACER;

public class ReplacerPlaceListener implements Listener {

    private final LiteSandBoy plugin;

    public ReplacerPlaceListener(LiteSandBoy plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Option<ReplacerData> replacerOption = plugin.getReplacerService().getReplacer(event.getItemInHand());

        if (replacerOption.isEmpty()) {
            return;
        }

        PluginConfig config = plugin.getPluginConfig();
        MessagesConfig messages = plugin.getMessagesConfig();
        Location location = event.getBlock().getLocation();

        ReplacerData replacerData = replacerOption.get();
        Replacer replacer = replacerData.getReplacer();

        Player player = event.getPlayer();
        MicroChat playerChat = new MicroChat(player, REPLACER.formatter(replacerData));

        Option<FunnyGuildsHook> guildsHookOption = this.plugin.getHookService().getFunnyGuildsHook();

        if (guildsHookOption.isPresent()) {
            FunnyGuildsHookConfiguration funnyGuilds = config.funnyGuilds;
            FunnyGuildsHook guildsHook = guildsHookOption.get();

            boolean correctMember = !funnyGuilds.blockNoGuildMembers || guildsHook.hasGuild(player);
            boolean correctRegion = guildsHook.isInsideGuild(location)
                    ? funnyGuilds.blockOutsideGuild
                    : funnyGuilds.blockInsideGuild;

            if (!correctMember || !correctRegion) {
                playerChat.send(!correctMember ? funnyGuilds.blockNoGuildMember : funnyGuilds.blockRegionMessage);
                event.setCancelled(true);
                return;
            }
        }

        ReplacerContext startContext = ReplacerContext.staticContext(location.getBlock(), ReplacerValidator.NONE);
        Formatter startContextFormatter = Placeholders.CONTEXT.formatter(startContext);

        Result<Boolean, ReplacerContext> result = replacer.validFirstReplace(location);

        if (result.isErr()) {

            // change start context if start is invalid
            startContext = result.getError();
            startContextFormatter = Placeholders.CONTEXT.formatter(startContext);

            ReplacerValidator validator = startContext.getValidator();

            if (!(validator instanceof MessageContainer)) {
                return;
            }

            MessageContainer messageContainer = (MessageContainer) validator;

            playerChat.registerFormatters(startContextFormatter);
            playerChat.send(messageContainer.getMessage(messages, startContext), messageContainer.getFormatter());
            event.setCancelled(true);
            return;
        }

        playerChat.actionBar(messages.actionBarStart, startContextFormatter);

        replacer.replacer(location).subscribe(context -> {
            ReplacerValidator validator = context.getValidator();

            if (!player.isOnline()) {
                return;
            }

            playerChat.registerFormatters(Placeholders.CONTEXT.formatter(context));
            playerChat.actionBar(messages.actionBarEnd);

            if (validator instanceof MessageContainer) {
                MessageContainer messageContainer = (MessageContainer) validator;

                playerChat.send(messageContainer.getMessage(messages, context), messageContainer.getFormatter());
            }
        });
    }

}

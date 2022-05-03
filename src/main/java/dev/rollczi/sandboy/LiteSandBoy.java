package dev.rollczi.sandboy;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.valid.ValidationInfo;
import dev.rollczi.sandboy.command.ReloadCommand;
import dev.rollczi.sandboy.command.SandBoyGiveCommand;
import dev.rollczi.sandboy.command.SandBoyInfoCommand;
import dev.rollczi.sandboy.command.elements.IntegerArgument;
import dev.rollczi.sandboy.command.elements.InvalidUseContextMessage;
import dev.rollczi.sandboy.command.elements.PermissionContextMessage;
import dev.rollczi.sandboy.command.elements.PlayerArgument;
import dev.rollczi.sandboy.command.elements.ReplacerDataArgument;
import dev.rollczi.sandboy.config.ConfigLoader;
import dev.rollczi.sandboy.config.MessagesConfig;
import dev.rollczi.sandboy.config.PluginConfig;
import dev.rollczi.sandboy.crafting.service.Legacy1_12_R1vRecipeService;
import dev.rollczi.sandboy.crafting.service.Standard1_16_R1vRecipeService;
import dev.rollczi.sandboy.crafting.service.Legacy1_8_R1vRecipeService;
import dev.rollczi.sandboy.crafting.service.RecipeService;
import dev.rollczi.sandboy.hook.HookService;
import dev.rollczi.sandboy.replacer.ReplacerData;
import dev.rollczi.sandboy.replacer.ReplacerPlaceListener;
import dev.rollczi.sandboy.replacer.ReplacerService;
import dev.rollczi.sandboy.utils.ServerUtils;
import io.github.slimjar.app.builder.ApplicationBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

public class LiteSandBoy extends JavaPlugin {

    private static LiteSandBoy instance;

    private ConfigLoader configLoader;
    private ReplacerService replacerService;
    private RecipeService recipeService;
    private PluginConfig pluginConfig;
    private MessagesConfig messagesConfig;
    private HookService hookService;
    private LiteCommands liteCommands;

    @Override
    public void onEnable() {
        instance = this;

        try {
            ApplicationBuilder.appending("Lite-SandBoy").build();
        } catch (IOException | ReflectiveOperationException | URISyntaxException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }


        this.configLoader = new ConfigLoader(this);
        this.replacerService = new ReplacerService();
        this.recipeService = ServerUtils.is1_16vOrHigher()   ? new Standard1_16_R1vRecipeService(this)
                           : ServerUtils.is1_12vOrNoLegacy() ? new Legacy1_12_R1vRecipeService(this)
                                                             : new Legacy1_8_R1vRecipeService(this);

        this.reloadConfigurations();

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "lite-sandboy")
                .command(
                        SandBoyGiveCommand.class,
                        SandBoyInfoCommand.class,
                        ReloadCommand.class
                )
                .bind(PluginConfig.class, () -> pluginConfig)
                .bind(MessagesConfig.class, () -> messagesConfig)
                .bind(LiteSandBoy.class, this)
                .bind(ReplacerService.class, replacerService)
                .argument(Player.class, new PlayerArgument(this))
                .argument(Integer.class, new IntegerArgument(this))
                .argument(ReplacerData.class, new ReplacerDataArgument(this))
                .message(ValidationInfo.NO_PERMISSION, new PermissionContextMessage(this))
                .message(ValidationInfo.INVALID_USE, new InvalidUseContextMessage(this))
                .placeholder("${command.sandboy}", () -> pluginConfig.customName)
                .register();

        Bukkit.getPluginManager().registerEvents(new ReplacerPlaceListener(this), this);
    }

    public void reloadConfigurations() {
        this.pluginConfig = this.configLoader.load(PluginConfig.class, "config.yml");
        this.messagesConfig = this.configLoader.load(MessagesConfig.class, "messages.yml");
        this.pluginConfig.replacers.forEach(replacerService::loadFromCustomReplacerSection);
        this.recipeService.reset();
        this.pluginConfig.recipes.forEach(recipeService::load);
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public ReplacerService getReplacerService() {
        return replacerService;
    }

    public LiteCommands getLiteCommands() {
        return liteCommands;
    }

    public static LiteSandBoy getInstance() {
        return instance;
    }

}

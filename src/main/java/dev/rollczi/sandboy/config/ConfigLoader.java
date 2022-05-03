package dev.rollczi.sandboy.config;

import dev.rollczi.sandboy.config.composers.ByteComposer;
import dev.rollczi.sandboy.config.composers.CharacterComposer;
import dev.rollczi.sandboy.config.composers.EnchantmentComposer;
import dev.rollczi.sandboy.config.composers.HeightRangeValidatorComposer;
import dev.rollczi.sandboy.config.composers.MaterialDataComposer;
import dev.rollczi.sandboy.config.composers.NextMoveComposer;
import dev.rollczi.sandboy.config.composers.WhiteOrBlackListBlockValidatorComposer;
import dev.rollczi.sandboy.replacer.NextMove;
import dev.rollczi.sandboy.replacer.validator.HeightRangeValidator;
import dev.rollczi.sandboy.replacer.validator.WhiteOrBlackListBlockValidator;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.module.yaml.YamlLikeModule;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import panda.std.function.ThrowingFunction;

import java.io.File;
import java.io.Serializable;

public class ConfigLoader {

    private final Plugin plugin;
    private final Cdn cdn = Cdn.configure()
            .registerModule(new YamlLikeModule())
            .withComposer(NextMove.class, new NextMoveComposer())
            .withComposer(MaterialData.class, new MaterialDataComposer())
            .withComposer(byte.class, new ByteComposer())
            .withComposer(char.class, new CharacterComposer())
            .withComposer(Enchantment.class, new EnchantmentComposer())
            .withComposer(HeightRangeValidator.class, new HeightRangeValidatorComposer())
            .withComposer(WhiteOrBlackListBlockValidator.class, new WhiteOrBlackListBlockValidatorComposer())
            .build();

    public ConfigLoader(Plugin plugin) { this.plugin = plugin; }

    public <T extends Serializable> T load(Class<T> configurationClass, String fileName) {
        File folderPath = plugin.getDataFolder();
        File file = new File(folderPath, fileName);

        if (!folderPath.exists() && folderPath.mkdir()) {
            plugin.getLogger().info("Created data folder");
        }

        try {
            Resource resource = Source.of(file);
            T load = cdn.load(resource, configurationClass)
                    .orElseThrow(ThrowingFunction.identity());

            String render = cdn.render(load, resource)
                    .orElseThrow(ThrowingFunction.identity());

            return load;
        } catch (Exception exception) {
            throw new RuntimeException("Can't load " + fileName, exception);
        }
    }

}
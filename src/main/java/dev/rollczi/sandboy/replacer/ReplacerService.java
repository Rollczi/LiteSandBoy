package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.config.ItemSection;
import dev.rollczi.sandboy.replacer.implementations.FloorReplacer;
import dev.rollczi.sandboy.utils.ItemBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReplacerService {

    private final Map<String, ReplacerData> replacers = new ConcurrentHashMap<>();

    public void registerReplacer(String name, String displayName, Replacer replacer, ItemBuilder item) {
        replacers.put(name, new ReplacerData(name, displayName, replacer, item));
    }

    public Option<ReplacerData> unregisterReplacer(String name) {
        return Option.of(replacers.remove(name));
    }

    public Option<Replacer> getReplacer(String name) {
        return Option.of(replacers.get(name)).map(ReplacerData::getReplacer);
    }

    public Option<ReplacerData> getReplacerData(String name) {
        return Option.of(replacers.get(name));
    }

    public Option<ReplacerData> getReplacer(ItemStack item) {
        return PandaStream.of(replacers.values())
                .find(replacerData -> replacerData.getItem().isSimilar(item));
    }

    public Collection<Replacer> getReplacers() {
        return PandaStream.of(replacers.values())
                .map(ReplacerData::getReplacer)
                .toList();
    }

    public Collection<ReplacerData> getReplacerDates() {
        return replacers.values();
    }

    public List<ItemBuilder> getReplacersItems() {
        return PandaStream.of(replacers.values())
                .map(ReplacerData::getItem)
                .toList();
    }

    public RegisterConfiguration configuration() {
        return new RegisterConfiguration(this);
    }

    public void loadFromCustomReplacerSection(ReplacerConfiguration custom) {
        this.unregisterReplacer(custom.name);

        FloorReplacer replacer = new FloorReplacer(custom.block, custom.floorBlock,
                custom.nextMove, custom.delay, custom.blocksListValidator, custom.heightRangeValidator);


        ItemSection itemSection = custom.item;
        ItemBuilder item = new ItemBuilder(itemSection.type, 1, itemSection.data)
                .setName(itemSection.name)
                .addEnchantments(itemSection.enchantments)
                .setItemFlags(itemSection.itemFlags)
                .setLore(itemSection.lore);

        this.configuration()
                .name(custom.name)
                .displayName(custom.displayName)
                .replacer(replacer)
                .item(item)
                .register();
    }

    public static class RegisterConfiguration {

        private final ReplacerService replacerService;
        private String name;
        private String displayName;
        private Replacer replacer;
        private ItemBuilder item;

        public RegisterConfiguration(ReplacerService replacerService) {
            this.replacerService = replacerService;
        }

        public RegisterConfiguration name(String name) {
            this.name = name;
            return this;
        }

        public RegisterConfiguration displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public RegisterConfiguration replacer(Replacer replacer) {
            this.replacer = replacer;
            return this;
        }

        public RegisterConfiguration item(ItemStack item) {
            this.item = ItemBuilder.of(item);
            return this;
        }

        public RegisterConfiguration item(ItemBuilder item) {
            this.item = ItemBuilder.of(item);
            return this;
        }

        public void register() {
            Validate.notNull(name, "The name is null");
            Validate.notNull(replacer, "The replacer is null");
            Validate.notNull(item, "The item is null");

            if (displayName == null) {
                displayName = name;
            }

            replacerService.registerReplacer(name, displayName, replacer, item);
        }

    }

}

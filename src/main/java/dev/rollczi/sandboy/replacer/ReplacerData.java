package dev.rollczi.sandboy.replacer;

import dev.rollczi.sandboy.utils.ItemBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

public class ReplacerData {

    private final String name;
    private String displayName;
    private final Replacer replacer;
    private ItemBuilder item;

    public ReplacerData(String name, String displayName, Replacer replacer, ItemBuilder item) {
        this.name = name;
        this.displayName = displayName;
        this.replacer = replacer;
        this.item = ItemBuilder.of(item);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Replacer getReplacer() {
        return replacer;
    }

    public ItemBuilder getItem() {
        return ItemBuilder.of(item);
    }

    public void setItem(ItemBuilder item) {
        Validate.notNull(item, "The item is null");
        this.item = ItemBuilder.of(item);
    }

    public void setItem(ItemStack item) {
        Validate.notNull(item, "The item is null");
        this.item = ItemBuilder.of(item);
    }

}

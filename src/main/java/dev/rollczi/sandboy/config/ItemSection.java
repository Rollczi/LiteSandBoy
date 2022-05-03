package dev.rollczi.sandboy.config;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import panda.utilities.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Contextual
public class ItemSection implements Serializable {

    public String name = StringUtils.EMPTY;
    public Material type = Material.STONE;
    public byte data = 0;
    public List<String> lore = new ArrayList<>();
    public Map<Enchantment, Integer> enchantments = new HashMap<>();
    @Description("# Item flags: HIDE_ENCHANTS, HIDE_ATTRIBUTES, HIDE_UNBREAKABLE, HIDE_DESTROYS, HIDE_PLACED_ON, HIDE_POTION_EFFECTS, HIDE_DYE")
    public List<ItemFlag> itemFlags = new ArrayList<>();

    public ItemSection() {
    }

    public ItemSection(String name, Material type, List<String> lore) {
        this.name = name;
        this.type = type;
        this.lore = lore;
    }

    public ItemSection enchant(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

}

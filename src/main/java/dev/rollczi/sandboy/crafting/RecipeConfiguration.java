package dev.rollczi.sandboy.crafting;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import panda.std.stream.PandaStream;
import panda.utilities.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Contextual
public class RecipeConfiguration {

    public String name = StringUtils.EMPTY;
    public Map<Character, MaterialData> crafting = new HashMap<>();

    public RecipeConfiguration() {
    }

    public RecipeConfiguration(String name, Map<CraftingSlot, MaterialData> crafting) {
        this.name = name;
        this.crafting = PandaStream.of(crafting.entrySet())
                .toMap(entry -> entry.getKey().getKey(), Map.Entry::getValue);
    }

}

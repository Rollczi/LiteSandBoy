package dev.rollczi.sandboy.config;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.sandboy.crafting.CraftingSlot;
import dev.rollczi.sandboy.crafting.RecipeConfiguration;
import dev.rollczi.sandboy.hook.FunnyGuildsHookConfiguration;
import dev.rollczi.sandboy.replacer.NextMove;
import dev.rollczi.sandboy.replacer.ReplacerConfiguration;
import dev.rollczi.sandboy.replacer.validator.HeightRangeValidator;
import dev.rollczi.sandboy.replacer.validator.WhiteOrBlackListBlockValidator;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.material.MaterialData;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PluginConfig implements Serializable {

    @Description("# -- Command --")
    public String customName = "custom-farmer";

    @Description("# -- FunnyGuilds hook --")
    public FunnyGuildsHookConfiguration funnyGuilds = new FunnyGuildsHookConfiguration();

    @Description("# -- Konfiguracja replacerów --")
    public List<ReplacerConfiguration> replacers = Arrays.asList(
            new ReplacerConfiguration(
                    "boyfarmer",
                    "&9BoyFarmer",
                    Material.OBSIDIAN,
                    Material.OBSIDIAN,
                    NextMove.DOWN,
                    new HeightRangeValidator(10, 130),
                    new WhiteOrBlackListBlockValidator(Arrays.asList(Material.BEDROCK, Material.WATER, Material.LAVA), true),
                    new ItemSection("&9BoyFarmer", Material.OBSIDIAN, Arrays.asList("&7text1", "&atext2"))
                            .enchant(Enchantment.DURABILITY, 10)
            ),
            new ReplacerConfiguration(
                    "sandfarmer",
                    "&eSandFarmer",
                    Material.SAND,
                    Material.SANDSTONE,
                    NextMove.UP,
                    new HeightRangeValidator(20, 130),
                    new WhiteOrBlackListBlockValidator(Collections.singletonList(Material.AIR), false),
                    new ItemSection("&9SandFarmer", Material.SAND, Arrays.asList("&7text1", "&atext2"))
                            .enchant(Enchantment.DURABILITY, 10)
            ),
            new ReplacerConfiguration(
                    "kopacz",
                    "&8Kopacz",
                    Material.AIR,
                    Material.AIR,
                    NextMove.DOWN,
                    new HeightRangeValidator(0, 255),
                    new WhiteOrBlackListBlockValidator(Collections.singletonList(Material.BEDROCK), true),
                    new ItemSection("&9Kopacz", Material.BEDROCK, Arrays.asList("&7text1", "&atext2"))
                            .enchant(Enchantment.DURABILITY, 10)
            )
    );

    @Description("# -- Konfiguracja craftingów --")
    public List<RecipeConfiguration> recipes = Collections.singletonList(new RecipeConfiguration("boyfarmer", new ImmutableMap.Builder<CraftingSlot, MaterialData>()
            .put(CraftingSlot.TOP_1, new MaterialData(Material.REDSTONE, (byte) 0))
            .put(CraftingSlot.TOP_2, new MaterialData(Material.OBSIDIAN, (byte) 0))
            .put(CraftingSlot.TOP_3, new MaterialData(Material.REDSTONE, (byte) 0))
            .put(CraftingSlot.MIDDLE_4, new MaterialData(Material.OBSIDIAN, (byte) 0))
            .put(CraftingSlot.MIDDLE_5, new MaterialData(Material.DIAMOND, (byte) 0))
            .put(CraftingSlot.MIDDLE_6, new MaterialData(Material.OBSIDIAN, (byte) 0))
            .put(CraftingSlot.BOTTOM_7, new MaterialData(Material.REDSTONE, (byte) 0))
            .put(CraftingSlot.BOTTOM_8, new MaterialData(Material.OBSIDIAN, (byte) 0))
            .put(CraftingSlot.BOTTOM_9, new MaterialData(Material.REDSTONE, (byte) 0))
            .build()));

}

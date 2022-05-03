package dev.rollczi.sandboy.crafting.service;

import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.crafting.CraftingSlot;
import dev.rollczi.sandboy.crafting.RecipeConfiguration;
import dev.rollczi.sandboy.replacer.ReplacerData;
import dev.rollczi.sandboy.replacer.ReplacerService;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import panda.std.Option;

import java.util.HashMap;
import java.util.Map;

public class Standard1_16_R1vRecipeService implements RecipeService {

    private final Map<NamespacedKey, ShapedRecipe> recipes = new HashMap<>();
    private final LiteSandBoy sandBoy;

    public Standard1_16_R1vRecipeService(LiteSandBoy sandBoy) {
        this.sandBoy = sandBoy;
    }

    @Override
    public void load(RecipeConfiguration recipeConfiguration) {
        //TODO: START - same 1 {@link CraftingRecipeService}
        String name = recipeConfiguration.name;
        ReplacerService replacerService = sandBoy.getReplacerService();
        Option<ReplacerData> option = replacerService.getReplacerData(name);

        if (option.isEmpty()) {
            return;
        }

        ReplacerData data = option.get();
        ItemStack item = data.getItem().build();
        //TODO: END - same 1 {@link CraftingRecipeService}

        NamespacedKey namespace = new NamespacedKey(sandBoy, name);

        if (recipes.containsKey(namespace)) {
            recipes.remove(namespace);
            Bukkit.removeRecipe(namespace);
        }

        ShapedRecipe shapedRecipe = new ShapedRecipe(namespace, item);

        shapedRecipe.shape(CraftingSlot.getShape());

        recipeConfiguration.crafting.forEach(shapedRecipe::setIngredient);
        recipes.put(namespace, shapedRecipe);
        Bukkit.addRecipe(shapedRecipe);
    }

    @Override
    public void reset() {
        recipes.forEach((namespacedKey, shapedRecipe) -> Bukkit.removeRecipe(namespacedKey));
        recipes.clear();
    }

}

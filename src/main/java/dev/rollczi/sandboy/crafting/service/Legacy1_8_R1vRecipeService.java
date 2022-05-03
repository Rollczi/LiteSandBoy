package dev.rollczi.sandboy.crafting.service;

import dev.rollczi.sandboy.LiteSandBoy;
import dev.rollczi.sandboy.crafting.CraftingSlot;
import dev.rollczi.sandboy.crafting.RecipeConfiguration;
import dev.rollczi.sandboy.replacer.ReplacerData;
import dev.rollczi.sandboy.replacer.ReplacerService;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import panda.std.Option;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Legacy1_8_R1vRecipeService implements RecipeService {

    private final Map<String, ShapedRecipe> recipes = new HashMap<>();
    private final LiteSandBoy sandBoy;

    public Legacy1_8_R1vRecipeService(LiteSandBoy sandBoy) {
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

        if (recipes.containsKey(name)) {
            return; // no reset - Legacy
        }

        ShapedRecipe shapedRecipe = new ShapedRecipe(item); // no namespace - Legacy

        //TODO: START - same 2 {@link CraftingRecipeService}
        shapedRecipe.shape(CraftingSlot.getShape());

        recipeConfiguration.crafting.forEach(shapedRecipe::setIngredient);
        recipes.put(name, shapedRecipe);
        Bukkit.addRecipe(shapedRecipe);
        //TODO: END - same 2 {@link CraftingRecipeService}
    }

    @Override
    public void reset() {
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();

        while (recipeIterator.hasNext()) {
            Recipe next = recipeIterator.next();

            if (next == null) {
                continue;
            }

            for (ShapedRecipe shapedRecipe : recipes.values()) {
                if (!next.getResult().isSimilar(shapedRecipe.getResult())) {
                    continue;
                }

                recipeIterator.remove();
                break;
            }
        }
    }

}

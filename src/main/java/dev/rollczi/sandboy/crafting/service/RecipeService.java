package dev.rollczi.sandboy.crafting.service;

import dev.rollczi.sandboy.crafting.RecipeConfiguration;

public interface RecipeService {

    void load(RecipeConfiguration recipeConfiguration);

    void reset();

}

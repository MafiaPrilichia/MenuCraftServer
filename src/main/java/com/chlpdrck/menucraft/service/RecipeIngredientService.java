package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientDto;

public interface RecipeIngredientService {
    RecipeIngredientDto createRecipeIngredient(RecipeIngredientCRUDDto recipeIngredientCRUDDto, String username);
}

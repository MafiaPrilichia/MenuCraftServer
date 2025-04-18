package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientShowDto;

import java.util.List;

public interface RecipeIngredientService {
    List<RecipeIngredientShowDto> getAllRecipeIngredientByRecipeId(Long recipeId, String username);
    RecipeIngredientDto createRecipeIngredient(RecipeIngredientCRUDDto recipeIngredientCRUDDto, String username);
    RecipeIngredientDto updateRecipeIngredient(RecipeIngredientCRUDDto recipeIngredientCRUDDto, String username);
    void deleteRecipeIngredient(Long recipeId, Long ingredientId, String username);
}

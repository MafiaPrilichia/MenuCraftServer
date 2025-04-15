package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.dto.RecipeCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    List<RecipeDto> getAllRecipes();
    RecipeDto getRecipeById(Long id);
    RecipeDto createRecipe(RecipeCRUDDto recipeCRUDDto, String username);
    RecipeDto updateRecipe(Long id, RecipeCRUDDto recipeCRUDDto, String username);
    void deleteRecipe(Long id, String username);

}

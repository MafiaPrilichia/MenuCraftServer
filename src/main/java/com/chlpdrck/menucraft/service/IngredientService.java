package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.entity.Ingredient;
import com.chlpdrck.menucraft.mapper.dto.IngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.IngredientDto;

import java.util.List;

public interface IngredientService {
    List<IngredientDto> getAllIngredients();
    Ingredient getIngredientById(Long id);
    IngredientDto createIngredient(IngredientCRUDDto ingredientCRUDDto, String username);
    IngredientDto updateIngredient(Long id, IngredientCRUDDto ingredientCRUDDto, String username);
    void deleteIngredient(Long id, String username);
}
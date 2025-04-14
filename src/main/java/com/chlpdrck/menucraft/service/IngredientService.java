package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.dto.IngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.IngredientDto;

import java.util.List;

public interface IngredientService {
    List<IngredientDto> getAllIngredients();
    IngredientDto createIngredient(IngredientDto ingredientDto, String username);
    IngredientDto updateIngredient(Long id, IngredientCRUDDto ingredientCRUDDto, String username);
    void deleteIngredient(Long id, String username);
}
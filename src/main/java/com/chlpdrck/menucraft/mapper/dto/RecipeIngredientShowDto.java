package com.chlpdrck.menucraft.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.RecipeIngredient}
 */
@Value
@AllArgsConstructor
public class RecipeIngredientShowDto {
    Long recipeId;
    IngredientDto ingredient;
    UnitDto unit;
    BigDecimal amount;
}

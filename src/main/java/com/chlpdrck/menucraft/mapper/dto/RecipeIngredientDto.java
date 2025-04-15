package com.chlpdrck.menucraft.mapper.dto;

import lombok.Value;

import java.math.BigDecimal;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.RecipeIngredient}
 */
@Value
public class RecipeIngredientDto {
    Long recipeId;
    Long ingredientId;
    UnitDto unit;
    BigDecimal amount;
}
package com.chlpdrck.menucraft.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.EventRecipe}
 */
@Value
@AllArgsConstructor
public class EventRecipeShowDto {
    Long eventId;
    RecipeDto recipe;
    Integer portions;
}
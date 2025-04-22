package com.chlpdrck.menucraft.mapper.dto;

import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.EventRecipe}
 */
@Value
public class EventRecipeDto {
    Long eventId;
    Long recipeId;
    Integer portions;
}
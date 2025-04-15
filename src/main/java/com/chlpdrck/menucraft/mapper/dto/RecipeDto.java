package com.chlpdrck.menucraft.mapper.dto;

import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Recipe}
 */
@Value
public class RecipeDto {
    Long id;
    UserDto user;
    CategoryDto category;
    String name;
    String description;
    Integer servings;
    Integer cookingTime;
}
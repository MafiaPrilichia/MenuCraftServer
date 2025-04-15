package com.chlpdrck.menucraft.mapper.dto;

import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Recipe}
 */
@Value
public class RecipeCRUDDto {
    Long userId;
    Long categoryId;
    String name;
    String description;
    Integer servings;
    Integer cookingTime;
}
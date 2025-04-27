package com.chlpdrck.menucraft.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Recipe}
 */
@Getter
@Setter
public class RecipeCRUDDto {
    Long userId;
    Long categoryId;
    String name;
    String description;
    Integer servings;
    Integer cookingTime;
}
package com.chlpdrck.menucraft.mapper.dto;

import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Category}
 */
@Value
public class CategoryDto {
    Long id;
    String name;
}
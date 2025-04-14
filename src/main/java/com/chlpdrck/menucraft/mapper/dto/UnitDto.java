package com.chlpdrck.menucraft.mapper.dto;

import com.chlpdrck.menucraft.entity.Unit;
import lombok.Value;

/**
 * DTO for {@link Unit}
 */
@Value
public class UnitDto {
    Long id;
    String name;
}
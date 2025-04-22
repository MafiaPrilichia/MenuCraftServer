package com.chlpdrck.menucraft.mapper.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.EventRecipe}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventIngredientShowDto {
    Long eventId;
    IngredientDto ingredient;
    UnitDto unit;
    BigDecimal amount;
}
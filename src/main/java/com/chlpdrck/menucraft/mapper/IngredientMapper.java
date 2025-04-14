package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Ingredient;
import com.chlpdrck.menucraft.mapper.dto.IngredientDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {
    Ingredient toEntity(IngredientDto ingredientDto);

    IngredientDto toIngredientDto(Ingredient ingredient);
}
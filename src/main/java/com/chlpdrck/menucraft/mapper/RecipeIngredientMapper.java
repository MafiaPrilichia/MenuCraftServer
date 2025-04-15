package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.RecipeIngredient;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UnitMapper.class})
public interface RecipeIngredientMapper {
    @Mapping(source = "ingredientId", target = "id.ingredientId")
    @Mapping(source = "recipeId", target = "id.recipeId")
    RecipeIngredient toEntity(RecipeIngredientDto recipeIngredientDto);

    @InheritInverseConfiguration(name = "toEntity")
    RecipeIngredientDto toRecipeIngredientDto(RecipeIngredient recipeIngredient);

    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "ingredientId", target = "id.ingredientId")
    @Mapping(source = "recipeId", target = "id.recipeId")
    RecipeIngredient toEntity(RecipeIngredientCRUDDto recipeIngredientCRUDDto);

    @InheritInverseConfiguration(name = "toEntity")
    RecipeIngredientCRUDDto toRecipeIngredientCRUDDto(RecipeIngredient recipeIngredient);
}
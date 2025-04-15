package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Recipe;
import com.chlpdrck.menucraft.mapper.dto.RecipeCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CategoryMapper.class})
public interface RecipeMapper {
    Recipe toEntity(RecipeDto recipeDto);

    RecipeDto toRecipeDto(Recipe recipe);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "userId", target = "user.id")
    Recipe toEntity(RecipeCRUDDto recipeCRUDDto);

    @InheritInverseConfiguration(name = "toEntity")
    RecipeCRUDDto toRecipeCRUDDto(Recipe recipe);
}
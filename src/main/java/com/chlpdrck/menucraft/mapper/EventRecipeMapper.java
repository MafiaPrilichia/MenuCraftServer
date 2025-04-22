package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.EventRecipe;
import com.chlpdrck.menucraft.mapper.dto.EventRecipeDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventRecipeMapper {
    @Mapping(source = "recipeId", target = "id.recipeId")
    @Mapping(source = "eventId", target = "id.eventId")
    EventRecipe toEntity(EventRecipeDto eventRecipeDto);

    @InheritInverseConfiguration(name = "toEntity")
    EventRecipeDto toEventRecipeDto(EventRecipe eventRecipe);
}
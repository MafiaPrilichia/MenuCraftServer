package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.dto.EventIngredientShowDto;
import com.chlpdrck.menucraft.mapper.dto.EventRecipeDto;
import com.chlpdrck.menucraft.mapper.dto.EventRecipeShowDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientShowDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventRecipeService {
    List<EventRecipeShowDto> getAllEventRecipeByEventId(Long eventId, String username);
    List<EventIngredientShowDto> getAllIngredientByEventId(Long eventId, String username);
    EventRecipeDto createEventRecipe(EventRecipeDto eventRecipeDto, String username);
    EventRecipeDto updateEventRecipe(EventRecipeDto eventRecipeDto, String username);
    void deleteEventRecipe(Long eventId, Long recipeId, String username);
}

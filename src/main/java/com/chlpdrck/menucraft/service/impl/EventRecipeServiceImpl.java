package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.*;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.EventRecipeMapper;
import com.chlpdrck.menucraft.mapper.UnitMapper;
import com.chlpdrck.menucraft.mapper.dto.*;
import com.chlpdrck.menucraft.repository.EventRecipeRepository;
import com.chlpdrck.menucraft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventRecipeServiceImpl implements EventRecipeService {

    private final EventRecipeRepository eventRecipeRepository;
    private final EventRecipeMapper eventRecipeMapper;
    private final EventService eventService;
    private final UserService userService;
    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;


    @Transactional(readOnly = true)
    @Override
    public List<EventRecipeShowDto> getAllEventRecipeByEventId(Long eventId, String username) {
        Event event = eventService.getEventEntityById(eventId, username);

        if (!Objects.equals(userService.getUserById(event.getOwner().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this event!");
        }

        List<EventRecipe> eventRecipes = eventRecipeRepository.findAllById_EventId(eventId);

        return eventRecipes.stream()
                .map(entity -> {
                    RecipeDto recipeDto = recipeService.getRecipeById(entity.getId().getRecipeId());

                    return new EventRecipeShowDto(
                            event.getId(),
                            recipeDto,
                            entity.getPortions()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventIngredientShowDto> getAllIngredientByEventId(Long eventId, String username) {
        Event event = eventService.getEventEntityById(eventId, username);

        if (!Objects.equals(userService.getUserById(event.getOwner().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this event!");
        }

        List<EventRecipe> eventRecipes = eventRecipeRepository.findAllById_EventId(eventId);

        Map<String, EventIngredientShowDto> ingredientMap = new HashMap<>();

        for (EventRecipe er : eventRecipes) {
            RecipeDto recipe = recipeService.getRecipeById(er.getId().getRecipeId());

            for (RecipeIngredientShowDto ri : recipeIngredientService.getAllRecipeIngredientByRecipeId(recipe.getId(), username)) {
                IngredientDto ingredientDto = ri.getIngredient();
                Long unitId = ri.getUnit().getId();
                String key = ingredientDto.getId() + "_" + unitId;

                BigDecimal amount = ri.getAmount().divide(BigDecimal.valueOf(recipe.getServings()), 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(er.getPortions()));

                if (ingredientMap.containsKey(key)) {
                    EventIngredientShowDto dto = ingredientMap.get(key);
                    dto.setAmount(dto.getAmount().add(amount));
                } else {
                    UnitDto unitDto = ri.getUnit();

                    EventIngredientShowDto dto = new EventIngredientShowDto(
                            eventId,
                            ingredientDto,
                            unitDto,
                            amount
                    );

                    ingredientMap.put(key, dto);
                }
            }
        }

        return new ArrayList<>(ingredientMap.values());
    }

    @Transactional()
    @Override
    public EventRecipeDto createEventRecipe(EventRecipeDto eventRecipeDto, String username) {
        EventRecipe eventRecipe = eventRecipeMapper.toEntity(eventRecipeDto);

        if (eventRecipeRepository.existsById(eventRecipe.getId())) {
            throw new CrudException("This recipe already exists in the event.");
        }

        checkChangeableOfEventRecipe(username, eventRecipe);

        eventRecipe = eventRecipeRepository.save(eventRecipe);

        return eventRecipeMapper.toEventRecipeDto(eventRecipe);
    }

    @Transactional()
    @Override
    public EventRecipeDto updateEventRecipe(EventRecipeDto eventRecipeDto, String username) {
        EventRecipe eventRecipe = eventRecipeRepository.findById(new EventRecipeId(eventRecipeDto.getEventId(), eventRecipeDto.getRecipeId()))
                .orElseThrow(() -> new CrudException("EventRecipe for update doesn't find!"));

        Event event = eventService.getEventEntityById(eventRecipe.getId().getEventId(), username);
        if (!Objects.equals(userService.getUserById(event.getOwner().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this event!");
        }

        RecipeDto recipe = recipeService.getRecipeById(eventRecipe.getId().getRecipeId());
        if (!recipe.getIsPublic() && !recipe.getUser().getUsername().equals(username)) {
            throw new CrudException("User can't choose this recipe!");
        }

        eventRecipe.setPortions(eventRecipeDto.getPortions());

        eventRecipe = eventRecipeRepository.save(eventRecipe);

        return eventRecipeMapper.toEventRecipeDto(eventRecipe);
    }

    @Transactional()
    @Override
    public void deleteEventRecipe(Long eventId, Long recipeId, String username) {
        EventRecipe eventRecipe = eventRecipeRepository.findById(new EventRecipeId(eventId, recipeId))
                .orElseThrow(() -> new CrudException("EventRecipe for update doesn't find!"));

        checkChangeableOfEventRecipe(username, eventRecipe);

        eventRecipeRepository.delete(eventRecipe);
    }

    private void checkChangeableOfEventRecipe(String username, EventRecipe eventRecipe) {
        Event event = eventService.getEventEntityById(eventRecipe.getId().getEventId(), username);
        if (!Objects.equals(userService.getUserById(event.getOwner().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this event!");
        }

        recipeService.getRecipeById(eventRecipe.getId().getRecipeId());
    }

}
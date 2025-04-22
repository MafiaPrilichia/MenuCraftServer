package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.mapper.dto.*;
import com.chlpdrck.menucraft.service.EventRecipeService;
import com.chlpdrck.menucraft.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventRecipeService eventRecipeService;

    @GetMapping("/owned")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventShortDto>> getOwnedEvents(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        List<EventShortDto> ownedEvents = eventService.getEventsByOwnerUsername(username);

        return ResponseEntity.ok(ownedEvents);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        EventDto event = eventService.getEventById(id, username);

        return ResponseEntity.ok(event);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventCRUDDto> createEvent(@RequestBody EventCRUDDto eventCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        EventCRUDDto createdEvent = eventService.createEvent(eventCRUDDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventCRUDDto eventCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        EventDto updatedEvent = eventService.updateEvent(id, eventCRUDDto, username);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        eventService.deleteEvent(id, username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipe/{eventId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventRecipeShowDto>> getAllRecipesByEvent(@PathVariable Long eventId, @AuthenticationPrincipal UserDetails userDetails) {
        List<EventRecipeShowDto> eventRecipes = eventRecipeService.getAllEventRecipeByEventId(eventId, userDetails.getUsername());
        return ResponseEntity.ok(eventRecipes);
    }

    @GetMapping("/ingredient/{eventId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventIngredientShowDto>> getAllIngredientsByEvent(@PathVariable Long eventId, @AuthenticationPrincipal UserDetails userDetails) {
        List<EventIngredientShowDto> eventIngredients = eventRecipeService.getAllIngredientByEventId(eventId, userDetails.getUsername());
        return ResponseEntity.ok(eventIngredients);
    }

    @PostMapping("/recipe")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventRecipeDto> createEventRecipe(@RequestBody EventRecipeDto eventRecipeDto, @AuthenticationPrincipal UserDetails userDetails) {
        EventRecipeDto createdEventRecipe = eventRecipeService.createEventRecipe(eventRecipeDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventRecipe);
    }

    @PutMapping("/recipe")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventRecipeDto> updateRecipeIngredient(@RequestBody EventRecipeDto eventRecipeDto, @AuthenticationPrincipal UserDetails userDetails) {
        EventRecipeDto updatedEventRecipe = eventRecipeService.updateEventRecipe(eventRecipeDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedEventRecipe);
    }

    @DeleteMapping("/recipe")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteEventRecipe(@RequestParam Long eventId, @RequestParam Long recipeId, @AuthenticationPrincipal UserDetails userDetails) {
        eventRecipeService.deleteEventRecipe(eventId, recipeId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
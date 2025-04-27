package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.mapper.dto.*;
import com.chlpdrck.menucraft.service.RecipeIngredientService;
import com.chlpdrck.menucraft.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecipeDto>> getAllRecipes(@AuthenticationPrincipal UserDetails userDetails) {
        List<RecipeDto> recipes = recipeService.getAllRecipes(userDetails.getUsername());
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecipeDto>> getAllRecipesForUser(@AuthenticationPrincipal UserDetails userDetails,
                                                                @RequestParam(required = false) List<Long> categoryIds) {
        List<RecipeDto> recipes = recipeService.getAllRecipesForUser(userDetails.getUsername(), categoryIds);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        RecipeDto recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeCRUDDto recipeCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        RecipeDto createdRecipe = recipeService.createRecipe(recipeCRUDDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeCRUDDto recipeCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeCRUDDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        recipeService.deleteRecipe(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ingredient/{recipeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecipeIngredientShowDto>> getAllIngredientsByRecipe(@PathVariable Long recipeId, @AuthenticationPrincipal UserDetails userDetails) {
        List<RecipeIngredientShowDto> recipeIngredients = recipeIngredientService.getAllRecipeIngredientByRecipeId(recipeId, userDetails.getUsername());
        return ResponseEntity.ok(recipeIngredients);
    }

    @PostMapping("/ingredient")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecipeIngredientDto> createRecipeIngredient(@RequestBody RecipeIngredientCRUDDto recipeIngredientCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        RecipeIngredientDto createdRecipeIngredient = recipeIngredientService.createRecipeIngredient(recipeIngredientCRUDDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipeIngredient);
    }

    @PutMapping("/ingredient")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecipeIngredientDto> updateRecipeIngredient(@RequestBody RecipeIngredientCRUDDto recipeIngredientCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        RecipeIngredientDto updatedRecipeIngredient = recipeIngredientService.updateRecipeIngredient(recipeIngredientCRUDDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedRecipeIngredient);
    }

    @DeleteMapping("/ingredient")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteRecipeIngredient(@RequestParam Long recipeId, @RequestParam Long ingredientId, @AuthenticationPrincipal UserDetails userDetails) {
        recipeIngredientService.deleteRecipeIngredient(recipeId, ingredientId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.mapper.dto.IngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.IngredientDto;
import com.chlpdrck.menucraft.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<IngredientDto> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto, @AuthenticationPrincipal UserDetails userDetails) {
        IngredientDto createdIngredient = ingredientService.createIngredient(ingredientDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIngredient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable Long id, @RequestBody IngredientCRUDDto ingredientCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredientCRUDDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        ingredientService.deleteIngredient(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
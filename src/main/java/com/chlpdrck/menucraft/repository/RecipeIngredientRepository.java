package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.RecipeIngredient;
import com.chlpdrck.menucraft.entity.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, RecipeIngredientId> {
    List<RecipeIngredient> findAllById_RecipeId(Long idRecipeId);
}
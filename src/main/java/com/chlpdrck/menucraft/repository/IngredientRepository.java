package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
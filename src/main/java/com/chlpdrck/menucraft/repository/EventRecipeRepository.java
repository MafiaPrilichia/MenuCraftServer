package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.EventRecipe;
import com.chlpdrck.menucraft.entity.EventRecipeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRecipeRepository extends JpaRepository<EventRecipe, EventRecipeId> {
    List<EventRecipe> findAllById_EventId(Long eventId);
}
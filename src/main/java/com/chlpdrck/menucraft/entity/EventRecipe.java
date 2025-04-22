package com.chlpdrck.menucraft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_recipe")
public class EventRecipe {
    @EmbeddedId
    private EventRecipeId id;

    @Column(name = "portions", nullable = false)
    private Integer portions;

}
package com.chlpdrck.menucraft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EventRecipeId implements Serializable {
    private static final long serialVersionUID = -2626559505035976852L;
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventRecipeId entity = (EventRecipeId) o;
        return Objects.equals(this.eventId, entity.eventId) &&
                Objects.equals(this.recipeId, entity.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, recipeId);
    }

}
package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("""
        SELECT r FROM Recipe r
        WHERE (r.isPublic = true OR r.user.id = :userId)
            AND (:categoryIds IS NULL OR r.category.id IN :categoryIds)
        ORDER BY CASE WHEN r.user.id = :userId THEN 0 ELSE 1 END
    """)
    List<Recipe> findAllVisibleToUser(@Param("userId") Long userId,
                                      @Param("categoryIds") List<Long> categoryIds);
}
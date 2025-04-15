package com.chlpdrck.menucraft.repository;

import com.chlpdrck.menucraft.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
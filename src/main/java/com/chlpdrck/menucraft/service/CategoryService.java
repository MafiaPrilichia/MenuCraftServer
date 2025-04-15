package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.entity.Category;
import com.chlpdrck.menucraft.mapper.dto.CategoryCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    Category getCategoryById(Long id);
    CategoryDto createCategory(CategoryCRUDDto categoryCRUDDto, String username);
    CategoryDto updateCategory(Long id, CategoryCRUDDto categoryCRUDDto, String username);
    void deleteCategory(Long id, String username);
}
package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Category;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.CategoryMapper;
import com.chlpdrck.menucraft.mapper.dto.CategoryCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.CategoryDto;
import com.chlpdrck.menucraft.repository.CategoryRepository;
import com.chlpdrck.menucraft.service.CategoryService;
import com.chlpdrck.menucraft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;


    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CrudException("Category doesn't exist!"));
    }

    @Transactional()
    @Override
    public CategoryDto createCategory(CategoryCRUDDto categoryCRUDDto, String username) {
        if (userService.checkUserAdmin(username)) {
            Category category = categoryMapper.toEntity(categoryCRUDDto);

            category = categoryRepository.save(category);
            return categoryMapper.toCategoryDto(category);
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional()
    @Override
    public CategoryDto updateCategory(Long id, CategoryCRUDDto categoryCRUDDto, String username) {
        if (userService.checkUserAdmin(username)) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CrudException("Category for update doesn't find!"));

            category.setName(categoryCRUDDto.getName());

            category = categoryRepository.save(category);
            return categoryMapper.toCategoryDto(category);
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional()
    @Override
    public void deleteCategory(Long id, String username) {
        if (userService.checkUserAdmin(username)) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CrudException("Category for delete doesn't find!"));

            categoryRepository.delete(category);
        } else {
            throw new CrudException("User is not admin!");
        }
    }
}

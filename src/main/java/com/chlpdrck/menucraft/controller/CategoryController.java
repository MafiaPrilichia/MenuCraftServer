package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.mapper.dto.CategoryCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.CategoryDto;
import com.chlpdrck.menucraft.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryCRUDDto categoryCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        CategoryDto createdCategory = categoryService.createCategory(categoryCRUDDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryCRUDDto categoryCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryCRUDDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        categoryService.deleteCategory(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
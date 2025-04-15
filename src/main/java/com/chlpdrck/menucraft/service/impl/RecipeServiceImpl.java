package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Category;
import com.chlpdrck.menucraft.entity.Recipe;
import com.chlpdrck.menucraft.entity.User;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.RecipeMapper;
import com.chlpdrck.menucraft.mapper.dto.RecipeCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeDto;
import com.chlpdrck.menucraft.repository.RecipeRepository;
import com.chlpdrck.menucraft.service.CategoryService;
import com.chlpdrck.menucraft.service.IngredientService;
import com.chlpdrck.menucraft.service.RecipeService;
import com.chlpdrck.menucraft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final UserService userService;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;


    @Transactional(readOnly = true)
    @Override
    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::toRecipeDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public RecipeDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CrudException("Recipe doesn't exist!"));

        return recipeMapper.toRecipeDto(recipe);
    }

    @Transactional()
    @Override
    public RecipeDto createRecipe(RecipeCRUDDto recipeCRUDDto, String username) {
        Recipe recipe = recipeMapper.toEntity(recipeCRUDDto);

        User user = userService.getUserById(recipeCRUDDto.getUserId());
        Category category = categoryService.getCategoryById(recipeCRUDDto.getCategoryId());
        recipe.setUser(user);
        recipe.setCategory(category);

        recipe = recipeRepository.save(recipe);

        return recipeMapper.toRecipeDto(recipe);
    }

    @Transactional()
    @Override
    public RecipeDto updateRecipe(Long id, RecipeCRUDDto recipeCRUDDto, String username) {
        if (!Objects.equals(userService.getUserById(recipeCRUDDto.getUserId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this recipe!");
        }

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CrudException("Recipe not found"));

        recipe.setName(recipeCRUDDto.getName());
        recipe.setDescription(recipeCRUDDto.getDescription());
        recipe.setServings(recipeCRUDDto.getServings());
        recipe.setCookingTime(recipeCRUDDto.getCookingTime());

        User user = userService.getUserById(recipeCRUDDto.getUserId());
        recipe.setUser(user);

        Category category = categoryService.getCategoryById(recipeCRUDDto.getCategoryId());
        recipe.setCategory(category);

        recipe = recipeRepository.save(recipe);

        return recipeMapper.toRecipeDto(recipe);
    }

    @Transactional()
    @Override
    public void deleteRecipe(Long id, String username) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CrudException("Recipe for delete doesn't find!"));

        if (!Objects.equals(userService.getUserById(recipe.getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't delete this recipe!");
        }

        recipeRepository.delete(recipe);
    }
}

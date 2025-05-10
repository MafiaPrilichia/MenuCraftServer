package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.*;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.RecipeMapper;
import com.chlpdrck.menucraft.mapper.dto.RecipeCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeDto;
import com.chlpdrck.menucraft.repository.RecipeIngredientRepository;
import com.chlpdrck.menucraft.repository.RecipeRepository;
import com.chlpdrck.menucraft.service.CategoryService;
import com.chlpdrck.menucraft.service.RecipeService;
import com.chlpdrck.menucraft.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final RecipeIngredientRepository recipeIngredientRepository;


    @Transactional(readOnly = true)
    @Override
    public List<RecipeDto> getAllRecipes(String username) {
        if (userService.checkUserAdmin(username)) {
            return recipeRepository.findAll()
                    .stream()
                    .map(recipeMapper::toRecipeDto)
                    .collect(Collectors.toList());
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecipeDto> getAllRecipesForUser(String username, List<Long> categoryIds) {
        User user = userService.getUserByUsername(username);
        return recipeRepository.findAllVisibleToUser(user.getId(), categoryIds)
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

        if (recipeCRUDDto.getUserId() == null) {
            recipeCRUDDto.setUserId(userService.getUserByUsername(username).getId());
        }

        if (!Objects.equals(userService.getUserById(recipeCRUDDto.getUserId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't create recipe for another user!");
        }

        User user = userService.getUserById(recipeCRUDDto.getUserId());
        Category category = categoryService.getCategoryById(recipeCRUDDto.getCategoryId());
        recipe.setUser(user);
        recipe.setCategory(category);
        recipe.setIsPublic(false);

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

        if (!Objects.equals(userService.getUserById(recipe.getUser().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't delete this recipe!");
        }

        recipeRepository.delete(recipe);
    }

    @Transactional
    @Override
    public Long saveRecipeFromAnotherUser(Long recipeId, String username) {
        log.info("Попытка сохранить рецепт с ID {} для пользователя {}", recipeId, username);

        Recipe originalRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> {
                    log.error("Рецепт с ID {} не найден", recipeId);
                    return new CrudException("Recipe not found");
                });

        String originalAuthor = userService.getUserById(originalRecipe.getUser().getId()).getUsername();
        log.info("Оригинальный автор рецепта: {}", originalAuthor);

        if (Objects.equals(originalAuthor, username) && !userService.checkUserAdmin(username)) {
            log.warn("Пользователь {} не имеет прав сохранять этот рецепт", username);
            throw new CrudException("This user created the recipe!");
        }

        // Создание новой копии рецепта вручную
        Recipe newRecipe = new Recipe();
        newRecipe.setUser(userService.getUserByUsername(username));
        newRecipe.setCategory(originalRecipe.getCategory());
        newRecipe.setName(originalRecipe.getName());
        newRecipe.setDescription(originalRecipe.getDescription());
        newRecipe.setServings(originalRecipe.getServings());
        newRecipe.setCookingTime(originalRecipe.getCookingTime());
        newRecipe.setIsPublic(false);

        Recipe savedRecipe = recipeRepository.save(newRecipe);
        log.info("Создан новый рецепт с ID {} от имени пользователя {}", savedRecipe.getId(), username);

        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAllById_RecipeId(recipeId);
        log.info("Найдено {} ингредиентов для копирования", recipeIngredients.size());

        // Создание новых объектов RecipeIngredient с новым ключом
        List<RecipeIngredient> newIngredients = recipeIngredients.stream()
                .map(old -> {
                    RecipeIngredient newIngredient = new RecipeIngredient();
                    newIngredient.setId(new RecipeIngredientId(savedRecipe.getId(), old.getId().getIngredientId()));
                    newIngredient.setUnit(old.getUnit());
                    newIngredient.setAmount(old.getAmount());
                    return newIngredient;
                }).toList();

        recipeIngredientRepository.saveAll(newIngredients);
        log.info("Ингредиенты успешно сохранены для нового рецепта");

        return savedRecipe.getId();
    }


}

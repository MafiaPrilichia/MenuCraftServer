package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.RecipeIngredient;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.RecipeIngredientMapper;
import com.chlpdrck.menucraft.mapper.dto.RecipeDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.RecipeIngredientDto;
import com.chlpdrck.menucraft.repository.RecipeIngredientRepository;
import com.chlpdrck.menucraft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final RecipeService recipeService;
    private final UserService userService;
    private final IngredientService ingredientService;
    private final UnitService unitService;


//    @Transactional(readOnly = true)
//    @Override
//    public List<RecipeDto> getAllIngredientsByRecipe() {
//        return recipeRepository.findAll()
//                .stream()
//                .map(recipeMapper::toRecipeDto)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public RecipeDto getRecipeById(Long id) {
//        Recipe recipe = recipeRepository.findById(id)
//                .orElseThrow(() -> new CrudException("Category doesn't exist!"));
//
//        return recipeMapper.toRecipeDto(recipe);
//    }

    @Transactional()
    @Override
    public RecipeIngredientDto createRecipeIngredient(RecipeIngredientCRUDDto recipeIngredientCRUDDto, String username) {
        RecipeIngredient recipeIngredient = recipeIngredientMapper.toEntity(recipeIngredientCRUDDto);

        if (recipeIngredientRepository.existsById(recipeIngredient.getId())) {
            throw new CrudException("This ingredient already exists in the recipe.");
        }

        RecipeDto recipe = recipeService.getRecipeById(recipeIngredient.getId().getRecipeId());
        if (!Objects.equals(userService.getUserById(recipe.getUser().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this recipe!");
        }

        ingredientService.getIngredientById(recipeIngredient.getId().getIngredientId());
        unitService.getUnitById(recipeIngredient.getUnit().getId());

        recipeIngredient = recipeIngredientRepository.save(recipeIngredient);

        return recipeIngredientMapper.toRecipeIngredientDto(recipeIngredient);
    }

//    @Transactional()
//    @Override
//    public RecipeDto updateRecipeIngredient(Long id, RecipeCRUDDto recipeCRUDDto, String username) {
//        if (!Objects.equals(userService.getUserById(recipeCRUDDto.getUserId()).getUsername(), username)
//                && !userService.checkUserAdmin(username)) {
//            throw new CrudException("User can't change this recipe!");
//        }
//
//        Recipe recipe = recipeRepository.findById(id)
//                .orElseThrow(() -> new CrudException("Recipe not found"));
//
//        recipe.setName(recipeCRUDDto.getName());
//        recipe.setDescription(recipeCRUDDto.getDescription());
//        recipe.setServings(recipeCRUDDto.getServings());
//        recipe.setCookingTime(recipeCRUDDto.getCookingTime());
//
//        User user = userService.getUserById(recipeCRUDDto.getUserId());
//        recipe.setUser(user);
//
//        Category category = categoryService.getCategoryById(recipeCRUDDto.getCategoryId());
//        recipe.setCategory(category);
//
//        recipe = recipeRepository.save(recipe);
//
//        return recipeMapper.toRecipeDto(recipe);
//    }

//    @Transactional()
//    @Override
//    public void deleteRecipe(Long id, String username) {
//        Recipe recipe = recipeRepository.findById(id)
//                .orElseThrow(() -> new CrudException("Recipe for delete doesn't find!"));
//
//        if (!Objects.equals(userService.getUserById(recipe.getId()).getUsername(), username)
//                && !userService.checkUserAdmin(username)) {
//            throw new CrudException("User can't delete this recipe!");
//        }
//
//        recipeRepository.delete(recipe);
//    }

}

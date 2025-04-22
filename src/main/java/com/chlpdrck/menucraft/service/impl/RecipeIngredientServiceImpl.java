package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Ingredient;
import com.chlpdrck.menucraft.entity.RecipeIngredient;
import com.chlpdrck.menucraft.entity.RecipeIngredientId;
import com.chlpdrck.menucraft.entity.Unit;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.IngredientMapper;
import com.chlpdrck.menucraft.mapper.RecipeIngredientMapper;
import com.chlpdrck.menucraft.mapper.UnitMapper;
import com.chlpdrck.menucraft.mapper.dto.*;
import com.chlpdrck.menucraft.repository.RecipeIngredientRepository;
import com.chlpdrck.menucraft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final RecipeService recipeService;
    private final UserService userService;
    private final IngredientService ingredientService;
    private final UnitService unitService;
    private final IngredientMapper ingredientMapper;
    private final UnitMapper unitMapper;


    @Transactional(readOnly = true)
    @Override
    public List<RecipeIngredientShowDto> getAllRecipeIngredientByRecipeId(Long recipeId, String username) {
        RecipeDto recipe = recipeService.getRecipeById(recipeId);

        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAllById_RecipeId(recipeId);

        return recipeIngredients.stream()
                .map(entity -> {
                    Ingredient ingredient = ingredientService.getIngredientById(entity.getId().getIngredientId());

                    IngredientDto ingredientDto = ingredientMapper.toIngredientDto(ingredient);
                    UnitDto unitDto = unitMapper.toUnitDto(entity.getUnit());

                    return new RecipeIngredientShowDto(
                            recipe.getId(),
                            ingredientDto,
                            unitDto,
                            entity.getAmount()
                    );
                })
                .collect(Collectors.toList());
    }

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

    @Transactional()
    @Override
    public RecipeIngredientDto updateRecipeIngredient(RecipeIngredientCRUDDto recipeIngredientCRUDDto, String username) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(new RecipeIngredientId(recipeIngredientCRUDDto.getRecipeId(), recipeIngredientCRUDDto.getIngredientId()))
                .orElseThrow(() -> new CrudException("RecipeIngredient for update doesn't find!"));

        RecipeDto recipe = recipeService.getRecipeById(recipeIngredient.getId().getRecipeId());
        if (!Objects.equals(userService.getUserById(recipe.getUser().getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't change this recipe!");
        }

        ingredientService.getIngredientById(recipeIngredient.getId().getIngredientId());
        Unit unit = unitService.getUnitById(recipeIngredientCRUDDto.getUnitId());

        recipeIngredient.setUnit(unit);
        recipeIngredient.setAmount(recipeIngredientCRUDDto.getAmount());

        recipeIngredient = recipeIngredientRepository.save(recipeIngredient);

        return recipeIngredientMapper.toRecipeIngredientDto(recipeIngredient);
    }

    @Transactional()
    @Override
    public void deleteRecipeIngredient(Long recipeId, Long ingredientId, String username) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(new RecipeIngredientId(recipeId, ingredientId))
                .orElseThrow(() -> new CrudException("RecipeIngredient for update doesn't find!"));

        RecipeDto recipe = recipeService.getRecipeById(recipeIngredient.getId().getRecipeId());
        if (!Objects.equals(userService.getUserById(recipe.getId()).getUsername(), username)
                && !userService.checkUserAdmin(username)) {
            throw new CrudException("User can't delete this recipe!");
        }
        ingredientService.getIngredientById(recipeIngredient.getId().getIngredientId());

        recipeIngredientRepository.delete(recipeIngredient);
    }

}

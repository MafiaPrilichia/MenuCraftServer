package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Ingredient;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.IngredientMapper;
import com.chlpdrck.menucraft.mapper.dto.IngredientCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.IngredientDto;
import com.chlpdrck.menucraft.repository.IngredientRepository;
import com.chlpdrck.menucraft.service.IngredientService;
import com.chlpdrck.menucraft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientMapper::toIngredientDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new CrudException("Ingredient doesn't exist!"));
    }

    @Transactional()
    @Override
    public IngredientDto createIngredient(IngredientCRUDDto ingredientCRUDDto, String username) {
        if (userService.checkUserAdmin(username)) {
            Ingredient ingredient = ingredientMapper.toEntity(ingredientCRUDDto);

            ingredient = ingredientRepository.save(ingredient);
            return ingredientMapper.toIngredientDto(ingredient);
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional()
    @Override
    public IngredientDto updateIngredient(Long id, IngredientCRUDDto ingredientCRUDDto, String username) {
        if (userService.checkUserAdmin(username)) {
            Ingredient ingredient = ingredientRepository.findById(id)
                    .orElseThrow(() -> new CrudException("Ingredient for update doesn't find!"));

            ingredient.setName(ingredientCRUDDto.getName());

            ingredient = ingredientRepository.save(ingredient);
            return ingredientMapper.toIngredientDto(ingredient);
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional()
    @Override
    public void deleteIngredient(Long id, String username) {
        if (userService.checkUserAdmin(username)) {
            Ingredient ingredient = ingredientRepository.findById(id)
                    .orElseThrow(() -> new CrudException("Ingredient for delete doesn't find!"));

            ingredientRepository.delete(ingredient);
        } else {
            throw new CrudException("User is not admin!");
        }
    }
}
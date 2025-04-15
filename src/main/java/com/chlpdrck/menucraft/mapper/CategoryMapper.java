package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Category;
import com.chlpdrck.menucraft.mapper.dto.CategoryCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toCategoryDto(Category category);

    Category toEntity(CategoryCRUDDto categoryCRUDDto);

    CategoryCRUDDto toCategoryCRUDDto(Category category);
}
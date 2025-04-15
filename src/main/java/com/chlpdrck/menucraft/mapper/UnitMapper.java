package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Unit;
import com.chlpdrck.menucraft.mapper.dto.UnitCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.UnitDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnitMapper {
    Unit toEntity(UnitDto unitDto);

    UnitDto toUnitDto(Unit unit);

    Unit toEntity(UnitCRUDDto unitCRUDDto);

    UnitCRUDDto toUnitCRUDDto(Unit unit);
}
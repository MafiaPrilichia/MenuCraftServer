package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.dto.UnitCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.UnitDto;

import java.util.List;

public interface UnitService {
    List<UnitDto> getAllUnits();
    UnitDto createUnit(UnitDto unitDto, String username);
    UnitDto updateUnit(Long id, UnitCRUDDto unitCRUDDto, String username);
    void deleteUnit(Long id, String username);
}

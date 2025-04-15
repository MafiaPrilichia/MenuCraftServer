package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.entity.Unit;
import com.chlpdrck.menucraft.mapper.dto.UnitCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.UnitDto;

import java.util.List;

public interface UnitService {
    List<UnitDto> getAllUnits();
    Unit getUnitById(Long id);
    UnitDto createUnit(UnitCRUDDto unitCRUDDto, String username);
    UnitDto updateUnit(Long id, UnitCRUDDto unitCRUDDto, String username);
    void deleteUnit(Long id, String username);
}

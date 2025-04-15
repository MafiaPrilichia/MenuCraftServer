package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Unit;
import com.chlpdrck.menucraft.exception.CrudException;
import com.chlpdrck.menucraft.mapper.UnitCRUDMapper;
import com.chlpdrck.menucraft.mapper.UnitMapper;
import com.chlpdrck.menucraft.mapper.dto.UnitCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.UnitDto;
import com.chlpdrck.menucraft.repository.UnitRepository;
import com.chlpdrck.menucraft.service.UnitService;
import com.chlpdrck.menucraft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UnitServiceImpl implements UnitService {

    private final UserService userService;
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final UnitCRUDMapper unitCRUDMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UnitDto> getAllUnits() {
        return unitRepository.findAll()
                .stream()
                .map(unitMapper::toUnitDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Unit getUnitById(Long id) {
        return  unitRepository.findById(id)
                .orElseThrow(() -> new CrudException("Unit doesn't exist!"));
    }

    @Transactional()
    @Override
    public UnitDto createUnit(UnitCRUDDto unitCRUDDto, String username) {
        if (userService.checkUserAdmin(username)) {
            Unit unit = unitCRUDMapper.toEntity(unitCRUDDto);

            unit = unitRepository.save(unit);
            return unitMapper.toUnitDto(unit);
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional()
    @Override
    public UnitDto updateUnit(Long id, UnitCRUDDto unitCRUDDto, String username) {
        if (userService.checkUserAdmin(username)) {
            Unit unit = unitRepository.findById(id)
                    .orElseThrow(() -> new CrudException("Unit for update doesn't find!"));

            unit.setName(unitCRUDDto.getName());

            unit = unitRepository.save(unit);
            return unitMapper.toUnitDto(unit);
        } else {
            throw new CrudException("User is not admin!");
        }
    }

    @Transactional()
    @Override
    public void deleteUnit(Long id, String username) {
        if (userService.checkUserAdmin(username)) {
            Unit unit = unitRepository.findById(id)
                    .orElseThrow(() -> new CrudException("Unit for delete doesn't find!"));

            unitRepository.delete(unit);
        } else {
            throw new CrudException("User is not admin!");
        }
    }
}

package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.mapper.dto.UnitCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.UnitDto;
import com.chlpdrck.menucraft.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UnitDto>> getAllUnits() {
        List<UnitDto> units = unitService.getAllUnits();

        return ResponseEntity.ok(units);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UnitDto> createUnit(@RequestBody UnitDto unitDto, @AuthenticationPrincipal UserDetails userDetails) {
        UnitDto createdUnit = unitService.createUnit(unitDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnit);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UnitDto> updateUnit(@PathVariable Long id, @RequestBody UnitCRUDDto unitCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        UnitDto updatedUnit = unitService.updateUnit(id, unitCRUDDto, userDetails.getUsername());
        return ResponseEntity.ok(updatedUnit);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        unitService.deleteUnit(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

}
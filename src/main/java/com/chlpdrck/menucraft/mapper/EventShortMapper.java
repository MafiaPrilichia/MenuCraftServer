package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventShortMapper {
    Event toEntity(EventShortDto eventShortDto);

    EventShortDto toEventShortDto(Event event);
}
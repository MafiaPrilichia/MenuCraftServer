package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Event;
import com.chlpdrck.menucraft.mapper.dto.EventCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.EventDto;
import com.chlpdrck.menucraft.mapper.dto.EventShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    Event toEntity(EventDto eventDto);

    EventDto toEventDto(Event event);

    Event toEntity(EventCRUDDto eventDto);

    EventCRUDDto toEventCRUDDto(Event event);

    Event toEntity(EventShortDto eventShortDto);

    EventShortDto toEventShortDto(Event event);
}
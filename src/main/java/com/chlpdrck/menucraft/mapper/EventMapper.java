package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.Event;
import com.chlpdrck.menucraft.mapper.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {
    Event toEntity(EventDto eventDto);

    EventDto toEventDto(Event event);
}
package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.entity.Event;
import com.chlpdrck.menucraft.mapper.dto.EventCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.EventDto;
import com.chlpdrck.menucraft.mapper.dto.EventShortDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsByOwnerUsername(String username);
    Event getEventEntityById(Long id, String username);
    EventCRUDDto createEvent(EventCRUDDto eventDto, String username);
    EventDto updateEvent(Long id, EventCRUDDto eventDto, String username);
    void deleteEvent(Long id, String username);
    EventDto getEventById(Long id, String username);
}

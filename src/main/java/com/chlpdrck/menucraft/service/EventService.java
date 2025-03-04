package com.chlpdrck.menucraft.service;

import com.chlpdrck.menucraft.mapper.EventCRUDDto;
import com.chlpdrck.menucraft.mapper.EventDto;
import com.chlpdrck.menucraft.mapper.EventShortDto;

import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsByOwnerUsername(String username);
    EventCRUDDto createEvent(EventCRUDDto eventDto, String username);
    EventCRUDDto updateEvent(Long id, EventCRUDDto eventDto, String username);
    void deleteEvent(Long id, String username);
    EventDto getEventById(Long id, String username);
}

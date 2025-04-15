package com.chlpdrck.menucraft.service.impl;

import com.chlpdrck.menucraft.entity.Event;
import com.chlpdrck.menucraft.entity.User;
import com.chlpdrck.menucraft.mapper.dto.EventCRUDDto;
import com.chlpdrck.menucraft.mapper.dto.EventDto;
import com.chlpdrck.menucraft.mapper.EventMapper;
import com.chlpdrck.menucraft.mapper.dto.EventShortDto;
import com.chlpdrck.menucraft.repository.EventRepository;
import com.chlpdrck.menucraft.repository.UserRepository;
import com.chlpdrck.menucraft.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getEventsByOwnerUsername(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return eventRepository.findByOwner(owner)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventDto getEventById(Long id, String username) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to look on this event");
        }

        return eventMapper.toEventDto(event);
    }

    @Transactional
    @Override
    public EventCRUDDto createEvent(EventCRUDDto eventDto, String username) {
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventMapper.toEntity(eventDto);
        event.setOwner(owner);
        event.setCreatedAt(LocalDateTime.now());

        event = eventRepository.save(event);
        return eventMapper.toEventCRUDDto(event);
    }

    @Transactional
    @Override
    public EventDto updateEvent(Long id, EventCRUDDto eventDto, String username) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to update this event");
        }

        event.setName(eventDto.getName());
        event.setTheme(eventDto.getTheme());
        event.setLocation(eventDto.getLocation());
        event.setDescription(eventDto.getDescription());
        event.setGuests(eventDto.getGuests());
        event.setEventDate(eventDto.getEventDate());
        event.setUpdatedAt(LocalDateTime.now());

        event = eventRepository.save(event);
        return eventMapper.toEventDto(event);
    }

    @Transactional
    @Override
    public void deleteEvent(Long id, String username) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to delete this event");
        }

        eventRepository.delete(event);
    }
}
package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.mapper.EventCRUDDto;
import com.chlpdrck.menucraft.mapper.EventDto;
import com.chlpdrck.menucraft.mapper.EventShortDto;
import com.chlpdrck.menucraft.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/owned")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventShortDto>> getOwnedEvents(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        List<EventShortDto> ownedEvents = eventService.getEventsByOwnerUsername(username);

        return ResponseEntity.ok(ownedEvents);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        EventDto event = eventService.getEventById(id, username);

        return ResponseEntity.ok(event);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventCRUDDto> createEvent(@RequestBody EventCRUDDto eventCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        EventCRUDDto createdEvent = eventService.createEvent(eventCRUDDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventCRUDDto eventCRUDDto, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        EventDto updatedEvent = eventService.updateEvent(id, eventCRUDDto, username);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        eventService.deleteEvent(id, username);
        return ResponseEntity.noContent().build();
    }

}
package com.chlpdrck.menucraft.mapper.dto;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Event}
 */
@Value
public class EventDto {
    Long id;
    String name;
    String theme;
    LocalDateTime eventDate;
    String location;
    String description;
    Integer guests;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
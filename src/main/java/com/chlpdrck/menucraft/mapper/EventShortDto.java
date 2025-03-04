package com.chlpdrck.menucraft.mapper;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Event}
 */
@Value
public class EventShortDto {
    Long id;
    String name;
    String theme;
    LocalDateTime eventDate;
    String location;
    Integer guests;
}
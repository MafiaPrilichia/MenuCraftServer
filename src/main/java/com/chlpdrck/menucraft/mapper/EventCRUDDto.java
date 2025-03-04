package com.chlpdrck.menucraft.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.Event}
 */
@Value
public class EventCRUDDto {
    String name;
    String theme;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime eventDate;
    String location;
    String description;
    Integer guests;
}
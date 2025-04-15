package com.chlpdrck.menucraft.mapper.dto;

import com.chlpdrck.menucraft.entity.Role;
import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.User}
 */
@Value
public class UserDto {
    Long id;
    String username;
    Role role;
}
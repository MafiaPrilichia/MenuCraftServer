package com.chlpdrck.menucraft.mapper.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * DTO for {@link com.chlpdrck.menucraft.entity.User}
 */
@Getter
@Setter
@Value
public class UserAuthDto {
    String username;
    String password;
}
package com.chlpdrck.menucraft.mapper;

import com.chlpdrck.menucraft.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAuthMapper {
    User toEntity(UserAuthDto userAuthDto);

    UserAuthDto toUserAuthDto(User user);
}
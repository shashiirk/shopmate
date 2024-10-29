package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.User;
import dev.shashiirk.shopmate.dto.UserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
}

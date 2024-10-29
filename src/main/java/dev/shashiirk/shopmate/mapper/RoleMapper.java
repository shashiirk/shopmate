package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Role;
import dev.shashiirk.shopmate.dto.RoleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
}

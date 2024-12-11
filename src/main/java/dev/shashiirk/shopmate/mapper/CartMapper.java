package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.dto.CartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
}

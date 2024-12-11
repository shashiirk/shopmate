package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Wishlist;
import dev.shashiirk.shopmate.dto.WishlistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Wishlist} and its DTO {@link WishlistDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistMapper extends EntityMapper<WishlistDTO, Wishlist> {
}

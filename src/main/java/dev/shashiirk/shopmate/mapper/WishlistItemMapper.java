package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.WishlistItem;
import dev.shashiirk.shopmate.dto.WishlistItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link WishlistItem} and its DTO {@link WishlistItemDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistItemMapper extends EntityMapper<WishlistItemDTO, WishlistItem> {
}

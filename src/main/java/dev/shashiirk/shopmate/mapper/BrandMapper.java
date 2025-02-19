package dev.shashiirk.shopmate.mapper;

import dev.shashiirk.shopmate.domain.Brand;
import dev.shashiirk.shopmate.dto.BrandDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Brand} and its DTO {@link BrandDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
}

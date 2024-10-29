package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Brand;
import dev.shashiirk.shopmate.dto.BrandDTO;
import dev.shashiirk.shopmate.mapper.BrandMapper;
import dev.shashiirk.shopmate.repository.BrandRepository;
import dev.shashiirk.shopmate.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        return brandMapper.toDTO(brand);
    }

    @Override
    public Optional<BrandDTO> partialUpdate(BrandDTO brandDTO) {
        return brandRepository.findById(brandDTO.getId()).map(brand -> {
            brandMapper.partialUpdate(brandDTO, brand);
            return brand;
        }).map(brandRepository::save).map(brandMapper::toDTO);
    }

    @Override
    public Optional<BrandDTO> findOne(Long id) {
        return brandRepository.findById(id).map(brandMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }

}

package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Wishlist;
import dev.shashiirk.shopmate.dto.WishlistDTO;
import dev.shashiirk.shopmate.mapper.WishlistMapper;
import dev.shashiirk.shopmate.repository.WishlistRepository;
import dev.shashiirk.shopmate.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Wishlist}.
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final WishlistMapper wishlistMapper;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, WishlistMapper wishlistMapper) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistMapper = wishlistMapper;
    }

    @Override
    public WishlistDTO save(WishlistDTO wishlistDTO) {
        Wishlist wishlist = wishlistMapper.toEntity(wishlistDTO);
        wishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.toDTO(wishlist);
    }

    @Override
    public Optional<WishlistDTO> partialUpdate(WishlistDTO wishlistDTO) {
        return wishlistRepository.findById(wishlistDTO.getId()).map(wishlist -> {
            wishlistMapper.partialUpdate(wishlistDTO, wishlist);
            return wishlist;
        }).map(wishlistRepository::save).map(wishlistMapper::toDTO);
    }

    @Override
    public Optional<WishlistDTO> findOne(Long id) {
        return wishlistRepository.findById(id).map(wishlistMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        wishlistRepository.deleteById(id);
    }

}

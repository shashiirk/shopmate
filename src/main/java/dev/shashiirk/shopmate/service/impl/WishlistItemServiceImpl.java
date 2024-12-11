package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.WishlistItem;
import dev.shashiirk.shopmate.dto.WishlistItemDTO;
import dev.shashiirk.shopmate.enumeration.WishlistItemStatus;
import dev.shashiirk.shopmate.mapper.WishlistItemMapper;
import dev.shashiirk.shopmate.repository.WishlistItemRepository;
import dev.shashiirk.shopmate.service.WishlistItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WishlistItem}.
 */
@Service
public class WishlistItemServiceImpl implements WishlistItemService {

    private final WishlistItemRepository wishlistItemRepository;

    private final WishlistItemMapper wishlistItemMapper;

    public WishlistItemServiceImpl(WishlistItemRepository wishlistItemRepository,
                                   WishlistItemMapper wishlistItemMapper) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistItemMapper = wishlistItemMapper;
    }

    @Override
    public WishlistItemDTO save(WishlistItemDTO wishlistItemDTO) {
        WishlistItem wishlistItem = wishlistItemMapper.toEntity(wishlistItemDTO);
        wishlistItem = wishlistItemRepository.save(wishlistItem);
        return wishlistItemMapper.toDTO(wishlistItem);
    }

    @Override
    public Optional<WishlistItemDTO> partialUpdate(WishlistItemDTO wishlistItemDTO) {
        return wishlistItemRepository.findById(wishlistItemDTO.getId()).map(wishlistItem -> {
            wishlistItemMapper.partialUpdate(wishlistItemDTO, wishlistItem);
            return wishlistItem;
        }).map(wishlistItemRepository::save).map(wishlistItemMapper::toDTO);
    }

    @Override
    public Optional<WishlistItemDTO> findOne(Long id) {
        return wishlistItemRepository.findById(id).map(wishlistItemMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        wishlistItemRepository.deleteById(id);
    }

    @Override
    public List<WishlistItemDTO> findAllActiveItemsByWishlistId(Long wishlistId) {
        return wishlistItemRepository
                .findAllByWishlistIdAndStatus(wishlistId, WishlistItemStatus.ADDED)
                .stream()
                .map(wishlistItemMapper::toDTO)
                .toList();
    }
    
}

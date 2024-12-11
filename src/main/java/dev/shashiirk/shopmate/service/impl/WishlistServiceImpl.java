package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Wishlist;
import dev.shashiirk.shopmate.dto.*;
import dev.shashiirk.shopmate.enumeration.WishlistItemStatus;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.exception.UnauthorizedException;
import dev.shashiirk.shopmate.mapper.WishlistMapper;
import dev.shashiirk.shopmate.repository.WishlistRepository;
import dev.shashiirk.shopmate.service.AuthService;
import dev.shashiirk.shopmate.service.ProductService;
import dev.shashiirk.shopmate.service.WishlistItemService;
import dev.shashiirk.shopmate.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Wishlist}.
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final WishlistMapper wishlistMapper;

    private final AuthService authService;

    private final WishlistItemService wishlistItemService;

    private final ProductService productService;

    public WishlistServiceImpl(
            WishlistRepository wishlistRepository,
            WishlistMapper wishlistMapper,
            AuthService authService,
            WishlistItemService wishlistItemService,
            ProductService productService
    ) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistMapper = wishlistMapper;
        this.authService = authService;
        this.wishlistItemService = wishlistItemService;
        this.productService = productService;
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

    /**
     * Get the current wishlist of the logged-in user.
     *
     * @return The current wishlist.
     */
    private WishlistDTO getCurrentWishlist() {
        UserDTO currentUser = authService.getCurrentUser().orElseThrow(UnauthorizedException::new);

        return wishlistRepository.findByUserId(currentUser.getId())
                                 .map(wishlistMapper::toDTO)
                                 .orElseGet(() -> {
                                     WishlistDTO wishlistDTO = WishlistDTO.builder()
                                                                          .user(currentUser)
                                                                          .build();
                                     return save(wishlistDTO);
                                 });
    }

    /**
     * Get the items in the current wishlist of the logged-in user.
     *
     * @return The items in the current wishlist.
     */
    private List<WishlistItemDTO> getWishlistItems(Long wishlistId) {
        return wishlistItemService.findAllActiveItemsByWishlistId(wishlistId);
    }

    @Override
    public WishlistResponseDTO getProducts() {
        WishlistDTO currentWishlist = getCurrentWishlist();
        List<WishlistItemDTO> wishlistItems = getWishlistItems(currentWishlist.getId());
        List<ProductDTO> products = wishlistItems
                .stream()
                .map(WishlistItemDTO::getProduct)
                .toList();

        return WishlistResponseDTO.builder()
                                  .products(products)
                                  .build();
    }

    @Override
    public WishlistResponseDTO addProduct(WishlistActionDTO wishlistActionDTO) {
        Long productId = wishlistActionDTO.getProductId();
        ProductDTO product = productService.findOne(productId)
                                           .orElseThrow(() -> new NotFoundException("Product not found"));
        WishlistDTO currentWishlist = getCurrentWishlist();
        getWishlistItems(currentWishlist.getId())
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId) &&
                                item.getStatus() == WishlistItemStatus.ADDED)
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            throw new BadRequestException("Product already exists in wishlist");
                        },
                        () -> {
                            WishlistItemDTO wishlistItemDTO = WishlistItemDTO.builder()
                                                                             .product(product)
                                                                             .wishlist(currentWishlist)
                                                                             .status(WishlistItemStatus.ADDED)
                                                                             .build();
                            wishlistItemService.save(wishlistItemDTO);
                        }
                );

        return getProducts();
    }

    @Override
    public void removeProduct(WishlistActionDTO wishlistActionDTO) {
        Long productId = wishlistActionDTO.getProductId();
        if (!productService.existsById(productId)) {
            throw new NotFoundException("Product not found");
        }
        WishlistDTO currentWishlist = getCurrentWishlist();
        List<WishlistItemDTO> wishlistItems = getWishlistItems(currentWishlist.getId());
        wishlistItems
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            item.setStatus(WishlistItemStatus.REMOVED);
                            wishlistItemService.save(item);
                        },
                        () -> {
                            throw new NotFoundException("Product not found in wishlist");
                        }
                );
    }

}

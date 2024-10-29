package dev.shashiirk.shopmate.controller;


import dev.shashiirk.shopmate.domain.WishlistItem;
import dev.shashiirk.shopmate.dto.WishlistItemDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.WishlistItemRepository;
import dev.shashiirk.shopmate.service.WishlistItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link WishlistItem}.
 */
@RestController
@RequestMapping("/api/wishlist-items")
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    private final WishlistItemRepository wishlistItemRepository;

    public WishlistItemController(WishlistItemService wishlistItemService,
                                  WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemService = wishlistItemService;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    /**
     * Retrieves a wishlist item by ID.
     *
     * @param id The ID of the wishlist item to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the wishlistItemDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<WishlistItemDTO> getWishlistItem(@PathVariable Long id) {
        Optional<WishlistItemDTO> wishlistItemDTO = wishlistItemService.findOne(id);
        return wishlistItemDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new wishlist item.
     *
     * @param wishlistItemDTO The wishlistItemDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new wishlistItemDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WishlistItemDTO> createWishlistItem(
            @RequestBody WishlistItemDTO wishlistItemDTO) throws URISyntaxException {
        if (wishlistItemDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        WishlistItemDTO result = wishlistItemService.save(wishlistItemDTO);
        return ResponseEntity.created(new URI("/api/wishlist-items/" + result.getId())).body(result);
    }

    /**
     * Updates an existing wishlist item.
     *
     * @param id              The ID of the wishlist item to update.
     * @param wishlistItemDTO The wishlistItemDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated wishlistItemDTO, or with status 400 (Bad Request) if the wishlistItemDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WishlistItemDTO> updateWishlistItem(@PathVariable Long id,
                                                              @RequestBody WishlistItemDTO wishlistItemDTO) {
        if (wishlistItemDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, wishlistItemDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!wishlistItemRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        WishlistItemDTO result = wishlistItemService.save(wishlistItemDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing wishlist item.
     *
     * @param id              The ID of the wishlist item to update.
     * @param wishlistItemDTO The wishlistItemDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated wishlistItemDTO, or with status 400 (Bad Request) if the wishlistItemDTO is not valid,
     * or with status 404 (Not Found) if the wishlistItemDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<WishlistItemDTO> partialUpdateWishlistItem(@PathVariable Long id,
                                                                     @RequestBody WishlistItemDTO wishlistItemDTO) {
        if (wishlistItemDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, wishlistItemDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!wishlistItemRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<WishlistItemDTO> result = wishlistItemService.partialUpdate(wishlistItemDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a wishlist item by ID.
     *
     * @param id The ID of the wishlist item to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable Long id) {
        wishlistItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


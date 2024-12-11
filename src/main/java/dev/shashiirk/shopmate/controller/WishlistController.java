package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.Wishlist;
import dev.shashiirk.shopmate.dto.WishlistActionDTO;
import dev.shashiirk.shopmate.dto.WishlistDTO;
import dev.shashiirk.shopmate.dto.WishlistResponseDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.WishlistRepository;
import dev.shashiirk.shopmate.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Wishlist}.
 */
@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    private final WishlistRepository wishlistRepository;

    public WishlistController(WishlistService wishlistService, WishlistRepository wishlistRepository) {
        this.wishlistService = wishlistService;
        this.wishlistRepository = wishlistRepository;
    }

    /**
     * Retrieves a wishlist by ID.
     *
     * @param id The ID of the wishlist to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the wishlistDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<WishlistDTO> getWishlist(@PathVariable Long id) {
        Optional<WishlistDTO> wishlistDTO = wishlistService.findOne(id);
        return wishlistDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new wishlist.
     *
     * @param wishlistDTO The wishlistDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new wishlistDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WishlistDTO> createWishlist(@RequestBody WishlistDTO wishlistDTO) throws URISyntaxException {
        if (wishlistDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        WishlistDTO result = wishlistService.save(wishlistDTO);
        return ResponseEntity.created(new URI("/api/wishlists/" + result.getId())).body(result);
    }

    /**
     * Updates an existing wishlist.
     *
     * @param id          The ID of the wishlist to update.
     * @param wishlistDTO The wishlistDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated wishlistDTO, or with status 400 (Bad Request) if the wishlistDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WishlistDTO> updateWishlist(@PathVariable Long id, @RequestBody WishlistDTO wishlistDTO) {
        if (wishlistDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, wishlistDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!wishlistRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        WishlistDTO result = wishlistService.save(wishlistDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing wishlist.
     *
     * @param id          The ID of the wishlist to update.
     * @param wishlistDTO The wishlistDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated wishlistDTO, or with status 400 (Bad Request) if the wishlistDTO is not valid,
     * or with status 404 (Not Found) if the wishlistDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<WishlistDTO> partialUpdateWishlist(@PathVariable Long id,
                                                             @RequestBody WishlistDTO wishlistDTO) {
        if (wishlistDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, wishlistDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!wishlistRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<WishlistDTO> result = wishlistService.partialUpdate(wishlistDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a wishlist by ID.
     *
     * @param id The ID of the wishlist to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        wishlistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the current wishlist products of the logged-in user.
     *
     * @return The ResponseEntity with status 200 (OK) and with body the list of products.
     */
    @GetMapping("/products")
    public ResponseEntity<WishlistResponseDTO> getWishlistProducts() {
        WishlistResponseDTO wishlistResponseDTO = wishlistService.getProducts();
        return ResponseEntity.ok().body(wishlistResponseDTO);
    }

    /**
     * Adds a product to the current wishlist.
     *
     * @param wishlistActionDTO The item to add.
     * @return The ResponseEntity with status 200 (OK) and with body the added item.
     */
    @PostMapping("/products/add")
    public ResponseEntity<WishlistResponseDTO> addProductToWishlist(
            @RequestBody WishlistActionDTO wishlistActionDTO) {
        WishlistResponseDTO wishlistResponseDTO = wishlistService.addProduct(wishlistActionDTO);
        return ResponseEntity.ok().body(wishlistResponseDTO);
    }

    /**
     * Removes a product from the current wishlist.
     *
     * @param wishlistActionDTO The item to remove.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @PostMapping("/products/remove")
    public ResponseEntity<Void> removeProductFromWishlist(@RequestBody WishlistActionDTO wishlistActionDTO) {
        wishlistService.removeProduct(wishlistActionDTO);
        return ResponseEntity.noContent().build();
    }

}


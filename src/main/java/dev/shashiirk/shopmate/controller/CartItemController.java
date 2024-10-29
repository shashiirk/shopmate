package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.CartItem;
import dev.shashiirk.shopmate.dto.CartItemDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.CartItemRepository;
import dev.shashiirk.shopmate.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link CartItem}.
 */
@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    private final CartItemRepository cartItemRepository;

    public CartItemController(CartItemService cartItemService, CartItemRepository cartItemRepository) {
        this.cartItemService = cartItemService;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Retrieves a cart item by ID.
     *
     * @param id The ID of the cart item to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the cartItemDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable Long id) {
        Optional<CartItemDTO> cartItemDTO = cartItemService.findOne(id);
        return cartItemDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new cart item.
     *
     * @param cartItemDTO The cartItemDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new cartItemDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody CartItemDTO cartItemDTO) throws URISyntaxException {
        if (cartItemDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        CartItemDTO result = cartItemService.save(cartItemDTO);
        return ResponseEntity.created(new URI("/api/cart-items/" + result.getId())).body(result);
    }

    /**
     * Updates an existing cart item.
     *
     * @param id          The ID of the cart item to update.
     * @param cartItemDTO The cartItemDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated cartItemDTO, or with status 400 (Bad Request) if the cartItemDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestBody CartItemDTO cartItemDTO) {
        if (cartItemDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, cartItemDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!cartItemRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        CartItemDTO result = cartItemService.save(cartItemDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing cart item.
     *
     * @param id          The ID of the cart item to update.
     * @param cartItemDTO The cartItemDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated cartItemDTO, or with status 400 (Bad Request) if the cartItemDTO is not valid,
     * or with status 404 (Not Found) if the cartItemDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CartItemDTO> partialUpdateCartItem(@PathVariable Long id,
                                                             @RequestBody CartItemDTO cartItemDTO) {
        if (cartItemDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, cartItemDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!cartItemRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<CartItemDTO> result = cartItemService.partialUpdate(cartItemDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a cart item by ID.
     *
     * @param id The ID of the cart item to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


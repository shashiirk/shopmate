package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.Cart;
import dev.shashiirk.shopmate.dto.CartActionDTO;
import dev.shashiirk.shopmate.dto.CartDTO;
import dev.shashiirk.shopmate.dto.CartResponseDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.CartRepository;
import dev.shashiirk.shopmate.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Cart}.
 */
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    private final CartRepository cartRepository;

    public CartController(CartService cartService, CartRepository cartRepository) {
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    /**
     * Retrieves a cart by ID.
     *
     * @param id The ID of the cart to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the cartDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        Optional<CartDTO> cartDTO = cartService.findOne(id);
        return cartDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new cart.
     *
     * @param cartDTO The cartDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new cartDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) throws URISyntaxException {
        if (cartDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId())).body(result);
    }

    /**
     * Updates an existing cart.
     *
     * @param id      The ID of the cart to update.
     * @param cartDTO The cartDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated cartDTO, or with status 400 (Bad Request) if the cartDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        if (cartDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, cartDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!cartRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing cart.
     *
     * @param id      The ID of the cart to update.
     * @param cartDTO The cartDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated cartDTO, or with status 400 (Bad Request) if the cartDTO is not valid,
     * or with status 404 (Not Found) if the cartDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CartDTO> partialUpdateCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        if (cartDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, cartDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!cartRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<CartDTO> result = cartService.partialUpdate(cartDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a cart by ID.
     *
     * @param id The ID of the cart to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.OrderItem;
import dev.shashiirk.shopmate.dto.OrderItemDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.OrderItemRepository;
import dev.shashiirk.shopmate.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link OrderItem}.
 */
@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    private final OrderItemRepository orderItemRepository;

    public OrderItemController(OrderItemService orderItemService, OrderItemRepository orderItemRepository) {
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Retrieves an order item by ID.
     *
     * @param id The ID of the order item to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the orderItemDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItem(@PathVariable Long id) {
        Optional<OrderItemDTO> orderItemDTO = orderItemService.findOne(id);
        return orderItemDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new order item.
     *
     * @param orderItemDTO The orderItemDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new orderItemDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderItemDTO> createOrderItem(
            @RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        if (orderItemDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.created(new URI("/api/order-items/" + result.getId())).body(result);
    }

    /**
     * Updates an existing order item.
     *
     * @param id           The ID of the order item to update.
     * @param orderItemDTO The orderItemDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated orderItemDTO, or with status 400 (Bad Request) if the orderItemDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO) {
        if (orderItemDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, orderItemDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!orderItemRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing order item.
     *
     * @param id           The ID of the order item to update.
     * @param orderItemDTO The orderItemDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated orderItemDTO, or with status 400 (Bad Request) if the orderItemDTO is not valid,
     * or with status 404 (Not Found) if the orderItemDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<OrderItemDTO> partialUpdateOrderItem(@PathVariable Long id,
                                                               @RequestBody OrderItemDTO orderItemDTO) {
        if (orderItemDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, orderItemDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!orderItemRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<OrderItemDTO> result = orderItemService.partialUpdate(orderItemDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes an order item by ID.
     *
     * @param id The ID of the order item to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


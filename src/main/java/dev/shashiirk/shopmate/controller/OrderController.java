package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.Order;
import dev.shashiirk.shopmate.dto.OrderDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.OrderRepository;
import dev.shashiirk.shopmate.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Order}.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves an order by ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the orderDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        Optional<OrderDTO> orderDTO = orderService.findOne(id);
        return orderDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new order.
     *
     * @param orderDTO The orderDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new orderDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        if (orderDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        OrderDTO result = orderService.save(orderDTO);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).body(result);
    }

    /**
     * Updates an existing order.
     *
     * @param id       The ID of the order to update.
     * @param orderDTO The orderDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated orderDTO, or with status 400 (Bad Request) if the orderDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        if (orderDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        OrderDTO result = orderService.save(orderDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing order.
     *
     * @param id       The ID of the order to update.
     * @param orderDTO The orderDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated orderDTO, or with status 400 (Bad Request) if the orderDTO is not valid,
     * or with status 404 (Not Found) if the orderDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> partialUpdateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        if (orderDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<OrderDTO> result = orderService.partialUpdate(orderDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes an order by ID.
     *
     * @param id The ID of the order to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


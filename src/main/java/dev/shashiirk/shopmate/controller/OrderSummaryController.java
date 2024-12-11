package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.OrderSummary;
import dev.shashiirk.shopmate.dto.OrderSummaryDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.OrderSummaryRepository;
import dev.shashiirk.shopmate.service.OrderSummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link OrderSummary}.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderSummaryController {

    private final OrderSummaryService orderSummaryService;

    private final OrderSummaryRepository orderSummaryRepository;

    public OrderSummaryController(OrderSummaryService orderSummaryService,
                                  OrderSummaryRepository orderSummaryRepository) {
        this.orderSummaryService = orderSummaryService;
        this.orderSummaryRepository = orderSummaryRepository;
    }

    /**
     * Retrieves an order by ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the orderDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderSummaryDTO> getOrder(@PathVariable Long id) {
        Optional<OrderSummaryDTO> orderDTO = orderSummaryService.findOne(id);
        return orderDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new order.
     *
     * @param orderSummaryDTO The orderDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new orderDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrderSummaryDTO> createOrder(
            @RequestBody OrderSummaryDTO orderSummaryDTO) throws URISyntaxException {
        if (orderSummaryDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        OrderSummaryDTO result = orderSummaryService.save(orderSummaryDTO);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId())).body(result);
    }

    /**
     * Updates an existing order.
     *
     * @param id              The ID of the order to update.
     * @param orderSummaryDTO The orderDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated orderDTO, or with status 400 (Bad Request) if the orderDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderSummaryDTO> updateOrder(@PathVariable Long id,
                                                       @RequestBody OrderSummaryDTO orderSummaryDTO) {
        if (orderSummaryDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, orderSummaryDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!orderSummaryRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        OrderSummaryDTO result = orderSummaryService.save(orderSummaryDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing order.
     *
     * @param id              The ID of the order to update.
     * @param orderSummaryDTO The orderDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated orderDTO, or with status 400 (Bad Request) if the orderDTO is not valid,
     * or with status 404 (Not Found) if the orderDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<OrderSummaryDTO> partialUpdateOrder(@PathVariable Long id,
                                                              @RequestBody OrderSummaryDTO orderSummaryDTO) {
        if (orderSummaryDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, orderSummaryDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!orderSummaryRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<OrderSummaryDTO> result = orderSummaryService.partialUpdate(orderSummaryDTO);
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
        orderSummaryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


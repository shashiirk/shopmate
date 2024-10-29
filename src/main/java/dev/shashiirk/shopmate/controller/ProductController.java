package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.Product;
import dev.shashiirk.shopmate.dto.ProductDTO;
import dev.shashiirk.shopmate.enumeration.ProductsSortOrder;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.ProductRepository;
import dev.shashiirk.shopmate.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Product}.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    /**
     * Retrieves a product by ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the productDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return productDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Retrieves all products by filter.
     *
     * @param brandIds    The brand IDs to filter by.
     * @param categoryIds The category IDs to filter by.
     * @param sortOrder   The sort order to apply.
     * @return The ResponseEntity with status 200 (OK) and with body the list of productDTOs.
     */
    @GetMapping("")
    public ResponseEntity<List<ProductDTO>> getProductsByFilter(
            @RequestParam(value = "brandIds", required = false) List<Long> brandIds,
            @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
            @RequestParam(value = "sortOrder", required = false) ProductsSortOrder sortOrder) {
        List<ProductDTO> productDTOs = productService.findByFilter(brandIds, categoryIds, sortOrder);
        return ResponseEntity.ok().body(productDTOs);
    }

    /**
     * Creates a new product.
     *
     * @param productDTO The productDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new productDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        if (productDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId())).body(result);
    }

    /**
     * Updates an existing product.
     *
     * @param id         The ID of the product to update.
     * @param productDTO The productDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated productDTO, or with status 400 (Bad Request) if the productDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!productRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing product.
     *
     * @param id         The ID of the product to update.
     * @param productDTO The productDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated productDTO, or with status 400 (Bad Request) if the productDTO is not valid,
     * or with status 404 (Not Found) if the productDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> partialUpdateProduct(@PathVariable Long id,
                                                           @NotNull @RequestBody ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!productRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<ProductDTO> result = productService.partialUpdate(productDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a product by ID.
     *
     * @param id The ID of the product to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

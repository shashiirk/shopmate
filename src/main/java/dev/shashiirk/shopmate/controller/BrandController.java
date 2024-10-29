package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.Brand;
import dev.shashiirk.shopmate.dto.BrandDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.BrandRepository;
import dev.shashiirk.shopmate.service.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Brand}.
 */
@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    private final BrandRepository brandRepository;

    public BrandController(BrandService brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    /**
     * Retrieves a brand by ID.
     *
     * @param id The ID of the brand to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the brandDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrand(@PathVariable Long id) {
        Optional<BrandDTO> brandDTO = brandService.findOne(id);
        return brandDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new brand.
     *
     * @param brandDTO The brandDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new brandDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BrandDTO> createBrand(@Valid @RequestBody BrandDTO brandDTO) throws URISyntaxException {
        if (brandDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.created(new URI("/api/brands/" + result.getId())).body(result);
    }

    /**
     * Updates an existing brand.
     *
     * @param id       The ID of the brand to update.
     * @param brandDTO The brandDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated brandDTO, or with status 400 (Bad Request) if the brandDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandDTO brandDTO) {
        if (brandDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, brandDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!brandRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing brand.
     *
     * @param id       The ID of the brand to update.
     * @param brandDTO The brandDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated brandDTO, or with status 400 (Bad Request) if the brandDTO is not valid,
     * or with status 404 (Not Found) if the brandDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BrandDTO> partialUpdateBrand(@PathVariable Long id, @NotNull @RequestBody BrandDTO brandDTO) {
        if (brandDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, brandDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!brandRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<BrandDTO> result = brandService.partialUpdate(brandDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a brand by ID.
     *
     * @param id The ID of the brand to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

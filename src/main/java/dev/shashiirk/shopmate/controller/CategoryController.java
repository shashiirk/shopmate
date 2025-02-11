package dev.shashiirk.shopmate.controller;

import dev.shashiirk.shopmate.domain.Category;
import dev.shashiirk.shopmate.dto.CategoryDTO;
import dev.shashiirk.shopmate.exception.BadRequestException;
import dev.shashiirk.shopmate.exception.NotFoundException;
import dev.shashiirk.shopmate.repository.CategoryRepository;
import dev.shashiirk.shopmate.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Category}.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves a category by ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the categoryDTO, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return categoryDTO.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Creates a new category.
     *
     * @param categoryDTO The categoryDTO to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new categoryDTO, or with status 400 (Bad Request) if the body has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CategoryDTO> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        if (categoryDTO.getId() != null) {
            throw new BadRequestException("Invalid ID");
        }

        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId())).body(result);
    }

    /**
     * Updates an existing category.
     *
     * @param id          The ID of the category to update.
     * @param categoryDTO The categoryDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated categoryDTO, or with status 400 (Bad Request) if the categoryDTO is not valid.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!categoryRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Partially updates an existing category.
     *
     * @param id          The ID of the category to update.
     * @param categoryDTO The categoryDTO to update.
     * @return The ResponseEntity with status 200 (OK) and with body the updated categoryDTO, or with status 400 (Bad Request) if the categoryDTO is not valid,
     * or with status 404 (Not Found) if the categoryDTO is not found.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> partialUpdateCategory(@PathVariable Long id,
                                                             @NotNull @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null) {
            throw new BadRequestException("Invalid ID");
        }
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new BadRequestException("Invalid ID");
        }
        if (!categoryRepository.existsById(id)) {
            throw new BadRequestException("Entity doesn't exist");
        }

        Optional<CategoryDTO> result = categoryService.partialUpdate(categoryDTO);
        return result.map(ResponseEntity::ok).orElseThrow(NotFoundException::new);
    }

    /**
     * Deletes a category by ID.
     *
     * @param id The ID of the category to delete.
     * @return The ResponseEntity with status 204 (NO_CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
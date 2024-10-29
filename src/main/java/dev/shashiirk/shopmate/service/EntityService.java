package dev.shashiirk.shopmate.service;

import java.util.Optional;

/**
 * Contract for a generic service.
 *
 * @param <D> - DTO type parameter.
 */
public interface EntityService<D> {

    /**
     * Save an entity.
     *
     * @param dto the DTO representing the entity to save
     * @return the saved entity DTO
     */
    D save(D dto);

    /**
     * Partially update an entity.
     *
     * @param dto the DTO representing the entity with updated fields
     * @return the updated entity DTO if it exists, otherwise an empty Optional
     */
    Optional<D> partialUpdate(D dto);

    /**
     * Find an entity by its ID.
     *
     * @param id the ID of the entity to find
     * @return an Optional containing the entity DTO if found, otherwise an empty Optional
     */
    Optional<D> findOne(Long id);

    /**
     * Delete an entity by its ID.
     *
     * @param id the ID of the entity to delete
     */
    void delete(Long id);

}

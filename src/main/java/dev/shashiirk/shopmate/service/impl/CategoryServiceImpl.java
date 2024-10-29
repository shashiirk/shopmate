package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Category;
import dev.shashiirk.shopmate.dto.CategoryDTO;
import dev.shashiirk.shopmate.mapper.CategoryMapper;
import dev.shashiirk.shopmate.repository.CategoryRepository;
import dev.shashiirk.shopmate.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Override
    public Optional<CategoryDTO> partialUpdate(CategoryDTO categoryDTO) {
        return categoryRepository.findById(categoryDTO.getId()).map(category -> {
            categoryMapper.partialUpdate(categoryDTO, category);
            return category;
        }).map(categoryRepository::save).map(categoryMapper::toDTO);
    }

    @Override
    public Optional<CategoryDTO> findOne(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}

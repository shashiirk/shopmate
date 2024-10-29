package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Product;
import dev.shashiirk.shopmate.dto.ProductDTO;
import dev.shashiirk.shopmate.dto.UserDTO;
import dev.shashiirk.shopmate.enumeration.ProductsSortOrder;
import dev.shashiirk.shopmate.mapper.ProductMapper;
import dev.shashiirk.shopmate.repository.ProductRepository;
import dev.shashiirk.shopmate.service.AuthService;
import dev.shashiirk.shopmate.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthService authService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              AuthService authService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.authService = authService;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        return productRepository.findById(productDTO.getId()).map(product -> {
            productMapper.partialUpdate(productDTO, product);
            return product;
        }).map(productRepository::save).map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDTO> findOne(Long id) {
        return productRepository.findById(id).map(productMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> findByFilter(List<Long> brandIds, List<Long> categoryIds, ProductsSortOrder sortOrder) {
        Optional<UserDTO> currentUser = authService.getCurrentUser();

        Sort sort = Sort.unsorted(); // Default sort order, no sorting applied

        if (sortOrder != null) {
            // Apply sorting based on the sort order
            if (sortOrder == ProductsSortOrder.PRICE_ASC) {
                sort = Sort.by("sellingPrice").ascending();
            } else if (sortOrder == ProductsSortOrder.PRICE_DESC) {
                sort = Sort.by("sellingPrice").descending();
            }
        }

        return productRepository
                .findAllByFilter(brandIds, categoryIds, sort)
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

}

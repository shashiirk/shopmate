package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.OrderItem;
import dev.shashiirk.shopmate.dto.OrderItemDTO;
import dev.shashiirk.shopmate.mapper.OrderItemMapper;
import dev.shashiirk.shopmate.repository.OrderItemRepository;
import dev.shashiirk.shopmate.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderItem}.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderItemDTO save(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(orderItem);
    }

    @Override
    public Optional<OrderItemDTO> partialUpdate(OrderItemDTO orderItemDTO) {
        return orderItemRepository.findById(orderItemDTO.getId()).map(orderItem -> {
            orderItemMapper.partialUpdate(orderItemDTO, orderItem);
            return orderItem;
        }).map(orderItemRepository::save).map(orderItemMapper::toDTO);
    }

    @Override
    public Optional<OrderItemDTO> findOne(Long id) {
        return orderItemRepository.findById(id).map(orderItemMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }

}

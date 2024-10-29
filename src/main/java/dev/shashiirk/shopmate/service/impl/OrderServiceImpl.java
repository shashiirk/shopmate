package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.Order;
import dev.shashiirk.shopmate.dto.OrderDTO;
import dev.shashiirk.shopmate.mapper.OrderMapper;
import dev.shashiirk.shopmate.repository.OrderRepository;
import dev.shashiirk.shopmate.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        return orderRepository.findById(orderDTO.getId()).map(order -> {
            orderMapper.partialUpdate(orderDTO, order);
            return order;
        }).map(orderRepository::save).map(orderMapper::toDTO);
    }

    @Override
    public Optional<OrderDTO> findOne(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

}

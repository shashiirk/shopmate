package dev.shashiirk.shopmate.service.impl;

import dev.shashiirk.shopmate.domain.OrderSummary;
import dev.shashiirk.shopmate.dto.OrderSummaryDTO;
import dev.shashiirk.shopmate.mapper.OrderSummaryMapper;
import dev.shashiirk.shopmate.repository.OrderSummaryRepository;
import dev.shashiirk.shopmate.service.OrderSummaryService;
import dev.shashiirk.shopmate.util.RandomIdGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderSummary}.
 */
@Service
public class OrderSummaryServiceImpl implements OrderSummaryService {

    private final OrderSummaryRepository orderSummaryRepository;

    private final OrderSummaryMapper orderSummaryMapper;

    public OrderSummaryServiceImpl(OrderSummaryRepository orderSummaryRepository,
                                   OrderSummaryMapper orderSummaryMapper) {
        this.orderSummaryRepository = orderSummaryRepository;
        this.orderSummaryMapper = orderSummaryMapper;
    }

    @Override
    public OrderSummaryDTO save(OrderSummaryDTO orderSummaryDTO) {
        OrderSummary orderSummary = orderSummaryMapper.toEntity(orderSummaryDTO);
        orderSummary = orderSummaryRepository.save(orderSummary);
        return orderSummaryMapper.toDTO(orderSummary);
    }

    @Override
    public Optional<OrderSummaryDTO> partialUpdate(OrderSummaryDTO orderSummaryDTO) {
        return orderSummaryRepository.findById(orderSummaryDTO.getId()).map(order -> {
            orderSummaryMapper.partialUpdate(orderSummaryDTO, order);
            return order;
        }).map(orderSummaryRepository::save).map(orderSummaryMapper::toDTO);
    }

    @Override
    public Optional<OrderSummaryDTO> findOne(Long id) {
        return orderSummaryRepository.findById(id).map(orderSummaryMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        orderSummaryRepository.deleteById(id);
    }

    @Override
    public String generateOrderId() {
        String orderNumber = RandomIdGenerator.generateRandomId(16);
        while (orderSummaryRepository.existsByOrderId(orderNumber)) {
            orderNumber = RandomIdGenerator.generateRandomId(16);
        }
        return orderNumber;
    }
}

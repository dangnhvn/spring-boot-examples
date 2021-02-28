package com.example.icommerce.services;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.icommerce.dtos.OrderDTO;
import com.example.icommerce.entities.Order;
import com.example.icommerce.entities.OrderDetail;
import com.example.icommerce.entities.OrderDetailPK;
import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.User;
import com.example.icommerce.mappers.OrderMapper;
import com.example.icommerce.models.PagingRequest;
import com.example.icommerce.repositories.OrderRepository;
import com.example.icommerce.utilities.OrderGenerator;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public Order getOrder (String orderNumber) {
        if ( StringHelper.isEmpty(orderNumber) ) {
            throw new IllegalArgumentException("Order number is missing");
        }
        return orderRepository.getByOrderNumber(orderNumber);
    }

    public OrderDTO getOrderDetailDTO (String orderNumber) {
        Order order = this.getOrder(orderNumber);

        return order == null ? null : this.convertToDTO(order);
    }

    private OrderDTO convertToDTO (Order order) {
        if ( order == null ) {
            return null;
        }

        if ( Objects.nonNull(order.getOrderDetailList()) && !order.getOrderDetailList().isEmpty() ) {

            OrderDTO dto = OrderMapper.INSTANCE.toDTO(order);

            return dto;
        }

        return null;
    }

    public Order createOrder (User user, Map<String, Integer> orderDetails, String status) {

        if ( user != null && userService.getUserByUsername(user.getUsername()) != null && orderDetails != null && !orderDetails.isEmpty() ) {
            Order order = new Order();
            order.setOrderNumber(OrderGenerator.generate());
            order.setUser(user);
            order.setStatus(status);

            for (Map.Entry<String, Integer> entry : orderDetails.entrySet()) {
                Product product = productService.getProductBySku(entry.getKey());
                OrderDetail orderDetail = new OrderDetail();
                OrderDetailPK orderDetailPK = new OrderDetailPK(order, product);
                orderDetail.setPk(orderDetailPK);
                orderDetail.setQuantity(entry.getValue());

                order.addOrderDetail(orderDetail);
            }

            order.setCreatedDate(Instant.now());

            return this.orderRepository.save(order);
        }

        return null;
    }

    public Page<OrderDTO> getOrdersByUsername (String username, PagingRequest pagingRequest) {
        User user = userService.getUserByUsername(username);
        if ( user == null ) {
            throw new IllegalArgumentException("User '" + username + "' not found");
        }

        Pageable pageable;

        if ( StringHelper.isEmpty(pagingRequest.getSortBy()) ) {
            pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), Sort.by(Sort.Order.desc("createdDate")));
        }
        else {
            Sort sort = Sort.by(Sort.Order.asc(pagingRequest.getSortBy()));
            if ( pagingRequest.getSortBy().startsWith("-") ) {
                sort = Sort.by(Sort.Order.desc(pagingRequest.getSortBy().substring(1)));
            }

            pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), sort);
        }

        Page<Order> orderPages = orderRepository.findAllByUser_Username(username, pageable);
        List<OrderDTO> orders = orderPages.getContent().stream().map(OrderMapper.INSTANCE::toDTO).collect(Collectors.toList());

        return new PageImpl<>(orders, pageable, orderPages.getTotalElements());

    }
}

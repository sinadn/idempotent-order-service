package ir.sinafood.orderservice.service;

import ir.sinafood.orderservice.dto.CreateOrderRequest;
import ir.sinafood.orderservice.dto.OrderItemRequest;
import ir.sinafood.orderservice.dto.OrderResponse;
import ir.sinafood.orderservice.entity.Order;
import ir.sinafood.orderservice.entity.OrderItem;
import ir.sinafood.orderservice.entity.Product;
import ir.sinafood.orderservice.repository.OrderItemRepository;
import ir.sinafood.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    public void createOrderItem(CreateOrderRequest request , Order order) {

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest item : request.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            Product product = new Product();
            product.setId(item.getProductId());
            orderItem.setProduct(product);
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);

    }


}
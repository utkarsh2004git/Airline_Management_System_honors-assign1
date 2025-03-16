package com.airline.repository;

import com.airline.dto.OrderInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderInfoRepository {

    private final RestTemplate restTemplate;
    Map<String, OrderInfo> orderInfoTable;

    public OrderInfoRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @PostConstruct
    public void init() {
        orderInfoTable = new HashMap<>();
    }


    public List<OrderInfo> getAllOrders(){
        return orderInfoTable.values()
                .stream().collect(Collectors.toList());
    }

    public OrderInfo createOrder(OrderInfo orderInfo) {
        String orderId = UUID.randomUUID().toString();
        OrderInfo updatedOrder = orderInfo.toBuilder().id(orderId).build();
        this.orderInfoTable.put(orderId, updatedOrder );

        return updatedOrder;
    }


    public OrderInfo getOrderById(String orderId){
        return orderInfoTable.get(orderId);
    }

}

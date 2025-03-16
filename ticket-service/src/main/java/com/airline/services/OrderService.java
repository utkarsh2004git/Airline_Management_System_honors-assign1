package com.airline.services;

import com.airline.dto.OrderInfo;
import com.airline.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<OrderInfo> getAllOrders(){
        return orderInfoRepository.getAllOrders();
    }

    public OrderInfo createOrder(OrderInfo orderInfo){
        OrderInfo createdOrderInfo =  orderInfoRepository.createOrder(orderInfo);
//        UserInfo userInfo = restTemplate.getForObject("http://localhost:9055/user-mgmt/users/"+orderInfo.getUserId(), UserInfo.class);

        String url = "http://localhost:9055//user-mgmt//users//"+orderInfo.getUserId()+"//"+createdOrderInfo.getId();
//        System.out.println(url);
        restTemplate.put(url, null);


        return createdOrderInfo;

    }

    public OrderInfo getOrder(String id) throws Exception{
        OrderInfo orderInfo = orderInfoRepository.getOrderById(id);
        if(orderInfo == null){
            throw new Exception("order not found");
        }
        return orderInfo;
    }

}

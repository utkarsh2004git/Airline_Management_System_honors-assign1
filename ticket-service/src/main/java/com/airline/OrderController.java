package com.airline;
import com.airline.dto.OrderInfo;
import com.airline.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    List<OrderInfo> getAllOrders(){
          return orderService.getAllOrders();
    }


    @PostMapping
    OrderInfo createOrder(@RequestBody OrderInfo orderInfo){

        return orderService.createOrder(orderInfo);
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderInfo>  getOrderById(@PathVariable String id){

        try{

            return ResponseEntity.ok(orderService.getOrder(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}

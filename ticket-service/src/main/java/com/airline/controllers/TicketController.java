package com.airline.controllers;
import com.airline.dto.TicketInfoDTO;
import com.airline.services.OrderService;
import com.airline.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    OrderService orderService;
    @Autowired
    private TicketService ticketService;

    @GetMapping
    List<TicketInfoDTO> getAllTickets(){
          return ticketService.getAllTickets();
    }


    @PostMapping
    TicketInfoDTO createTicket(@RequestBody TicketInfoDTO ticketInfo){

        return ticketService.createTicket(ticketInfo);
    }

    @GetMapping("/{id}")
    ResponseEntity<TicketInfoDTO>  getOrderById(@PathVariable String id){

        try{

            return ResponseEntity.ok(ticketService.getTicketById(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}

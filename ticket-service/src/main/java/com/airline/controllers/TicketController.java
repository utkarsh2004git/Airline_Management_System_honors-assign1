package com.airline.controllers;
import com.airline.dto.TicketInfoDTO;
import com.airline.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    List<TicketInfoDTO> getAllTickets(){
          return ticketService.getAllTickets();
    }


    @PostMapping
    Object createTicket(@RequestBody TicketInfoDTO ticketInfo){

        return ticketService.createTicket(ticketInfo);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object>  getOrderById(@PathVariable String id){

        try{

            return ResponseEntity.ok(ticketService.getTicketById(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}

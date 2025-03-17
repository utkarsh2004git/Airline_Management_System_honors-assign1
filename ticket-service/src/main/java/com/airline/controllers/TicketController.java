package com.airline.controllers;
import com.airline.dto.TicketInfoDTO;
import com.airline.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable String ticketId) {

        boolean deleted = ticketService.deleteTicket(ticketId);

        if (deleted) {
            return ResponseEntity.ok("Ticket deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete ticket.");
        }
    }



}

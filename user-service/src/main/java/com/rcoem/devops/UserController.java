package com.rcoem.devops;
import com.rcoem.devops.dto.UserInfo;
import com.rcoem.devops.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    List<UserInfo> getAllUsers(){
          return userService.getAllUsers();
    }


    @PostMapping
    UserInfo createUser(@RequestBody UserInfo userInfo){
        return userService.createUser(userInfo);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserInfo>  getUserById(@PathVariable String id){

        try{
            return ResponseEntity.ok(userService.getUser(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/tickets/{ticketId}")
    ResponseEntity<String> updateUser(@PathVariable String id, @PathVariable String ticketId){
//        System.out.println(ticketId);
        try{
            userService.updateUserTicketList(id, ticketId);
            return ResponseEntity.ok("User ticket Added");
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}/tickets/{ticketId}")
    ResponseEntity<String> deleteUserTicket(@PathVariable String id, @PathVariable String ticketId){
//        System.out.println(ticketId);
        try{
            userService.deleteUserTicket(id, ticketId);
            return ResponseEntity.ok("User ticket Added");
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}

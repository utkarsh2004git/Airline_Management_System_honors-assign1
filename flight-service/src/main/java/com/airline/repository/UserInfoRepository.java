package com.airline.repository;

import com.airline.dto.UserInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserInfoRepository {

    Map<String, UserInfo> userInfoTable;


    @PostConstruct
    public void init() {
        userInfoTable = new HashMap<>();
    }


    public List<UserInfo> getAllUsers(){
        return userInfoTable.values()
                .stream().collect(Collectors.toList());
    }

    public UserInfo createUser(UserInfo userInfo) {
        String userId = UUID.randomUUID().toString();
        UserInfo updatedUser = userInfo.toBuilder().id(userId).build();
        this.userInfoTable.put(userId, updatedUser);
        return updatedUser;
    }


    public UserInfo getUserById(String userId){
        return userInfoTable.get(userId);
    }

//    public void updateUserTicketList(String userId, String ticketId) {
//        UserInfo userInfo = userInfoTable.get(userId);
//        if (userInfo != null) {
//            // Ensure ticket list is initialized
//            if (userInfo.getBookedTicketIds() == null) {
//                userInfo.setBookedTicketIds(new ArrayList<>());
//            }
//
//            // Add ticketId if it's not already in the list
//            if (!userInfo.getBookedTicketIds().contains(ticketId)) {
//                userInfo.getBookedTicketIds().add(ticketId);
//            }
//
//            // Put the updated user info back into the map
//            userInfoTable.put(userId, userInfo);
//        }
//    }


}

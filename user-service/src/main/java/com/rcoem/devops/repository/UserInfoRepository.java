package com.rcoem.devops.repository;

import com.rcoem.devops.dto.UserInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserInfoRepository {

    HashMap<String, UserInfo> userInfoTable;

    HashMap<String,UserInfo> demoUsers(HashMap<String,UserInfo> userInfoTable) {

        UserInfo userInfo = UserInfo.builder()
                .id("abc123")
                .name("Utkarsh")
                .email("Utkarsh@gmail.com")
                .address("Nagpur")
                .phone("123456789")
                .bookedTicketIds(new ArrayList<>())
                .build();
        userInfoTable.put("abc123", userInfo);

        return userInfoTable;
    }


    @PostConstruct
    public void init() {
        userInfoTable = new HashMap<>();
        userInfoTable = demoUsers(userInfoTable);
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

    public void updateUserTicketList(String userId, String ticketId) {
        UserInfo userInfo = userInfoTable.get(userId);
        if (userInfo != null) {
            // Ensure ticket list is initialized
            if (userInfo.getBookedTicketIds() == null) {
                userInfo.setBookedTicketIds(new ArrayList<>());
            }

            // Add ticketId if it's not already in the list
            if (!userInfo.getBookedTicketIds().contains(ticketId)) {
                userInfo.getBookedTicketIds().add(ticketId);
            }

            // Put the updated user info back into the map
            userInfoTable.put(userId, userInfo);
        }
    }


}

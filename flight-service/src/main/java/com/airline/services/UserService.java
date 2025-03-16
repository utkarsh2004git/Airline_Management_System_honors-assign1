package com.airline.services;

import com.airline.dto.UserInfo;
import com.airline.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserInfoRepository userInfoRepository;


    public List<UserInfo> getAllUsers(){
        return userInfoRepository.getAllUsers();
    }

    public UserInfo createUser(UserInfo userInfo){
        return userInfoRepository.createUser(userInfo);

    }

    public UserInfo getUser(String id) throws Exception{
        UserInfo userInfo= userInfoRepository.getUserById(id);;
        if(userInfo == null){
            throw new Exception("User not found");
        }
        return userInfo;
    }

//    public void updateUserTicketList(String userId, String ticketId) {
//
//        userInfoRepository.updateUserTicketList(userId,ticketId);
//    }
}

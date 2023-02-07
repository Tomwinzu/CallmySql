package com.userManagement;


import com.userManagement.service.User;
import com.userManagement.service.UserManagement;
import com.userManagement.service.bean.UserPost;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserManagement userManagement;

    @PostMapping(path = "/api/user-management/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserPost userPost) {
        userManagement.addUser(userPost);
    }




    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {

        return userRepository.findAll();


    }


}
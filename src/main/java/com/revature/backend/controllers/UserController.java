package com.revature.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.backend.models.JsonResponse;
import com.revature.backend.models.User;
import com.revature.backend.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public JsonResponse registerUser(@RequestBody User credentials){

        if(!userService.registerUser(credentials)){
            return new JsonResponse(false, "username already exists in system", null);
        }

        return new JsonResponse(true, "user registered in system", null);

    }

}
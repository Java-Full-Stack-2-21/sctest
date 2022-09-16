package com.revature.backend.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.backend.models.JsonResponse;
import com.revature.backend.models.User;
import com.revature.backend.services.UserService;

/**
 * The class will hold all of the endpoints related to sessions
 */
@RestController
@RequestMapping(value = "session")
public class SessionController {

    private UserService userService;

    @Autowired
    public SessionController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<JsonResponse> getSession(HttpSession session){
        Integer userId = (int) session.getAttribute("userId");

        if(userId == 0){
            return ResponseEntity.ok(new JsonResponse(false, "no session found", null));
        }

        User user = this.userService.getUserGivenUserId(userId);

        return ResponseEntity.ok(new JsonResponse(true, "session found", user));
    }

    @PostMapping
    public JsonResponse login(HttpSession session, @RequestBody User credentials){
        
        if(!this.userService.validateCredentials(credentials)){
            return new JsonResponse(false, "invalid credentials", null);
        }

        User userFromDb = this.userService.getUserGivenUsername(credentials.getUsername());

        session.setAttribute("userId", userFromDb.getId());

        return new JsonResponse(true, "login successful", userFromDb);
    }

    @DeleteMapping
    public JsonResponse logout(HttpSession session){
        session.invalidate();

        return new JsonResponse(true, "user logged out and session invalidated", null);
    }
}

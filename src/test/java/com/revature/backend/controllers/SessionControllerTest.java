package com.revature.backend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.revature.backend.models.JsonResponse;
import com.revature.backend.models.User;
import com.revature.backend.services.UserService;

public class SessionControllerTest {

    HttpSession session = Mockito.mock(HttpSession.class);
    UserService userService = Mockito.mock(UserService.class);

    SessionController sessionController = new SessionController(userService);


    @Test
    void testGetSessionUserFound() {
        //arrange
        User user = new User(1, "kev123", "pass123", "Employee");
        Mockito.when(session.getAttribute("userId")).thenReturn(user.getId());
        Mockito.when(userService.getUserGivenUserId(user.getId())).thenReturn(user);
        ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok(new JsonResponse(true, "session found", user));     
    
        //act
        ResponseEntity<JsonResponse> actualResult = sessionController.getSession(session);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    /* @Test
    void testGetSessionUserNotFound() {
        //arrange
        User user = new User(1, "kev123", "pass123", "Employee");
        Mockito.when(session.getAttribute("userId")).thenReturn(0);
        JsonResponse expectedResult = new JsonResponse(false, "no session found", null);    
    
        //act
        JsonResponse actualResult = sessionController.getSession(session);

        //assert
        assertEquals(expectedResult, actualResult);
        Mockito.verify(userService, Mockito.times(0)).getUserGivenUserId(user.getId());

    } */
}

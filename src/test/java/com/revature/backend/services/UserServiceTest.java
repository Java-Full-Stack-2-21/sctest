package com.revature.backend.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.backend.models.User;
import com.revature.backend.repositories.UserRepo;

public class UserServiceTest {

    UserRepo userRepo = Mockito.mock(UserRepo.class);

    UserService userService = new UserService(userRepo);

    @Test
    void testGetUserGivenUserIdUserFound() {

        //arrange (the setup info we need prior to calling the method)
        User user = new User(1, "kev123", "pass123", "Employee");
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        //act (actually calling the method)
        User actualResult = this.userService.getUserGivenUserId(user.getId());

        //assert (compare expected and actual result)
        Assertions.assertEquals(user, actualResult);

    }

    @Test
    void testGetUserGivenUserIdUserNotFound() {
        //arrange (the setup info we need prior to calling the method)
        User user = new User(1, "kev123", "pass123", "Employee");
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(null));

        //act (actually calling the method)
        User actualResult = this.userService.getUserGivenUserId(user.getId());

        //assert (compare expected and actual result)
        Assertions.assertNull(actualResult);
    }


    @Test
    void testValidateCredentialsUsernameNotFound() {
        //arrange
        User credentials = new User(null, "kev123", "pass123", null);
        Mockito.when(userRepo.findByUsername(credentials.getUsername())).thenReturn(null);

        //act
        Boolean actualResult = userService.validateCredentials(credentials);

        //assert
        assertFalse(actualResult);
    }

    @Test
    void testValidateCredentialsInvalidPassword() {
        //arrange
        User credentials = new User(null, "kev123", "pass123", null);
        User userFromDb = new User(1, "kev123", "pass1234", "EMPLOYEE");
        Mockito.when(userRepo.findByUsername(credentials.getUsername())).thenReturn(userFromDb);

        //act
        Boolean actualResult = userService.validateCredentials(credentials);

        //assert
        assertFalse(actualResult);
    }

    @Test
    void testValidateCredentialsValidCredentials() {
        //arrange
        User credentials = new User(null, "kev123", "pass123", null);
        User userFromDb = new User(1, "kev123", "pass123", "EMPLOYEE");
        Mockito.when(userRepo.findByUsername(credentials.getUsername())).thenReturn(userFromDb);

        //act
        Boolean actualResult = userService.validateCredentials(credentials);

        //assert
        assertTrue(actualResult);
    }

    @Test
    void testRegisterUserUsernameAlreadyExists() {
        //arrange
        User credentials = new User(null, "kev123", "pass123", "EMPLOYEE");
        User userFromDb = new User(1, "kev123", "pass123", "EMPLOYEE");
        Mockito.when(userRepo.findByUsername(credentials.getUsername())).thenReturn(userFromDb);

        //act
        Boolean actualResult = userService.registerUser(credentials);

        //assert
        assertFalse(actualResult);
        Mockito.verify(userRepo, Mockito.times(0)).save(credentials);

    }

    @Test
    void testRegisterUserSuccessful(){
        //arrange
        User credentials = new User(null, "kev123", "pass123", "EMPLOYEE");
        Mockito.when(userRepo.findByUsername(credentials.getUsername())).thenReturn(null);

        //act
        Boolean actualResult = userService.registerUser(credentials);

        //assert
        assertTrue(actualResult);
        Mockito.verify(userRepo, Mockito.times(1)).save(credentials);

    }

    
}

package com.revature.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.backend.models.User;
import com.revature.backend.repositories.UserRepo;

/* 
 * What is a unit test?
 *      - testing the smallest pieces of your code in isolation
 *      - the smallest units of code we can test in java is methods
 * 
 * What is mockito?
 *      - mockito is a dependency that allows us to mock objects
 *      - for unit test, we mock external calls inside the method we are testing and hard code a result back
 *          - we do this because we are trying to test a particular method in isolation. If its calling another method that you wrote, youre technically testing 2 methods now.
 * 
 * What is code coverage?
 *      - measures how code is executed during testing
 *      - formula: (methods tested / total methods in app) * 100
 * 
 * What is test coverage?
 *      - measures how much of the feature set is covered with tests
 *      - formula: (features tested / total features) * 100
 * 
 * 
 * What is an integration test?
 *  - Testing how external services interact with your application
 *      - Database
 * 
 */
@SpringBootTest
public class UserServiceIT {
    
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;


    @Test
    void getUserGivenUsername(){
        //arange
        User expectedResult = new User(1, "kev123", "pass123", "EMPLOYEE");

        //act
        User actualResult = userService.getUserGivenUsername(expectedResult.getUsername());

        //assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
    void getUserGivenUserIdUserFound(){
        //arange
        User expectedResult = new User(1, "kev123", "pass123", "EMPLOYEE");

        //act
        User actualResult = userService.getUserGivenUserId(expectedResult.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }

}

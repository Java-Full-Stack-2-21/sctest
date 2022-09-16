package com.revature.backend.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.backend.models.Reimb;
import com.revature.backend.models.User;
import com.revature.backend.repositories.ReimbRepo;
import com.revature.backend.repositories.UserRepo;

public class ReimServiceTest {

    ReimbRepo reimbRepo = Mockito.mock(ReimbRepo.class);
    UserRepo userRepo = Mockito.mock(UserRepo.class);

    ReimService reimService = new ReimService(reimbRepo, userRepo);


    @Test
    void testCreateReimSuccessful() {
        //arrange
        User userCreatingReim = new User(1, "kev123", "pass123", "Employee");
        Reimb reimb = new Reimb();
        reimb.setDescription("business flight");
        reimb.setCreatedBy(userCreatingReim);
        Mockito.when(userRepo.findById(userCreatingReim.getId())).thenReturn(Optional.of(userCreatingReim));


        //act
        Boolean actualResult = this.reimService.createReim(reimb);

        //assert
        assertTrue(actualResult);
        Mockito.verify(reimbRepo, Mockito.times(1)).save(reimb);
    }


    @Test
    void testCreateReimUserNotFound() {
        //arrange
        User userCreatingReim = new User(1, "kev123", "pass123", "Employee");
        Reimb reimb = new Reimb();
        reimb.setDescription("business flight");
        reimb.setCreatedBy(userCreatingReim);
        Mockito.when(userRepo.findById(userCreatingReim.getId())).thenReturn(Optional.empty());

        //act
        Boolean actualResult = this.reimService.createReim(reimb);

        //assert
        assertFalse(actualResult);
        Mockito.verify(reimbRepo, Mockito.times(0)).save(reimb);
    }

}

package com.revature.backend.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.backend.models.User;
import com.revature.backend.repositories.UserRepo;

@Service
@Transactional
public class UserService {
    
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public User getUserGivenUserId(Integer userId){
        return this.userRepo.findById(userId).orElse(null);
    }

    public boolean validateCredentials(User credentials){
        User userFromDb = this.userRepo.findByUsername(credentials.getUsername());

        //if database comes back with null giving the username, return false
        if(userFromDb == null)
            return false;

        //if passwords are not equal return false
        if(!userFromDb.getPassword().equals(credentials.getPassword())){
            return false;
        }

        return true;

    }

    public User getUserGivenUsername(String username){
        return this.userRepo.findByUsername(username);
    }

    public Boolean registerUser(User credentials){

        //check if username already exists in system
        User userFromDb = userRepo.findByUsername(credentials.getUsername());

        if(userFromDb != null)
            return false;

        this.userRepo.save(credentials);


        return true;
    }
}

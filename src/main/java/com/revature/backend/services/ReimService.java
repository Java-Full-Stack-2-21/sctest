package com.revature.backend.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.backend.models.Reimb;
import com.revature.backend.models.User;
import com.revature.backend.repositories.ReimbRepo;
import com.revature.backend.repositories.UserRepo;

@Service
@Transactional
public class ReimService {
    private UserRepo userRepo;
    private ReimbRepo reimbRepo;

    @Autowired
    public ReimService(ReimbRepo reimbRepo, UserRepo userRepo){
        this.reimbRepo = reimbRepo;
        this.userRepo = userRepo;
    }


    public Boolean createReim(Reimb reimb){
        //validate user exists
        User userFromDb = this.userRepo.findById(reimb.getCreatedBy().getId()).orElse(null);

        if(userFromDb == null){
            return false;
        }


        reimb.setCreatedBy(userFromDb);

        this.reimbRepo.save(reimb);
        return true;
    }

    public Boolean updateStatus(Integer reimbId, String newStatus, Integer idFromSession){

        User userFromDb = this.userRepo.findById(idFromSession).orElse(null);

        //cant update if not manager
        if(!userFromDb.getRole().equals("MANAGER"))
            return false;

        //check if valid status
        if(!(newStatus.equals("APPROVED") || newStatus.equals("DENIED")))
            return false;

        Reimb reimbFromDb = this.reimbRepo.findById(reimbId).orElse(null);

        //make sure reim exists
        if(reimbFromDb == null)
            return false;

        reimbFromDb.setStatus(newStatus);
        reimbRepo.save(reimbFromDb);

        return true;
    }

    public List<Reimb> getAllReimbs(Integer userId){

        if(userId == 0)
            return null;

        User userFromDb = this.userRepo.findById(userId).orElse(null);


        if(!userFromDb.getRole().equals("MANAGER"))
            return null;

        return this.reimbRepo.findAll();
    }

    public List<Reimb> getAllReimbsForUser(Integer userId, Integer idFromSession){
        if(userId != idFromSession)
            return null;

        return this.reimbRepo.findAllByCreatedById(userId);
    }

}

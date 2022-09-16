package com.revature.backend.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.backend.models.JsonResponse;
import com.revature.backend.models.Reimb;
import com.revature.backend.services.ReimService;

@RestController
public class ReimController { 

    private ReimService reimService;
    
    @Autowired
    public ReimController(ReimService reimService){
        this.reimService = reimService;
    }

    @PostMapping("reimbursement")
    public JsonResponse createReim(@RequestBody Reimb reimb){

        if(!this.reimService.createReim(reimb)){
            return new JsonResponse(false, "unable to create reimbursement. User doesnt exist in system", false);
        }

        return new JsonResponse(true, "reimb created for user", null);
    }

    @PatchMapping("reimbursement/{reimbId}/{status}")
    public JsonResponse updateReim(HttpSession session, @PathVariable Integer reimbId, @PathVariable String status){

        Integer userId = (int) session.getAttribute("userId");

        if(!this.reimService.updateStatus(reimbId, status, userId))
            return new JsonResponse(false, "permission denied", null);
        

        return new JsonResponse(true, "reimb updated to status " + status, null);
    }

    @GetMapping("reimbursement")
    public JsonResponse getAllReimbs(HttpSession session){

        Integer userId = (int) session.getAttribute("userId");

        List<Reimb> reimbs = this.reimService.getAllReimbs(userId);

        if(reimbs == null)
            return new JsonResponse(false, "permission denied", null);

        return new JsonResponse(true, "display all reimbursements", reimbs);
    }

    @GetMapping("user/{userId}/reimbursement")
    public JsonResponse getAllReimbsForUser(HttpSession session, @PathVariable Integer userId){

        Integer idFromSession = (int) session.getAttribute("userId");

        List<Reimb> reimbs = this.reimService.getAllReimbsForUser(userId, idFromSession);

        if(reimbs == null)
            return new JsonResponse(false, "permission denied", null);

        return new JsonResponse(true, "display all reimbursements for user with id " + userId, reimbs);
    }
}

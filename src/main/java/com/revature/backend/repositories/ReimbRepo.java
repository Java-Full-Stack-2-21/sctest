package com.revature.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.backend.models.Reimb;

@Repository
public interface ReimbRepo extends JpaRepository<Reimb, Integer>{
    List<Reimb> findAllByCreatedById(Integer userId);
}

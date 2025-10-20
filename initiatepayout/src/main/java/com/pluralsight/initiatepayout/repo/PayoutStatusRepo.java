package com.pluralsight.initiatepayout.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pluralsight.initiatepayout.model.PayoutStatus;

public interface PayoutStatusRepo extends JpaRepository<PayoutStatus, String>{
    
}

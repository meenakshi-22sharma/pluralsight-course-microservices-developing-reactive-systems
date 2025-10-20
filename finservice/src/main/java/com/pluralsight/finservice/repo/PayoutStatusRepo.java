package com.pluralsight.finservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pluralsight.finservice.model.PayoutStatus;

public interface PayoutStatusRepo extends JpaRepository<PayoutStatus, String>{
    Optional<PayoutStatus> findByTazapayId(String tazapayId);
}

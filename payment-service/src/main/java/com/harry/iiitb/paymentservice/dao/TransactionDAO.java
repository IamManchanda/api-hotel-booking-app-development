package com.harry.iiitb.paymentservice.dao;

import com.harry.iiitb.paymentservice.entities.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDAO extends JpaRepository<TransactionDetailsEntity, Integer> {
}

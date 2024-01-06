package com.harry.iiitb.paymentservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harry.iiitb.paymentservice.dao.TransactionDAO;
import com.harry.iiitb.paymentservice.dto.TransactionDTO;
import com.harry.iiitb.paymentservice.dto.TransactionRequestDTO;
import com.harry.iiitb.paymentservice.entities.TransactionDetailsEntity;
import com.harry.iiitb.paymentservice.utils.MapperUtil;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    public TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        TransactionDetailsEntity createdTransactionDetails = TransactionDetailsEntity
                .fromTransactionRequestDTO(transactionRequestDTO);

        TransactionDetailsEntity savedTransactionDetails = transactionDAO
                .save(createdTransactionDetails);

        return MapperUtil.mapTransactionDetailsToDTO(savedTransactionDetails, true);
    }

    @Override
    public TransactionDTO findTransactionById(int transactionId) {
        TransactionDetailsEntity foundTransactionDetails = transactionDAO.findById(transactionId).orElse(null);

        if (foundTransactionDetails == null) {
            throw new IllegalArgumentException("Invalid Transaction Id");
        }

        return MapperUtil.mapTransactionDetailsToDTO(foundTransactionDetails, false);
    }
}

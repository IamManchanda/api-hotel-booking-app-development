package com.harry.iiitb.paymentservice.services;

import com.harry.iiitb.paymentservice.dto.TransactionRequestDTO;
import com.harry.iiitb.paymentservice.dto.TransactionDTO;

public interface TransactionService {
    public TransactionDTO createTransaction(TransactionRequestDTO transactionRequestDTO);

    public TransactionDTO findTransactionById(int transactionId);
}

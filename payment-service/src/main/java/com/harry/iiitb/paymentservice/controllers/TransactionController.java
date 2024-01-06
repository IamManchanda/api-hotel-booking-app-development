package com.harry.iiitb.paymentservice.controllers;

import com.harry.iiitb.paymentservice.dto.ErrorDTO;
import com.harry.iiitb.paymentservice.dto.TransactionDTO;
import com.harry.iiitb.paymentservice.dto.TransactionRequestDTO;
import com.harry.iiitb.paymentservice.services.TransactionService;
import com.harry.iiitb.paymentservice.utils.ErrorUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTransaction(@Validated @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            return handleSuccessfulTransaction(transactionRequestDTO);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findTransactionById(@PathVariable int transactionId) {
        try {
            return handleSuccessfulTransactionFetch(transactionId);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    private ResponseEntity<TransactionDTO> handleSuccessfulTransaction(TransactionRequestDTO transactionRequestDTO) {
        String paymentMode = transactionRequestDTO.getPaymentMode();

        List<String> validPaymentModes = new ArrayList<>();
        validPaymentModes.add("UPI");
        validPaymentModes.add("CARD");

        if (!validPaymentModes.contains(paymentMode)) {
            throw new IllegalArgumentException("Invalid mode of payment");
        }

        TransactionDTO createdTransaction = transactionService.createTransaction(transactionRequestDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    private ResponseEntity<TransactionDTO> handleSuccessfulTransactionFetch(int transactionId) {
        TransactionDTO foundTransaction = transactionService.findTransactionById(transactionId);
        return new ResponseEntity<>(foundTransaction, HttpStatus.OK);
    }

    private ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorDTO errorResponse = new ErrorDTO();
        errorResponse.setTimestamp(ErrorUtil.getCurrentTimestamp());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDTO> handleInternalServerError(Exception e) {
        ErrorDTO errorResponse = new ErrorDTO();
        errorResponse.setTimestamp(ErrorUtil.getCurrentTimestamp());
        errorResponse.setMessage("Internal server error: " + e.getMessage());
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

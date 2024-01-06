package com.harry.iiitb.paymentservice.utils;

import com.harry.iiitb.paymentservice.dto.TransactionDTO;
import com.harry.iiitb.paymentservice.entities.TransactionDetailsEntity;

public class MapperUtil {
    public static TransactionDTO mapTransactionDetailsToDTO(TransactionDetailsEntity transactionDetailsEntity,
            boolean hasTransactionIdAlias) {
        TransactionDTO transactionDTO = new TransactionDTO();

        int transactionId = transactionDetailsEntity.getTransactionId();
        transactionDTO.setId(transactionId);

        if (hasTransactionIdAlias) {
            transactionDTO.setTransactionId(transactionId);
        } else {
            transactionDTO.setTransactionId(null);
        }

        String paymentMode = transactionDetailsEntity.getPaymentMode();
        transactionDTO.setPaymentMode(paymentMode);

        int bookingId = transactionDetailsEntity.getBookingId();
        transactionDTO.setBookingId(bookingId);

        String upiId = transactionDetailsEntity.getUpiId();
        if (upiId == null) {
            upiId = "";
        }
        transactionDTO.setUpiId(upiId);

        String cardNumber = transactionDetailsEntity.getCardNumber();
        if (cardNumber == null) {
            cardNumber = "";
        }
        transactionDTO.setCardNumber(cardNumber);

        return transactionDTO;
    }
}

package com.harry.iiitb.bookingservice.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.harry.iiitb.bookingservice.dto.TransactionRequestDTO;

@Entity
@Table(name = "transaction")
public class TransactionDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    @Column(nullable = false)
    @NotNull(message = "Payment mode cannot be null")
    @Pattern(regexp = "upi|card", message = "Payment mode must be either 'upi' or 'card'")
    private String paymentMode;

    @Column(nullable = false)
    @NotNull(message = "Booking Id cannot be null")
    private int bookingId;

    @Column(nullable = true)
    private String upiId;

    @Column(nullable = true)
    private String cardNumber;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static TransactionDetailsEntity fromTransactionRequestDTO(TransactionRequestDTO transactionRequestDTO) {
        TransactionDetailsEntity transactionDetailsEntity = new TransactionDetailsEntity();

        String paymentModeParam = transactionRequestDTO.getPaymentMode();
        transactionDetailsEntity.setPaymentMode(paymentModeParam);

        int bookingIdParam = transactionRequestDTO.getBookingId();
        transactionDetailsEntity.setBookingId(bookingIdParam);

        if (paymentModeParam.equals("upi")) {
            String upiIdParam = transactionRequestDTO.getUpiId();
            transactionDetailsEntity.setUpiId(upiIdParam);
            transactionDetailsEntity.setCardNumber(null);
        } else {
            String cardNumberParam = transactionRequestDTO.getCardNumber();
            transactionDetailsEntity.setCardNumber(cardNumberParam);
            transactionDetailsEntity.setUpiId(null);
        }

        return transactionDetailsEntity;
    }

    @Override
    public String toString() {
        return "TransactionDetailsEntity{" +
                "transactionId=" + transactionId +
                ", paymentMode='" + paymentMode + '\'' +
                ", bookingId=" + bookingId +
                ", upiId='" + upiId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}

package com.harry.iiitb.paymentservice.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.harry.iiitb.paymentservice.dto.TransactionRequestDTO;

@Entity
@Table(name = "transaction")
public class TransactionDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    @Column(nullable = false)
    @NotNull(message = "Payment mode cannot be null")
    @Pattern(regexp = "^(UPI|CARD)$", message = "Payment mode must be UPI or CARD")
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

        String paymentModePayload = transactionRequestDTO.getPaymentMode();
        transactionDetailsEntity.setPaymentMode(paymentModePayload);

        int bookingIdPayload = transactionRequestDTO.getBookingId();
        transactionDetailsEntity.setBookingId(bookingIdPayload);

        if (paymentModePayload.equals("UPI")) {
            String upiIdPayload = transactionRequestDTO.getUpiId();
            transactionDetailsEntity.setUpiId(upiIdPayload);
            transactionDetailsEntity.setCardNumber(null);
        } else {
            String cardNumberPayload = transactionRequestDTO.getCardNumber();
            transactionDetailsEntity.setCardNumber(cardNumberPayload);
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

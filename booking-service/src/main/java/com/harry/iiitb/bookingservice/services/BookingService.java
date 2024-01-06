package com.harry.iiitb.bookingservice.services;

import com.harry.iiitb.bookingservice.dto.BookingRequestDTO;
import com.harry.iiitb.bookingservice.dto.TransactionRequestDTO;

import java.io.IOException;

import com.harry.iiitb.bookingservice.dto.BookingDTO;

public interface BookingService {
    public BookingDTO createBooking(BookingRequestDTO bookingRequestDTO);

    public BookingDTO createBookingTransaction(TransactionRequestDTO transactionRequestDTO) throws IOException;
}

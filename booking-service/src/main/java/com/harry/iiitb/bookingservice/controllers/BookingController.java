package com.harry.iiitb.bookingservice.controllers;

import com.harry.iiitb.bookingservice.dto.BookingRequestDTO;
import com.harry.iiitb.bookingservice.dto.BookingDTO;
import com.harry.iiitb.bookingservice.dto.ErrorDTO;
import com.harry.iiitb.bookingservice.dto.TransactionRequestDTO;
import com.harry.iiitb.bookingservice.services.BookingService;
import com.harry.iiitb.bookingservice.utils.ErrorUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBooking(@Validated @RequestBody BookingRequestDTO bookingRequestDTO) {
        try {
            return handleSuccessfulBooking(bookingRequestDTO);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    @PostMapping(value = "/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBookingTransaction(@PathVariable int bookingId,
            @Validated @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            return handleSuccessfulBookingTransaction(bookingId, transactionRequestDTO);
        } catch (IllegalArgumentException e) {
            return handleIllegalArgumentException(e);
        } catch (IOException e) {
            // same internal server error handler for IOException
            return handleInternalServerError(e);
        } catch (Exception e) {
            return handleInternalServerError(e);
        }
    }

    private ResponseEntity<BookingDTO> handleSuccessfulBooking(BookingRequestDTO bookingRequestDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String fromDate = bookingRequestDTO.getFromDate();
        LocalDate fromDateLocalDate = LocalDate.parse(fromDate, formatter);

        String toDate = bookingRequestDTO.getToDate();
        LocalDate toDateLocalDate = LocalDate.parse(toDate, formatter);

        if (fromDateLocalDate.isAfter(toDateLocalDate)) {
            throw new IllegalArgumentException(
                    "Invalid date range: fromDate must be before or equal to toDate");
        }

        BookingDTO createdBooking = bookingService.createBooking(bookingRequestDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    private ResponseEntity<BookingDTO> handleSuccessfulBookingTransaction(int bookingIdPathVar,
            TransactionRequestDTO transactionRequestDTO) throws IOException {
        int bookingId = transactionRequestDTO.getBookingId();

        if (bookingIdPathVar != bookingId) {
            throw new IllegalArgumentException(
                    "Invalid bookingId: bookingId in path variable must be equal to bookingId in request body");
        }

        String paymentMode = transactionRequestDTO.getPaymentMode();

        List<String> validPaymentModes = new ArrayList<>();
        validPaymentModes.add("UPI");
        validPaymentModes.add("CARD");

        if (!validPaymentModes.contains(paymentMode)) {
            throw new IllegalArgumentException("Invalid mode of payment");
        }

        BookingDTO createdBookingTransaction = bookingService.createBookingTransaction(transactionRequestDTO);
        return new ResponseEntity<>(createdBookingTransaction, HttpStatus.CREATED);
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

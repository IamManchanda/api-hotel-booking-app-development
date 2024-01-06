package com.harry.iiitb.bookingservice.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.harry.iiitb.bookingservice.dto.BookingDTO;
import com.harry.iiitb.bookingservice.entities.BookingInfoEntity;

public class MapperUtil {
    public static BookingDTO mapBookingInfoToDTO(BookingInfoEntity bookingInfoEntity) {
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        BookingDTO bookingDTO = new BookingDTO();

        int bookingId = bookingInfoEntity.getBookingId();
        bookingDTO.setId(bookingId);

        String fromDate = bookingInfoEntity
                .getFromDate()
                .atOffset(offset)
                .format(formatter);
        bookingDTO.setFromDate(fromDate);

        String toDate = bookingInfoEntity
                .getToDate()
                .atOffset(offset)
                .format(formatter);
        bookingDTO.setToDate(toDate);

        String aadharNumber = bookingInfoEntity.getAadharNumber();
        bookingDTO.setAadharNumber(aadharNumber);

        String roomNumbers = bookingInfoEntity.getRoomNumbers();
        bookingDTO.setRoomNumbers(roomNumbers);

        int roomPrice = bookingInfoEntity.getRoomPrice();
        bookingDTO.setRoomPrice(roomPrice);

        int transactionId = bookingInfoEntity.getTransactionId();
        bookingDTO.setTransactionId(transactionId);

        String bookedOne = bookingInfoEntity
                .getBookedOn()
                .atOffset(offset)
                .format(formatter);
        bookingDTO.setBookedOn(bookedOne);

        return bookingDTO;
    }
}

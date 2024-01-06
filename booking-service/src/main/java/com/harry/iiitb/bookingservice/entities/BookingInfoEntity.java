package com.harry.iiitb.bookingservice.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.harry.iiitb.bookingservice.dto.BookingRequestDTO;
import com.harry.iiitb.bookingservice.utils.RoomUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "booking")
public class BookingInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;

    @Column(nullable = true)
    private Instant fromDate;

    @Column(nullable = true)
    private Instant toDate;

    @Column(nullable = true)
    private String aadharNumber;

    @Column(nullable = false)
    @NotNull(message = "Number of rooms cannot be null")
    @Min(value = 1, message = "Number of rooms should be greater than or equal to 1")
    @Max(value = 10, message = "Number of rooms should be less than or equal to 10")
    private int numOfRooms;

    @Column(nullable = true)
    private String roomNumbers;

    @Column(nullable = false)
    @NotNull(message = "Room price cannot be null")
    private int roomPrice;

    @Column(nullable = false, columnDefinition = "int default 0")
    @NotNull(message = "Transaction id cannot be null")
    private int transactionId;

    @Column(nullable = true)
    private Instant bookedOn;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public String getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(String roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Instant getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(Instant bookedOn) {
        this.bookedOn = bookedOn;
    }

    public static BookingInfoEntity fromBookingRequestDTO(BookingRequestDTO bookingRequestDTO) {
        int transactionIdPayload = 0; // Set initial value to 0
        return fromBookingRequestDTO(bookingRequestDTO, transactionIdPayload);
    }

    public static BookingInfoEntity fromBookingRequestDTO(
            BookingRequestDTO bookingRequestDTO,
            int transactionIdPayload) {
        ZoneOffset offset = ZoneOffset.UTC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BookingInfoEntity bookingInfoEntity = new BookingInfoEntity();

        Instant fromDatePayload = LocalDate.parse(
                bookingRequestDTO.getFromDate(), formatter)
                .atStartOfDay()
                .toInstant(offset);
        bookingInfoEntity.setFromDate(fromDatePayload);

        Instant toDatePayload = LocalDate.parse(
                bookingRequestDTO.getToDate(), formatter)
                .atStartOfDay()
                .toInstant(offset);
        bookingInfoEntity.setToDate(toDatePayload);

        String aadharNumberPayload = bookingRequestDTO.getAadharNumber();
        bookingInfoEntity.setAadharNumber(aadharNumberPayload);

        int numOfRoomsPayload = bookingRequestDTO.getNumOfRooms();
        bookingInfoEntity.setNumOfRooms(numOfRoomsPayload);

        int roomPricePayload = RoomUtil.calculateRoomPrice(fromDatePayload, toDatePayload, numOfRoomsPayload);
        bookingInfoEntity.setRoomPrice(roomPricePayload);

        String roomNumbersPayload = RoomUtil.generateRoomNumbers(numOfRoomsPayload);
        bookingInfoEntity.setRoomNumbers(roomNumbersPayload);

        bookingInfoEntity.setTransactionId(transactionIdPayload);

        Instant bookedOnPayload = Instant.now()
                .atOffset(offset)
                .toInstant();
        bookingInfoEntity.setBookedOn(bookedOnPayload);

        return bookingInfoEntity;
    }

    @Override
    public String toString() {
        return "BookingInfoEntity{" +
                "bookingId=" + bookingId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", numOfRooms=" + numOfRooms +
                ", roomNumbers='" + roomNumbers + '\'' +
                ", roomPrice=" + roomPrice +
                ", transactionId=" + transactionId +
                ", bookedOn=" + bookedOn +
                '}';
    }
}

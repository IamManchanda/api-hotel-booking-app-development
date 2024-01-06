package com.harry.iiitb.bookingservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookingRequestDTO {
    @NotBlank
    private String fromDate;

    @NotBlank
    private String toDate;

    @NotBlank
    private String aadharNumber;

    @NotNull
    private int numOfRooms;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
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

    @Override
    public String toString() {
        return "BookingRequestDTO{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", numOfRooms=" + numOfRooms +
                '}';
    }
}

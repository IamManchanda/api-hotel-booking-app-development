package com.harry.iiitb.bookingservice.utils;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoomUtil {

    private static final int BASE_ROOM_PRICE_PER_DAY = 1000;
    private static final int MAX_ROOM_NUMBER = 9999;
    private static final int MIN_ROOM_NUMBER = 1000;

    public static int calculateRoomPrice(Instant fromDate, Instant toDate, int numOfRooms) {
        long numOfDaysDiff = Math.max(ChronoUnit.DAYS.between(fromDate, toDate), 0);
        int numOfDays = numOfDaysDiff == 0
                ? 1
                : (int) numOfDaysDiff + 1;
        return BASE_ROOM_PRICE_PER_DAY * numOfRooms * numOfDays;
    }

    public static String generateRoomNumbers(int count) {
        SecureRandom secureRandom = new SecureRandom();
        IntStream intStream = secureRandom.ints(count, MIN_ROOM_NUMBER, MAX_ROOM_NUMBER);
        List<Integer> roomNumberList = intStream.boxed().collect(Collectors.toList());
        return roomNumberList.stream().sorted().map(Object::toString).collect(Collectors.joining(","));
    }
}

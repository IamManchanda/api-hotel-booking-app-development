package com.harry.iiitb.paymentservice.utils;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ErrorUtil {
    public static String getCurrentTimestamp() {
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        return Instant.now().atOffset(offset)
                .format(formatter);
    }
}

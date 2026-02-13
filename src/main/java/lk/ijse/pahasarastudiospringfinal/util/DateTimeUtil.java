package lk.ijse.pahasarastudiospringfinal.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
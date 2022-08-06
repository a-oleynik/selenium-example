package com.oleynik.gradle.selenium.example.framework.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static com.oleynik.gradle.selenium.example.framework.config.ConfigurationManager.configuration;

public class DateTimeUtils {

    public static final String STANDARD_DATE_TIME = "dd/MM/yyyy - HH:mm:ss.SSS";
    public static final String DATETIME_FORMAT_FOR_EXCEL_FILE = "ddMMyy_hhmmss";
    public static final String DATETIME_FORMAT_FOR_ALLURE_CONSOLE_LOG = "ddMMyy hh:mm:ss.SSS";
    public static final ZoneId ENVIRONMENT_ZONE_ID = ZoneId.of(configuration().environmentTimeZone());

    public static String getDateTimeForAllureConsoleLog() {
        return getCurrentDateTime(getDateTimeFormatter(DATETIME_FORMAT_FOR_ALLURE_CONSOLE_LOG));
    }

    public static String getCurrentDateTime(DateTimeFormatter formatter) {
        return getCurrentDateTime(ENVIRONMENT_ZONE_ID, formatter);
    }

    public static String getCurrentDateTime(ZoneId zoneId, DateTimeFormatter formatter) {
        return LocalDateTime.now(zoneId).format(formatter);
    }

    public static DateTimeFormatter getDateTimeFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime, String format) {
        return zonedDateTime.format(getDateTimeFormatter(format));
    }

    public static String getDateTimeForExcelReport() {
        return getCurrentDateTime(getDateTimeFormatter(DATETIME_FORMAT_FOR_EXCEL_FILE));
    }

    public static Duration getDurationBetweenZonedDateTimes(ZonedDateTime dateTimeStart, ZonedDateTime dateTimeEnd) {
        return Duration.between(dateTimeStart, dateTimeEnd);
    }

    public static String getPrintableDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() - TimeUnit.DAYS.toHours(days);
        long minutes = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());
        long seconds = duration.getSeconds() - TimeUnit.MINUTES.toSeconds(duration.toMinutes());
        long millis = duration.toMillis() - TimeUnit.SECONDS.toMillis(duration.getSeconds());
        String printableDuration = "";
        if (days > 0) {
            printableDuration += String.format("%sd ", days);
        }
        if (hours > 0) {
            printableDuration += String.format("%sh ", hours);
        }
        if (minutes > 0) {
            printableDuration += String.format("%sm ", minutes);
        }
        if (seconds > 0) {
            printableDuration += String.format("%ss ", seconds);
        }
        return printableDuration + String.format("%sms", millis);
    }

    public static String getPrintableDuration(ZonedDateTime dateTimeStart, ZonedDateTime dateTimeEnd) {
        return getPrintableDuration(getDurationBetweenZonedDateTimes(dateTimeStart, dateTimeEnd));
    }
}

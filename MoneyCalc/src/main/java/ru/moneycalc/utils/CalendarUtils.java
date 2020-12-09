package ru.moneycalc.utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public final class CalendarUtils {
    private CalendarUtils() {
    }

    public static int daysBetween(@NotNull Calendar d1, @NotNull Calendar d2) {
        return Math.abs((int) ((d2.getTime().getTime() - d1.getTime().getTime()) / (1000 * 60 * 60 * 24)));
    }

    public static int getTotalDaysInYear(@NotNull Calendar date) {
        return date.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int getTotalDaysInMonth(@NotNull Calendar date) {
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDayNumInMonth(@NotNull Calendar date) {
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayNumInYear(@NotNull Calendar date) {
        return date.get(Calendar.DAY_OF_YEAR);
    }

    @NotNull
    public static Calendar localDateToCalendar(@NotNull LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        return calendar;
    }

    @NotNull
    public static String getCalendarString(@NotNull Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(calendar.getTime());
    }
}

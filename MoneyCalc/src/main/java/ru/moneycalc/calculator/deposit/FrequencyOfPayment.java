package ru.moneycalc.calculator.deposit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Map;


public enum FrequencyOfPayment {
    ANNUALLY_ON_DEPOSIT_DAY,
    DAILY,
    END_OF_DEPOSIT,
    EVERY_THREE_MONTHS_ON_DEPOSIT_DAY,
    MONTHLY_ON_DEPOSIT_DAY,
    ON_FIRST_DAY_OF_MONTH,
    ON_LAST_DAY_OF_MONTH,
    ON_LAST_DAY_OF_YEAR,
    SEMIANNUALLY_ON_DEPOSIT_DAY,
    WEEKLY;

    private static final Map<String, FrequencyOfPayment> tableOfTitles = Map.of(
            "В конце срока", FrequencyOfPayment.END_OF_DEPOSIT,
            "Ежедневно", FrequencyOfPayment.DAILY,
            "Еженедельно", FrequencyOfPayment.WEEKLY,
            "Раз в месяц в день вклада", FrequencyOfPayment.MONTHLY_ON_DEPOSIT_DAY,
            "В первый день месяца", FrequencyOfPayment.ON_FIRST_DAY_OF_MONTH,
            "В последний день месяца", FrequencyOfPayment.ON_LAST_DAY_OF_MONTH,
            "Раз в 3 месяца в день вклада", FrequencyOfPayment.EVERY_THREE_MONTHS_ON_DEPOSIT_DAY,
            "Раз в полгода в день вклада", FrequencyOfPayment.SEMIANNUALLY_ON_DEPOSIT_DAY,
            "Раз в год в день вклада", FrequencyOfPayment.ANNUALLY_ON_DEPOSIT_DAY,
            "В последний день года", FrequencyOfPayment.ON_LAST_DAY_OF_YEAR
    );

    @NotNull
    private Calendar getNewDate(@NotNull Calendar date) {
        switch (this) {
            case DAILY:
                date.add(Calendar.DATE, 1);
                break;
            case WEEKLY:
                date.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case ON_LAST_DAY_OF_YEAR:
                date.add(Calendar.DATE, 1);
                date.set(Calendar.DAY_OF_YEAR, CalendarUtils.getTotalDaysInYear(date));
                break;
            case ON_LAST_DAY_OF_MONTH:
                date.add(Calendar.DATE, 1);
                date.set(Calendar.DAY_OF_MONTH, CalendarUtils.getTotalDaysInMonth(date));
                break;
            case ON_FIRST_DAY_OF_MONTH:
                date.add(Calendar.MONTH, 1);
                date.set(Calendar.DAY_OF_MONTH, 1);
                break;
        }
        return date;
    }

    @NotNull
    public Calendar getNewDate(@NotNull Calendar date,
                               @NotNull Calendar depositDate,
                               int months) {
        Calendar newDate = (Calendar) date.clone();
        int monthsToAdd;
        switch (this) {
            case MONTHLY_ON_DEPOSIT_DAY:
                monthsToAdd = 1;
                break;
            case ANNUALLY_ON_DEPOSIT_DAY:
                monthsToAdd = 12;
                break;
            case SEMIANNUALLY_ON_DEPOSIT_DAY:
                monthsToAdd = 6;
                break;
            case EVERY_THREE_MONTHS_ON_DEPOSIT_DAY:
                monthsToAdd = 3;
                break;
            case END_OF_DEPOSIT:
                monthsToAdd = months;
                break;
            default:
                return getNewDate(newDate);
        }
        newDate.add(Calendar.MONTH, monthsToAdd);
        int dayOfMonth = Math.min(CalendarUtils.getDayNumInMonth(depositDate), CalendarUtils.getTotalDaysInMonth(newDate));
        newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return newDate;
    }

    public int getNumberOfPayouts(int months, @NotNull Calendar depositDate) {
        Calendar date = (Calendar) depositDate.clone();
        int daysInDepositPeriod = CalendarUtils.getTotalDaysInMonth(date) - CalendarUtils.getDayNumInMonth(date);
        int lastDaysInYearPayoutsNumber = CalendarUtils.getTotalDaysInYear(date) == CalendarUtils.getDayNumInYear(date) ? 0 : 1;
        int annuallyPayouts = (months % 12 == 0) ? 0 : 1;
        int semiannuallyPayouts = (months % 6 == 0) ? 0 : 1;
        int nextSemiannuallyMonth = (date.get(Calendar.MONTH) + 6) % 12;
        for (int month = 1; month <= months; ++month) {
            if (date.get(Calendar.MONTH) == Calendar.DECEMBER) {
                lastDaysInYearPayoutsNumber++;
            }
            date.add(Calendar.MONTH, 1);
            if (date.get(Calendar.MONTH) == nextSemiannuallyMonth) {
                semiannuallyPayouts++;
            }
            if (date.get(Calendar.MONTH) == depositDate.get(Calendar.MONTH)) {
                annuallyPayouts++;
                semiannuallyPayouts++;
            }
            if (month == months) {
                daysInDepositPeriod += CalendarUtils.getDayNumInMonth(date);
            } else {
                daysInDepositPeriod += CalendarUtils.getTotalDaysInMonth(date);
            }
        }
        switch (this) {
            case DAILY:
                return daysInDepositPeriod;
            case WEEKLY:
                return (int) Math.ceil((double) daysInDepositPeriod / 7);
            case MONTHLY_ON_DEPOSIT_DAY:
                return months;
            case END_OF_DEPOSIT:
                return 1;
            case ANNUALLY_ON_DEPOSIT_DAY:
                return annuallyPayouts;
            case SEMIANNUALLY_ON_DEPOSIT_DAY:
                return semiannuallyPayouts;
            case EVERY_THREE_MONTHS_ON_DEPOSIT_DAY:
                return (int) Math.ceil((double) daysInDepositPeriod / 365 * 4);
            case ON_FIRST_DAY_OF_MONTH:
                return date.get(Calendar.DAY_OF_MONTH) == 1 ? months : months + 1;
            case ON_LAST_DAY_OF_MONTH:
                return date.get(Calendar.DAY_OF_MONTH) == CalendarUtils.getTotalDaysInMonth(date) ? months : months + 1;
            case ON_LAST_DAY_OF_YEAR:
                return lastDaysInYearPayoutsNumber;
        }
        return 0;
    }

    @NotNull
    public static Map<String, FrequencyOfPayment> getTableOfTitles() {
        return tableOfTitles;
    }
}

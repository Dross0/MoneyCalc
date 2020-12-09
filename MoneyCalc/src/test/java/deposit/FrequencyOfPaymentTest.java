package deposit;

import org.junit.Test;
import ru.moneycalc.calculator.deposit.FrequencyOfPayment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;


public class FrequencyOfPaymentTest {

    @Test
    public void getDailyNewDate() {
        int day = 1;
        Calendar date = new GregorianCalendar(2020, Calendar.JUNE, day);
        FrequencyOfPayment frequency = FrequencyOfPayment.DAILY;
        for (; day < 30; ++day) {
            date = frequency.getNewDate(date, date, 1);
            assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JUNE, day + 1)));
        }
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JULY, 1)));
    }

    @Test
    public void getWeeklyNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.WEEKLY;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JUNE, 29)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JULY, 6)));
    }


    @Test
    public void getLastDayOfYearNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ON_LAST_DAY_OF_YEAR;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.DECEMBER, 31)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.DECEMBER, 31)));
    }

    @Test
    public void getLastDayOfMonthNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ON_LAST_DAY_OF_MONTH;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JUNE, 30)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JULY, 31)));
    }

    @Test
    public void getFirstDayOfMonthNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.DECEMBER, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ON_FIRST_DAY_OF_MONTH;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.JANUARY, 1)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.FEBRUARY, 1)));
    }

    @Test
    public void getMonthlyOnDepositDayNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.DECEMBER, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.MONTHLY_ON_DEPOSIT_DAY;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.JANUARY, 22)));
        GregorianCalendar depositDate = new GregorianCalendar(2020, Calendar.MAY, 31);
        date = frequency.getNewDate(depositDate, depositDate, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JUNE, 30)));
        date = frequency.getNewDate(date, depositDate, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2020, Calendar.JULY, 31)));
    }

    @Test
    public void getAnnuallyOnDepositDayNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.DECEMBER, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ANNUALLY_ON_DEPOSIT_DAY;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.DECEMBER, 22)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2022, Calendar.DECEMBER, 22)));
    }

    @Test
    public void getSemiannuallyOnDepositDayNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.DECEMBER, 31);
        FrequencyOfPayment frequency = FrequencyOfPayment.SEMIANNUALLY_ON_DEPOSIT_DAY;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.JUNE, 30)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.DECEMBER, 30)));
    }

    @Test
    public void getEveryThreeMonthsOnDepositDayNewDate() {
        Calendar date = new GregorianCalendar(2020, Calendar.DECEMBER, 31);
        FrequencyOfPayment frequency = FrequencyOfPayment.EVERY_THREE_MONTHS_ON_DEPOSIT_DAY;
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.MARCH, 31)));
        date = frequency.getNewDate(date, date, 1);
        assertEquals(0, date.compareTo(new GregorianCalendar(2021, Calendar.JUNE, 30)));
    }

    @Test
    public void getEndOfDepositDayNewDate() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.DECEMBER, 31);
        FrequencyOfPayment frequency = FrequencyOfPayment.END_OF_DEPOSIT;
        Calendar date = depositDate;
        date = frequency.getNewDate(date, depositDate, 23);
        assertEquals(0, date.compareTo(new GregorianCalendar(2022, Calendar.NOVEMBER, 30)));
        date = frequency.getNewDate(date, depositDate, 4);
        assertEquals(0, date.compareTo(new GregorianCalendar(2023, Calendar.MARCH, 31)));
    }

    @Test
    public void getDailyNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.DECEMBER, 25);
        FrequencyOfPayment frequency = FrequencyOfPayment.DAILY;
        assertEquals(31, frequency.getNumberOfPayouts(1, depositDate));
        assertEquals(62, frequency.getNumberOfPayouts(2, depositDate));
    }

    @Test
    public void getWeeklyNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.JUNE, 25);
        FrequencyOfPayment frequency = FrequencyOfPayment.WEEKLY;
        assertEquals(5, frequency.getNumberOfPayouts(1, depositDate));
        assertEquals(14, frequency.getNumberOfPayouts(3, depositDate));
    }


    @Test
    public void getLastDayOfYearNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ON_LAST_DAY_OF_YEAR;
        assertEquals(1, frequency.getNumberOfPayouts(5, depositDate));
        assertEquals(2, frequency.getNumberOfPayouts(8, depositDate));
        depositDate = new GregorianCalendar(2020, Calendar.DECEMBER, 31);
        assertEquals(3, frequency.getNumberOfPayouts(36, depositDate));
        assertEquals(3, frequency.getNumberOfPayouts(25, depositDate));
    }

    @Test
    public void getLastDayOfMonthNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ON_LAST_DAY_OF_MONTH;
        assertEquals(2, frequency.getNumberOfPayouts(1, depositDate));
        depositDate = new GregorianCalendar(2020, Calendar.JUNE, 30);
        assertEquals(3, frequency.getNumberOfPayouts(3, depositDate));
    }

    @Test
    public void getFirstDayOfMonthNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ON_FIRST_DAY_OF_MONTH;
        assertEquals(4, frequency.getNumberOfPayouts(3, depositDate));
        depositDate = new GregorianCalendar(2020, Calendar.JUNE, 1);
        assertEquals(3, frequency.getNumberOfPayouts(3, depositDate));
    }

    @Test
    public void getMonthlyOnDepositDayNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.JUNE, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.MONTHLY_ON_DEPOSIT_DAY;
        assertEquals(5, frequency.getNumberOfPayouts(5, depositDate));
        assertEquals(45, frequency.getNumberOfPayouts(45, depositDate));

    }

    @Test
    public void getAnnuallyOnDepositDayNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.DECEMBER, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.ANNUALLY_ON_DEPOSIT_DAY;
        assertEquals(1, frequency.getNumberOfPayouts(5, depositDate));
        assertEquals(4, frequency.getNumberOfPayouts(45, depositDate));
    }

    @Test
    public void getSemiannuallyOnDepositDayNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.DECEMBER, 22);
        FrequencyOfPayment frequency = FrequencyOfPayment.SEMIANNUALLY_ON_DEPOSIT_DAY;
        assertEquals(1, frequency.getNumberOfPayouts(5, depositDate));
        assertEquals(2, frequency.getNumberOfPayouts(7, depositDate));
        assertEquals(8, frequency.getNumberOfPayouts(45, depositDate));

    }

    @Test
    public void getEveryThreeMonthsOnDepositDayNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.JUNE, 31);
        FrequencyOfPayment frequency = FrequencyOfPayment.EVERY_THREE_MONTHS_ON_DEPOSIT_DAY;
        assertEquals(2, frequency.getNumberOfPayouts(5, depositDate));
        assertEquals(4, frequency.getNumberOfPayouts(12, depositDate));
    }

    @Test
    public void getEndOfDepositDayNumberOfPayouts() {
        Calendar depositDate = new GregorianCalendar(2020, Calendar.DECEMBER, 31);
        FrequencyOfPayment frequency = FrequencyOfPayment.END_OF_DEPOSIT;
        assertEquals(1, frequency.getNumberOfPayouts(34, depositDate));
    }
}
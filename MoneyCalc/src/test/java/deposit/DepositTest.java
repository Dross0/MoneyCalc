package deposit;


import org.junit.Test;
import ru.moneycalc.calculator.deposit.Deposit;
import ru.moneycalc.calculator.deposit.DepositInfo;
import ru.moneycalc.calculator.deposit.DepositPayment;
import ru.moneycalc.calculator.deposit.FrequencyOfPayment;
import ru.moneycalc.calculator.exceptions.DepositInfoException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;


public class DepositTest {

    private void checkPayment(DepositPayment payment, double expectedSum, double expectedPercent, double delta) {
        assertEquals(expectedSum, payment.getSumOfDeposit(), delta);
        assertEquals(expectedPercent, payment.getInterestCharges(), delta);
    }

    private void checkFinalOfDeposit(Deposit deposit, double expectedFinalSum, double expectedInterestCharges, double delta) {
        assertEquals(expectedFinalSum, deposit.getTotalSum(), delta);
        assertEquals(expectedInterestCharges, deposit.getAllInterestCharges(), delta);
    }

    private void checkPayment(DepositPayment payment, double expectedSum, double expectedPercent) {
        checkPayment(payment, expectedSum, expectedPercent, 0.1);
    }

    private void checkFinalOfDeposit(Deposit deposit, double expectedFinalSum, double expectedInterestCharges) {
        checkFinalOfDeposit(deposit, expectedFinalSum, expectedInterestCharges, 0.1);
    }


    @Test
    public void getPaymentByIndexMonthlyOnDepositDayWithoutCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                218000,
                5.2,
                false,
                FrequencyOfPayment.MONTHLY_ON_DEPOSIT_DAY,
                4,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(4, payoutsNumber);
        for (int payoutIndex = 0; payoutIndex < payoutsNumber; ++payoutIndex) {
            DepositPayment payment = deposit.getPaymentByIndex(payoutIndex);
            if (payoutIndex == 2) {
                checkPayment(payment, 218000, 929.18);
            } else {
                checkPayment(payment, 218000, 960.15);
            }
        }
        checkFinalOfDeposit(deposit, 218000, 3809.63);
    }

    @Test
    public void getPaymentByIndexMonthlyOnDepositDayWithCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                218000,
                5.2,
                true,
                FrequencyOfPayment.MONTHLY_ON_DEPOSIT_DAY,
                4,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(4, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 218960.15, 960.15);
        payment = deposit.getPaymentByIndex(2);
        checkPayment(payment, 220861.91, 937.38);
        checkFinalOfDeposit(deposit, 221834.67, 3834.67);
    }

    @Test
    public void getPaymentByIndexFirstDayOfMonthWithoutCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                289050,
                4.8,
                false,
                FrequencyOfPayment.ON_FIRST_DAY_OF_MONTH,
                4,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(5, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 289050, 1099.34);
        payment = deposit.getPaymentByIndex(2);
        checkPayment(payment, 289050, 1137.25);
        payment = deposit.getPaymentByIndex(4);
        checkPayment(payment, 289050, 75.82);
        checkFinalOfDeposit(deposit, 289050, 4662.71);
    }

    @Test
    public void getPaymentByIndexFirstDayOfMonthWithCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                289050,
                4.8,
                true,
                FrequencyOfPayment.ON_FIRST_DAY_OF_MONTH,
                4,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(5, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 290149.34, 1099.34);
        payment = deposit.getPaymentByIndex(2);
        checkPayment(payment, 292475.17, 1146.21);
        payment = deposit.getPaymentByIndex(4);
        checkPayment(payment, 293741.28, 77.03);
        checkFinalOfDeposit(deposit, 293741.28, 4691.28);
    }

    @Test
    public void getPaymentByIndexDailyWithoutCapitalization() throws DepositInfoException {
        Deposit deposit = new Deposit(new DepositInfo(
                300000,
                6.1,
                false,
                FrequencyOfPayment.DAILY,
                2,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(62, payoutsNumber);
        for (int payoutIndex = 0; payoutIndex < payoutsNumber; ++payoutIndex) {
            DepositPayment payment = deposit.getPaymentByIndex(payoutIndex);
            checkPayment(payment, 300000, 50);
        }
        checkFinalOfDeposit(deposit, 300000, 3100.00);
    }

    @Test
    public void getPaymentByIndexDailyWithCapitalization() throws DepositInfoException {
        Deposit deposit = new Deposit(new DepositInfo(
                300000,
                6.1,
                true,
                FrequencyOfPayment.DAILY,
                2,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(62, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 300050, 50);
        payment = deposit.getPaymentByIndex(10);
        checkPayment(payment, 300550.47, 50.08);
        checkFinalOfDeposit(deposit, 303115.83, 3115.83);
    }

    @Test
    public void getPaymentByIndexWeeklyWithoutCapitalization() throws DepositInfoException {
        Deposit deposit = new Deposit(new DepositInfo(
                320000,
                3.9,
                false,
                FrequencyOfPayment.WEEKLY,
                2,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(9, payoutsNumber);
        for (int payoutIndex = 0; payoutIndex < payoutsNumber - 1; ++payoutIndex) {
            DepositPayment payment = deposit.getPaymentByIndex(payoutIndex);
            checkPayment(payment, 320000, 238.69);
        }
        checkPayment(deposit.getPaymentByIndex(payoutsNumber - 1), 320000, 204.59);
        checkFinalOfDeposit(deposit, 320000, 2114.11);
    }

    @Test
    public void getPaymentByIndexWeeklyWithCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                320000,
                3.9,
                true,
                FrequencyOfPayment.WEEKLY,
                2,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(9, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(3);
        checkPayment(payment, 320955.82, 239.22);
        payment = deposit.getPaymentByIndex(8);
        checkPayment(payment, 322120.31, 205.81);
        checkFinalOfDeposit(deposit, 322120.31, 2120.31);
    }

    @Test
    public void getPaymentByIndexLastDayOfYearWithoutCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                425000,
                4.5,
                false,
                FrequencyOfPayment.ON_LAST_DAY_OF_YEAR,
                53,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(5, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 425000, 9457.99);
        payment = deposit.getPaymentByIndex(3);
        checkPayment(payment, 425000, 19125);
        checkFinalOfDeposit(deposit, 425000, 84494.88, 10);
    }

    @Test
    public void getPaymentByIndexLastDayOfYearWithCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                425000,
                4.5,
                true,
                FrequencyOfPayment.ON_LAST_DAY_OF_YEAR,
                53,
                new GregorianCalendar(2020, Calendar.JULY, 3))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(5, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 434457.99, 9457.99);
    }

    @Test
    public void getPaymentByIndexAnnuallyWithoutCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                287500,
                6.3,
                false,
                FrequencyOfPayment.ANNUALLY_ON_DEPOSIT_DAY,
                16,
                new GregorianCalendar(2020, Calendar.JULY, 6))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(2, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 287500, 18063.01);
        payment = deposit.getPaymentByIndex(1);
        checkPayment(payment, 287500, 6103.66);
        checkFinalOfDeposit(deposit, 287500, 24166.67);
    }

    @Test
    public void getPaymentByIndexAnnuallyWithCapitalization() {
        Deposit deposit = new Deposit(new DepositInfo(
                287500,
                6.3,
                true,
                FrequencyOfPayment.ANNUALLY_ON_DEPOSIT_DAY,
                16,
                new GregorianCalendar(2020, Calendar.JULY, 6))
        );
        int payoutsNumber = deposit.getNumberOfPayments();
        assertEquals(2, payoutsNumber);
        DepositPayment payment = deposit.getPaymentByIndex(0);
        checkPayment(payment, 305563.01, 18063.01);
        payment = deposit.getPaymentByIndex(1);
        checkPayment(payment, 312050.15, 6487.14);
    }
}
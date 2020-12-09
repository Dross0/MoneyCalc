import org.junit.Test;
import ru.moneycalc.calculator.credit.CreditInfo;
import ru.moneycalc.calculator.credit.CreditType;
import ru.moneycalc.calculator.credit.DifferentiatedCredit;
import ru.moneycalc.calculator.exceptions.WrongCreditType;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class DifferentiatedCreditTest {
    @Test
    public void create() {
        assertThrows(
                WrongCreditType.class,
                () -> new DifferentiatedCredit(
                        new CreditInfo(
                                293,
                                3,
                                3,
                                new GregorianCalendar(),
                                CreditType.ANNUITY
                        ))
        );
        assertDoesNotThrow(() -> new DifferentiatedCredit(
                new CreditInfo(
                        293,
                        3,
                        3,
                        new GregorianCalendar(),
                        CreditType.DIFFERENTIATED
                ))
        );
    }

    @Test
    public void getMonthlyPayment() {
        double[] MONTHLY_PAYOUTS = {33123.82, 32861.86, 32599.91, 32337.95};
        int months = 4;
        DifferentiatedCredit credit = new DifferentiatedCredit(new CreditInfo(
                128304,
                9.8,
                months,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
        for (int i = 0; i < months; ++i) {
            assertEquals(MONTHLY_PAYOUTS[i], credit.getMonthlyPayment(i + 1), 0.1);
        }
    }

    @Test
    public void getTotalPayout() {
        DifferentiatedCredit credit = new DifferentiatedCredit(new CreditInfo(
                124283,
                9.8,
                27,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
        assertEquals(138492.69, credit.getTotalPayout(), 0.1);
    }

    @Test
    public void getMonthlyCreditBalance() {
        DifferentiatedCredit credit = new DifferentiatedCredit(new CreditInfo(
                250000,
                18.5,
                25,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
        assertEquals(30000, credit.getMonthlyCreditBalance(22), 0.1);
        assertEquals(180000, credit.getMonthlyCreditBalance(7), 0.1);
    }

    @Test
    public void getMonthlyPrincipalPayout() {
        DifferentiatedCredit credit = new DifferentiatedCredit(new CreditInfo(
                250000,
                18.5,
                25,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
        for (int month = 1; month <= credit.getInfo().getMonths(); ++month) {
            assertEquals(10000, credit.getMonthlyPrincipalPayout(month), 0.1);
        }
    }

    @Test
    public void getMonthlyPercentPayout() {
        DifferentiatedCredit credit = new DifferentiatedCredit(new CreditInfo(
                250000,
                18.5,
                25,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
        assertEquals(3545.83, credit.getMonthlyPercentPayout(3), 0.1);
        assertEquals(1387.50, credit.getMonthlyPercentPayout(17), 0.1);
    }

    @Test
    public void getTotalPercentPayout() {
        DifferentiatedCredit credit = new DifferentiatedCredit(new CreditInfo(
                250000,
                18.5,
                25,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
        assertEquals(50104.17, credit.getTotalPercentPayout(), 0.1);
    }
}
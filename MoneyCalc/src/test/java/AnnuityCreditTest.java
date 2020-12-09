
import org.junit.Test;
import ru.moneycalc.calculator.credit.AnnuityCredit;
import ru.moneycalc.calculator.credit.CreditInfo;
import ru.moneycalc.calculator.credit.CreditType;
import ru.moneycalc.calculator.exceptions.WrongCreditType;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class AnnuityCreditTest {
    @Test
    public void create() {
        assertThrows(WrongCreditType.class, () -> new AnnuityCredit(new CreditInfo(293, 3, 3, new GregorianCalendar(), CreditType.DIFFERENTIATED)));
        assertDoesNotThrow(() -> new AnnuityCredit(new CreditInfo(293, 3, 3, new GregorianCalendar(), CreditType.ANNUITY)));
    }

    @Test
    public void getMonthlyPayment() {
        int months = 11;
        AnnuityCredit credit = new AnnuityCredit(new CreditInfo(12478, 9.8, months, new GregorianCalendar(), CreditType.ANNUITY));
        for (int i = 1; i <= months; ++i) {
            assertEquals(1190.7, credit.getMonthlyPayment(i), 0.001);
        }
    }

    @Test
    public void getTotalPayout() {
        int months = 23;
        AnnuityCredit credit = new AnnuityCredit(new CreditInfo(98763, 13.5, months, new GregorianCalendar(), CreditType.ANNUITY));
        assertEquals(112642.32, credit.getTotalPayout(), 0.1);
    }

    @Test
    public void getMonthlyCreditBalance() {
        AnnuityCredit credit = new AnnuityCredit(new CreditInfo(250000, 18.5, 25, new GregorianCalendar(), CreditType.ANNUITY));
        assertEquals(57926.17, credit.getMonthlyCreditBalance(20), 0.1);
        assertEquals(101180.28, credit.getMonthlyCreditBalance(16), 0.1);

    }

    @Test
    public void getMonthlyPrincipalPayout() {
        AnnuityCredit credit = new AnnuityCredit(new CreditInfo(250000, 18.5, 25, new GregorianCalendar(), CreditType.ANNUITY));
        assertEquals(8660.87, credit.getMonthlyPrincipalPayout(4), 0.1);
        assertEquals(11582.52, credit.getMonthlyPrincipalPayout(23), 0.1);

    }

    @Test
    public void getMonthlyPercentPayout() {
        AnnuityCredit credit = new AnnuityCredit(new CreditInfo(250000, 18.5, 25, new GregorianCalendar(), CreditType.ANNUITY));
        assertEquals(3726.63, credit.getMonthlyPercentPayout(2), 0.1);
        assertEquals(893.03, credit.getMonthlyPercentPayout(21), 0.1);
    }

    @Test
    public void getTotalPercentPayout() {
        AnnuityCredit credit = new AnnuityCredit(new CreditInfo(250000, 18.5, 25, new GregorianCalendar(), CreditType.ANNUITY));
        assertEquals(53162.89, credit.getTotalPercentPayout(), 0.1);
    }
}
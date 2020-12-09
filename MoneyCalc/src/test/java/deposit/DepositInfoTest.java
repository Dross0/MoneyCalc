package deposit;


import org.junit.Test;
import ru.moneycalc.calculator.deposit.DepositInfo;
import ru.moneycalc.calculator.deposit.FrequencyOfPayment;
import ru.moneycalc.calculator.exceptions.DepositInfoException;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class DepositInfoTest {
    @Test
    public void create() {
        assertThrows(
                DepositInfoException.class,
                () -> new DepositInfo(
                        -1,
                        3,
                        true,
                        FrequencyOfPayment.DAILY,
                        3,
                        new GregorianCalendar())
        );
        assertThrows(
                DepositInfoException.class,
                () -> new DepositInfo(
                        5,
                        0,
                        true,
                        FrequencyOfPayment.DAILY,
                        3,
                        new GregorianCalendar())
        );
        assertThrows(
                DepositInfoException.class,
                () -> new DepositInfo(
                        13,
                        3,
                        true,
                        FrequencyOfPayment.DAILY,
                        0,
                        new GregorianCalendar())
        );
        assertThrows(
                DepositInfoException.class,
                () -> new DepositInfo(
                        13,
                        3,
                        true,
                        FrequencyOfPayment.DAILY,
                        13,
                        null
                )
        );
        assertDoesNotThrow(() -> new DepositInfo(
                14,
                3,
                true,
                FrequencyOfPayment.DAILY,
                3,
                new GregorianCalendar())
        );
    }

    @Test
    public void getPercent() throws DepositInfoException {
        DepositInfo info = new DepositInfo(
                14,
                20,
                true,
                FrequencyOfPayment.DAILY,
                3,
                new GregorianCalendar()
        );
        assertEquals(0.2, info.getPercent(), 0.00001);
        info = new DepositInfo(14, 98, true, FrequencyOfPayment.DAILY, 3, new GregorianCalendar());
        assertEquals(0.98, info.getPercent(), 0.00001);
    }
}
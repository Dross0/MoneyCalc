
import org.junit.Test;
import ru.moneycalc.calculator.credit.CreditInfo;
import ru.moneycalc.calculator.credit.CreditType;
import ru.moneycalc.calculator.exceptions.CreditInfoException;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class CreditInfoTest {

    @Test
    public void create() {
        assertThrows(
                CreditInfoException.class,
                () -> new CreditInfo(
                        -1,
                        3,
                        4,
                        new GregorianCalendar(),
                        CreditType.ANNUITY)
        );
        assertThrows(
                CreditInfoException.class,
                () -> new CreditInfo(
                        4,
                        0,
                        4,
                        new GregorianCalendar(),
                        CreditType.ANNUITY)
        );
        assertThrows(
                CreditInfoException.class,
                () -> new CreditInfo(
                        3,
                        3,
                        -2,
                        new GregorianCalendar(),
                        CreditType.ANNUITY)
        );
        assertDoesNotThrow(() -> new CreditInfo(
                3,
                5,
                1,
                new GregorianCalendar(),
                CreditType.DIFFERENTIATED)
        );
    }

    @Test
    public void getPercent() {
        CreditInfo info = new CreditInfo(
                4,
                20,
                4,
                new GregorianCalendar(),
                CreditType.ANNUITY
        );
        assertEquals(0.2, info.getPercent(), 0.00001);
        info = new CreditInfo(
                5,
                98,
                4,
                new GregorianCalendar(),
                CreditType.ANNUITY
        );
        assertEquals(0.98, info.getPercent(), 0.00001);
    }

}
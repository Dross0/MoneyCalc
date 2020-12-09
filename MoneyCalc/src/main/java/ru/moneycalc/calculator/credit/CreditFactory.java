package ru.moneycalc.calculator.credit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.exceptions.WrongCreditType;

import java.util.Calendar;


public final class CreditFactory {
    private CreditFactory() {
    }

    @NotNull
    public static Credit create(@NotNull final CreditInfo creditInfo) {
        switch (creditInfo.getType()) {
            case ANNUITY:
                return new AnnuityCredit(creditInfo);
            case DIFFERENTIATED:
                return new DifferentiatedCredit(creditInfo);
            default:
                throw new WrongCreditType("Unknown credit type");
        }
    }

    @NotNull
    public static Credit create(double sumOfCredit,
                                double percent,
                                int months,
                                @NotNull Calendar date,
                                @NotNull CreditType creditType) {
        CreditInfo creditInfo = new CreditInfo(sumOfCredit, percent, months, date, creditType);
        return create(creditInfo);
    }
}
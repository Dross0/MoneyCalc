package ru.moneycalc.calculator.credit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Optional;

public class CreditPayment {
    private final double paymentSum;
    private final double principalPart;
    private final double percentPart;
    private final double creditBalance;

    @Nullable
    private final Calendar date;


    public CreditPayment(double paymentSum,
                         double principalPart,
                         double percentPart,
                         double creditBalance) {
        this(paymentSum, principalPart, percentPart, creditBalance, null);
    }

    public CreditPayment(double paymentSum,
                         double principalPart,
                         double percentPart,
                         double creditBalance,
                         Calendar date) {
        this.paymentSum = paymentSum;
        this.principalPart = principalPart;
        this.percentPart = percentPart;
        this.creditBalance = creditBalance;
        this.date = date;
    }

    @NotNull
    public Optional<Calendar> getDate() {
        return Optional.ofNullable(date);
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    public double getPaymentSum() {
        return paymentSum;
    }

    public double getPercentPart() {
        return percentPart;
    }

    public double getPrincipalPart() {
        return principalPart;
    }
}

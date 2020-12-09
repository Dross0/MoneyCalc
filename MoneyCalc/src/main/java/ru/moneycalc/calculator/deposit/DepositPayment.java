package ru.moneycalc.calculator.deposit;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Optional;


public class DepositPayment {
    private final double sumIncludingTax;
    private final double sumOfDeposit;
    private final double interestCharges;
    @Nullable
    private final Calendar date;
    private final double amountOfTaxes;


    public DepositPayment(double sumOfDeposit,
                          double interestCharges,
                          double amountOfTaxes) {
        this(sumOfDeposit, interestCharges, null, amountOfTaxes);
    }

    public DepositPayment(double sumOfDeposit,
                          double interestCharges,
                          @Nullable Calendar date,
                          double amountOfTaxes) {
        this.sumOfDeposit = sumOfDeposit;
        this.interestCharges = interestCharges;
        this.date = date;
        this.amountOfTaxes = amountOfTaxes;
        this.sumIncludingTax = interestCharges - amountOfTaxes;
    }

    public double getSumOfDeposit() {
        return sumOfDeposit;
    }

    public double getAmountOfTaxes() {
        return amountOfTaxes;
    }

    public double getInterestCharges() {
        return interestCharges;
    }

    @NotNull
    public Optional<Calendar> getDate() {
        return Optional.ofNullable(date);
    }

    public double getSumIncludingTax() {
        return sumIncludingTax;
    }
}

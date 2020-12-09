package ru.moneycalc.calculator.deposit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.exceptions.DepositInfoException;

import java.util.Calendar;


public class DepositInfo {
    private double sumOfDeposit;
    private double percent;
    private final boolean isCapitalization;
    @NotNull
    private final FrequencyOfPayment frequency;
    @NotNull
    private Calendar depositDate;
    private int months;

    public DepositInfo(double sumOfDeposit,
                       double percent,
                       boolean isCapitalization,
                       @NotNull FrequencyOfPayment frequency,
                       int months,
                       @NotNull Calendar depositDate) {
        setSumOfDeposit(sumOfDeposit);
        setPercent(percent);
        setMonths(months);
        setDepositDate(depositDate);
        this.isCapitalization = isCapitalization;
        this.frequency = frequency;
    }

    public DepositInfo(double sumOfDeposit,
                       double percent,
                       boolean isCapitalization,
                       @NotNull FrequencyOfPayment frequency,
                       int period,
                       @NotNull Calendar depositDate,
                       boolean isYearPeriod) {
        this(sumOfDeposit, percent, isCapitalization, frequency, isYearPeriod ? period * 12 : period, depositDate);
    }

    private void setDepositDate(Calendar depositDate) {
        if (depositDate == null) {
            throw new DepositInfoException("Deposit date cant be null");
        }
        this.depositDate = depositDate;
    }

    private void setMonths(int months) {
        if (months <= 0) {
            throw new DepositInfoException("Months must be positive");
        }
        this.months = months;
    }

    private void setPercent(double percent) {
        if (percent <= 0) {
            throw new DepositInfoException("Percent must be positive");
        }
        this.percent = percent / 100;
    }

    private void setSumOfDeposit(double sumOfDeposit) {
        if (sumOfDeposit <= 0) {
            throw new DepositInfoException("Sum of deposit must be positive");
        }
        this.sumOfDeposit = sumOfDeposit;
    }

    public double getSumOfDeposit() {
        return sumOfDeposit;
    }

    public int getMonths() {
        return months;
    }

    public double getPercent() {
        return percent;
    }

    @NotNull
    public FrequencyOfPayment getFrequency() {
        return frequency;
    }

    @NotNull
    public Calendar getDepositDate() {
        return depositDate;
    }

    public boolean isCapitalization() {
        return this.isCapitalization;
    }
}

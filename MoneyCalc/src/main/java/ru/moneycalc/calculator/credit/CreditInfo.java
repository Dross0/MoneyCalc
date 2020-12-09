package ru.moneycalc.calculator.credit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.exceptions.CreditInfoException;

import java.util.Calendar;
import java.util.Objects;


public class CreditInfo {
    private Calendar loanDate;
    @NotNull
    private final CreditType type;
    private double sumOfCredit;
    private double percent;
    private int months;

    public CreditInfo(double sumOfCredit,
                      double percent,
                      int months,
                      @NotNull Calendar loanDate,
                      @NotNull CreditType type) {
        setSumOfCredit(sumOfCredit);
        setLoanDate(loanDate);
        setPercent(percent);
        setMonths(months);
        this.type = type;
    }

    public CreditInfo(double sumOfCredit,
                      double percent,
                      int period,
                      @NotNull Calendar loanDate,
                      @NotNull CreditType type,
                      boolean isYearPeriod) {
        this(sumOfCredit, percent, isYearPeriod ? period * 12 : period, loanDate, type);
    }

    private void setSumOfCredit(double sumOfCredit) {
        if (sumOfCredit <= 0) {
            throw new CreditInfoException("Credit sum must be positive");
        }
        this.sumOfCredit = sumOfCredit;
    }

    private void setPercent(double percent) {
        if (percent <= 0) {
            throw new CreditInfoException("Percent must be positive");
        }
        this.percent = percent / 100;
    }

    private void setLoanDate(@NotNull Calendar date) {
        this.loanDate = Objects.requireNonNull(date);
    }

    private void setMonths(int months) {
        if (months <= 0) {
            throw new CreditInfoException("Months must be positive");
        }
        this.months = months;
    }

    @NotNull
    public CreditType getType() {
        return type;
    }

    @NotNull
    public Calendar getLoanDate() {
        return loanDate;
    }

    public double getPercent() {
        return percent;
    }

    public double getSumOfCredit() {
        return sumOfCredit;
    }

    public int getMonths() {
        return months;
    }
}

package ru.moneycalc.calculator.credit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.exceptions.WrongCreditType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AnnuityCredit implements Credit {
    @NotNull
    private final CreditInfo creditInfo;
    private final double monthlyPayment;
    @NotNull
    private final List<CreditPayment> listOfPayments;

    public AnnuityCredit(@NotNull CreditInfo creditInfo) {
        checkCreditType(creditInfo);
        this.creditInfo = Objects.requireNonNull(creditInfo);
        this.monthlyPayment = countMonthlyPayment();
        this.listOfPayments = new ArrayList<>(creditInfo.getMonths());
        fillPaymentsList();
    }

    private double countMonthlyPayment() {
        double monthlyPercent = creditInfo.getPercent() / 12;
        double tmp = Math.pow(monthlyPercent + 1, creditInfo.getMonths());
        double annuityCoefficient = (monthlyPercent * tmp) / (tmp - 1);
        return creditInfo.getSumOfCredit() * annuityCoefficient;
    }

    private void fillPaymentsList() {
        for (int monthIndex = 1; monthIndex <= this.creditInfo.getMonths(); ++monthIndex) {
            Calendar date = (Calendar) this.creditInfo.getLoanDate().clone();
            date.add(Calendar.MONTH, monthIndex);
            listOfPayments.add(new CreditPayment(getMonthlyPayment(monthIndex),
                    getMonthlyPrincipalPayout(monthIndex),
                    getMonthlyPercentPayout(monthIndex),
                    getMonthlyCreditBalance(monthIndex),
                    date)
            );
        }
    }


    @Override
    @NotNull
    public CreditPayment getPaymentByMonthNumber(int monthNum) {
        return listOfPayments.get(monthNum - 1);
    }

    @Override
    @NotNull
    public List<CreditPayment> getPaymentsList() {
        return listOfPayments;
    }

    private void checkCreditType(CreditInfo creditInfo) {
        if (creditInfo.getType() != CreditType.ANNUITY) {
            throw new WrongCreditType("Annuity credit must have CreditType.ANNUITY");
        }
    }


    @Override
    @NotNull
    public CreditInfo getInfo() {
        return creditInfo;
    }

    @Override
    public double getMonthlyPayment(int monthNumber) {
        return monthlyPayment;
    }

    @Override
    public double getTotalPayout() {
        return creditInfo.getMonths() * monthlyPayment;
    }

    @Override
    public double getMonthlyCreditBalance(int monthNumber) {
        double balance = creditInfo.getSumOfCredit();
        for (int month = 1; month <= monthNumber; ++month) {
            balance -= getMonthlyPrincipalPayout(month);
        }
        return balance;
    }

    @Override
    public double getMonthlyPrincipalPayout(int monthNumber) {
        return getPrincipalAndPercentPayout(monthNumber).get(0);
    }

    @Override
    public double getMonthlyPercentPayout(int monthNumber) {
        return getPrincipalAndPercentPayout(monthNumber).get(1);
    }

    @Override
    public double getTotalPercentPayout() {
        return getTotalPayout() - creditInfo.getSumOfCredit();
    }

    private List<Double> getPrincipalAndPercentPayout(int monthNumber) {
        double lastSum = creditInfo.getSumOfCredit();
        double monthPercent = creditInfo.getPercent() / 12;
        double percentPayout = 0;
        double principalPayout = 0;
        for (int month = 1; month <= monthNumber; ++month) {
            percentPayout = lastSum * monthPercent;
            principalPayout = getMonthlyPayment(month) - percentPayout;
            lastSum -= principalPayout;
        }
        return List.of(principalPayout, percentPayout);
    }
}

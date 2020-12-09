package ru.moneycalc.calculator.credit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.exceptions.WrongCreditType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DifferentiatedCredit implements Credit {
    @NotNull
    private final CreditInfo creditInfo;
    private double totalPayout;
    @NotNull
    private final List<CreditPayment> listOfPayments;

    public DifferentiatedCredit(@NotNull CreditInfo creditInfo) {
        Objects.requireNonNull(creditInfo);
        checkCreditType(creditInfo);
        this.creditInfo = creditInfo;
        this.totalPayout = 0;
        this.listOfPayments = new ArrayList<>();
        fillPaymentsList();
    }

    private void fillPaymentsList() {
        for (int monthIndex = 1; monthIndex <= this.creditInfo.getMonths(); ++monthIndex) {
            Calendar date = (Calendar) this.creditInfo.getLoanDate().clone();
            date.add(Calendar.MONTH, monthIndex);
            listOfPayments.add(new CreditPayment(getMonthlyPayment(monthIndex),
                    getMonthlyPrincipalPayout(monthIndex),
                    getMonthlyPercentPayout(monthIndex),
                    getMonthlyCreditBalance(monthIndex),
                    date));
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
        if (creditInfo.getType() != CreditType.DIFFERENTIATED) {
            throw new WrongCreditType("Differentiated credit must have CreditType.DIFFERENTIATED");
        }
    }

    @Override
    @NotNull
    public CreditInfo getInfo() {
        return creditInfo;
    }

    @Override
    public double getMonthlyPayment(int monthNumber) {
        double base = creditInfo.getSumOfCredit() / creditInfo.getMonths();
        return base + (creditInfo.getSumOfCredit() - (base * (monthNumber - 1))) * (creditInfo.getPercent() / 12);
    }

    @Override
    public double getTotalPayout() {
        if (totalPayout == 0) {
            for (int month = 1; month <= creditInfo.getMonths(); ++month) {
                totalPayout += getMonthlyPayment(month);
            }
        }
        return totalPayout;
    }

    @Override
    public double getMonthlyCreditBalance(int monthNumber) {
        return creditInfo.getSumOfCredit() - (monthNumber * getMonthlyPrincipalPayout(monthNumber));
    }

    @Override
    public double getMonthlyPrincipalPayout(int monthNumber) {
        return creditInfo.getSumOfCredit() / creditInfo.getMonths();
    }

    @Override
    public double getMonthlyPercentPayout(int monthNumber) {
        return getMonthlyPayment(monthNumber) - getMonthlyPrincipalPayout(monthNumber);
    }

    @Override
    public double getTotalPercentPayout() {
        return getTotalPayout() - creditInfo.getSumOfCredit();
    }


}

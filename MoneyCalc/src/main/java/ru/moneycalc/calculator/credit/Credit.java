package ru.moneycalc.calculator.credit;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Credit {
    @NotNull
    CreditInfo getInfo();

    double getMonthlyPayment(int monthNumber);

    double getTotalPayout();

    double getMonthlyCreditBalance(int monthNumber);

    double getMonthlyPrincipalPayout(int monthNumber);

    double getMonthlyPercentPayout(int monthNumber);

    double getTotalPercentPayout();

    @NotNull
    CreditPayment getPaymentByMonthNumber(int monthNum);

    @NotNull
    List<CreditPayment> getPaymentsList();
}

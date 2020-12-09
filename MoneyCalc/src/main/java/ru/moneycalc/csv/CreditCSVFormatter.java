package ru.moneycalc.csv;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.credit.Credit;
import ru.moneycalc.calculator.credit.CreditPayment;
import ru.moneycalc.gui.credit.CreditPaymentRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class CreditCSVFormatter {
    private static final List<String> columnsHeaders = List.of(
            "№",
            "Дата",
            "Сумма",
            "Основной долг",
            "Выплата процентов",
            "Остаток"
    );

    private final String delimiter;

    public CreditCSVFormatter(@NotNull String delimiter) {
        this.delimiter = delimiter;
    }

    @NotNull
    public List<String> format(@NotNull Credit credit) {
        List<String> creditAllPaymentsList = new ArrayList<>(credit.getInfo().getMonths() + 1);
        creditAllPaymentsList.add(generateHeader());
        List<CreditPayment> paymentList = credit.getPaymentsList();
        int paymentCount = 1;
        for (CreditPayment payment : paymentList) {
            creditAllPaymentsList.add(generateRowByPayment(payment, paymentCount));
            paymentCount++;
        }
        creditAllPaymentsList.add(generateTotalPayment(credit));
        return creditAllPaymentsList;
    }

    private String generateHeader() {
        return joinListOfStrings(columnsHeaders);
    }

    private String generateTotalPayment(Credit credit) {
        CreditPayment totalPayment = new CreditPayment(
                credit.getTotalPayout(),
                credit.getInfo().getSumOfCredit(),
                credit.getTotalPercentPayout(),
                0
        );
        CreditPaymentRepresentation paymentRepresentation = new CreditPaymentRepresentation(totalPayment);
        return joinListOfStrings(
                List.of(
                        "Итого",
                        "",
                        paymentRepresentation.getPaymentSum(),
                        paymentRepresentation.getPrincipalPart(),
                        paymentRepresentation.getPercentPart()
                )
        );
    }

    private String generateRowByPayment(@NotNull CreditPayment payment, int paymentIndex) {
        CreditPaymentRepresentation paymentRepresentation = new CreditPaymentRepresentation(payment, paymentIndex);
        return joinListOfStrings(
                List.of(
                        String.valueOf(paymentIndex),
                        paymentRepresentation.getDate(),
                        paymentRepresentation.getPaymentSum(),
                        paymentRepresentation.getPrincipalPart(),
                        paymentRepresentation.getPercentPart(),
                        paymentRepresentation.getCreditBalance()
                )
        );
    }

    private String joinListOfStrings(@NotNull List<String> columns) {
        StringJoiner joiner = new StringJoiner(delimiter);
        columns.forEach(joiner::add);
        return joiner.toString();
    }
}

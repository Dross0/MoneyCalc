package ru.moneycalc.csv;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.deposit.Deposit;
import ru.moneycalc.calculator.deposit.DepositPayment;
import ru.moneycalc.gui.deposit.DepositPaymentRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DepositCSVFormatter {
    private static final List<String> columnsHeaders = List.of(
            "№",
            "Дата",
            "Сумма начисленных процентов",
            "Удержанный налог",
            "Сумма",
            "Остаток вклада"
    );

    @NotNull
    private final String delimiter;

    public DepositCSVFormatter(@NotNull String delimiter) {
        this.delimiter = delimiter;
    }

    @NotNull
    public List<String> format(@NotNull Deposit deposit) {
        List<String> depositPaymentList = new ArrayList<>(deposit.getNumberOfPayments() + 2);
        depositPaymentList.add(generateHeader());
        List<DepositPayment> paymentList = deposit.getPaymentsList();
        int paymentCount = 1;
        for (DepositPayment payment : paymentList) {
            depositPaymentList.add(generateRowByPayment(payment, paymentCount));
            paymentCount++;
        }
        depositPaymentList.add(generateTotalPayment(deposit));
        return depositPaymentList;
    }

    private String generateTotalPayment(Deposit deposit) {
        DepositPayment totalPayment = new DepositPayment(
                deposit.getTotalSum(),
                deposit.getAllInterestCharges(),
                deposit.getAllTaxes()
        );
        DepositPaymentRepresentation paymentRepresentation = new DepositPaymentRepresentation(totalPayment);
        return joinListOfStrings(
                List.of(
                        "Итого",
                        "",
                        paymentRepresentation.getInterestCharges(),
                        paymentRepresentation.getAmountOfTaxes(),
                        paymentRepresentation.getSumIncludingTax(),
                        paymentRepresentation.getDepositBalance()
                )
        );
    }

    private String generateHeader() {
        return joinListOfStrings(columnsHeaders);
    }


    private String generateRowByPayment(@NotNull DepositPayment payment, int paymentIndex) {
        DepositPaymentRepresentation paymentRepresentation = new DepositPaymentRepresentation(payment, paymentIndex);
        return joinListOfStrings(
                List.of(
                        String.valueOf(paymentIndex),
                        paymentRepresentation.getDate(),
                        paymentRepresentation.getInterestCharges(),
                        paymentRepresentation.getAmountOfTaxes(),
                        paymentRepresentation.getSumIncludingTax(),
                        paymentRepresentation.getDepositBalance()
                )
        );
    }

    private String joinListOfStrings(@NotNull List<String> columns) {
        StringJoiner joiner = new StringJoiner(delimiter);
        columns.forEach(joiner::add);
        return joiner.toString();
    }
}

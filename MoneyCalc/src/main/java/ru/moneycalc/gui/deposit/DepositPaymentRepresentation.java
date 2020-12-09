package ru.moneycalc.gui.deposit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.deposit.DepositPayment;
import ru.moneycalc.utils.CalendarUtils;

import java.util.Locale;

public class DepositPaymentRepresentation {
    @NotNull
    private final String sumIncludingTax;
    @NotNull
    private final String depositBalance;
    @NotNull
    private final String interestCharges;
    @NotNull
    private final String amountOfTaxes;
    private String date;
    private final int id;

    public DepositPaymentRepresentation(DepositPayment payment, int id) {
        //Locale.ENGLISH use to make "." as separator at double
        this.sumIncludingTax = String.format(Locale.ENGLISH, "%.2f", payment.getSumIncludingTax());
        this.interestCharges = String.format(Locale.ENGLISH, "%.2f", payment.getInterestCharges());
        this.depositBalance = String.format(Locale.ENGLISH, "%.2f", payment.getSumOfDeposit());
        this.amountOfTaxes = String.format(Locale.ENGLISH, "%.2f", payment.getAmountOfTaxes());
        payment.getDate()
                .ifPresent(date ->
                        this.date = CalendarUtils.getCalendarString(date)
                );
        this.id = id;
    }

    public DepositPaymentRepresentation(DepositPayment payment) {
        this(payment, 0);
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    @NotNull
    public String getAmountOfTaxes() {
        return amountOfTaxes;
    }

    @NotNull
    public String getDepositBalance() {
        return depositBalance;
    }

    @NotNull
    public String getInterestCharges() {
        return interestCharges;
    }

    @NotNull
    public String getSumIncludingTax() {
        return sumIncludingTax;
    }
}

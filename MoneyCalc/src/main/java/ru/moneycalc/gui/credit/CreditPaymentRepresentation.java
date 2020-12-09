package ru.moneycalc.gui.credit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.credit.CreditPayment;
import ru.moneycalc.utils.CalendarUtils;

import java.util.Locale;


public class CreditPaymentRepresentation {
    @NotNull
    private final String paymentSum;
    @NotNull
    private final String principalPart;
    @NotNull
    private final String percentPart;
    @NotNull
    private final String creditBalance;
    private String date;
    private final int id;

    public CreditPaymentRepresentation(@NotNull CreditPayment payment, int id) {
        //Locale.ENGLISH use to make "." as separator at double
        this.paymentSum = String.format(Locale.ENGLISH, "%.2f", payment.getPaymentSum());
        this.principalPart = String.format(Locale.ENGLISH, "%.2f", payment.getPrincipalPart());
        this.percentPart = String.format(Locale.ENGLISH, "%.2f", payment.getPercentPart());
        this.creditBalance = String.format(Locale.ENGLISH, "%.2f", payment.getCreditBalance());
        payment.getDate()
                .ifPresent(date ->
                        this.date = CalendarUtils.getCalendarString(date)
                );

        this.id = id;
    }

    public CreditPaymentRepresentation(CreditPayment payment) {
        this(payment, 0);
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getCreditBalance() {
        return creditBalance;
    }

    @NotNull
    public String getDate() {
        return date;
    }

    @NotNull
    public String getPaymentSum() {
        return paymentSum;
    }

    @NotNull
    public String getPercentPart() {
        return percentPart;
    }

    @NotNull
    public String getPrincipalPart() {
        return principalPart;
    }
}

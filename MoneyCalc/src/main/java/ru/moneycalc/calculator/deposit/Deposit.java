package ru.moneycalc.calculator.deposit;


import org.jetbrains.annotations.NotNull;
import ru.moneycalc.calculator.exceptions.DepositInfoException;
import ru.moneycalc.utils.CalendarUtils;

import java.util.*;


public class Deposit {
    @NotNull
    private final DepositInfo initDepositInfo;
    private final double interestRate;
    private int numberOfPayments;
    @NotNull
    private final List<DepositPayment> listOfPayments;
    private double totalSum;
    private double allInterestCharges;
    @NotNull
    private final Calendar closeDate;
    private final double allTaxes;

    public Deposit(@NotNull DepositInfo depositInfo) {
        this.initDepositInfo = Objects.requireNonNull(depositInfo);
        this.interestRate = depositInfo.getPercent();
        this.listOfPayments = new ArrayList<>();
        this.totalSum = depositInfo.getSumOfDeposit();
        this.allInterestCharges = 0;
        Calendar depositDate = depositInfo.getDepositDate();
        this.closeDate = new GregorianCalendar(
                depositDate.get(Calendar.YEAR),
                depositDate.get(Calendar.MONTH),
                depositDate.get(Calendar.DATE)
        );
        this.closeDate.add(Calendar.MONTH, depositInfo.getMonths());
        this.allTaxes = 0;
        fillPayments();
    }

    @NotNull
    public DepositInfo getInitDepositInfo() {
        return initDepositInfo;
    }

    public int getNumberOfPayments() {
        return this.numberOfPayments;
    }

    public double getAllInterestCharges() {
        return allInterestCharges;
    }

    public double getTotalSum() {
        return totalSum;
    }

    @NotNull
    public DepositPayment getPaymentByIndex(int index) {
        return listOfPayments.get(index);
    }

    private void fillPayments() {
        Calendar currentDate = (Calendar) this.initDepositInfo.getDepositDate().clone();
        Calendar nextPayoutDate = currentDate;
        FrequencyOfPayment frequencyOfPayment = this.initDepositInfo.getFrequency();
        this.numberOfPayments = frequencyOfPayment.getNumberOfPayouts(this.initDepositInfo.getMonths(), currentDate);
        double depositSum = this.initDepositInfo.getSumOfDeposit();
        for (int paymentIndex = 0; paymentIndex < this.numberOfPayments; ++paymentIndex) {
            currentDate = nextPayoutDate;
            nextPayoutDate = frequencyOfPayment.getNewDate(currentDate, this.initDepositInfo.getDepositDate(), this.initDepositInfo.getMonths());
            if (nextPayoutDate.compareTo(this.closeDate) > 0) {
                nextPayoutDate = this.closeDate;
            }
            int daysBetweenPayoutDates = CalendarUtils.daysBetween(currentDate, nextPayoutDate);
            double interestChargesInPeriod = makeCount(depositSum, daysBetweenPayoutDates, currentDate.getActualMaximum(Calendar.DAY_OF_YEAR));
            this.allInterestCharges += interestChargesInPeriod;
            if (this.initDepositInfo.isCapitalization()) {
                depositSum += interestChargesInPeriod;
                this.totalSum = depositSum;
            }
            this.listOfPayments.add(new DepositPayment(depositSum, interestChargesInPeriod, (Calendar) nextPayoutDate.clone(), this.allTaxes));
        }
    }

    private double makeCount(double depositSum, int daysOfPeriod, int daysInYear) {
        if (daysInYear != 365 && daysInYear != 366) {
            throw new DepositInfoException("Days in year must be 365 or 366");
        }
        return depositSum * daysOfPeriod * this.interestRate / daysInYear;
    }

    public double getAllTaxes() {
        return this.allTaxes;
    }

    @NotNull
    public List<DepositPayment> getPaymentsList() {
        return listOfPayments;
    }
}

package ru.moneycalc.calculator.exceptions;

public class CreditInfoException extends RuntimeException {
    private final String errorMessage;

    public CreditInfoException(final String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

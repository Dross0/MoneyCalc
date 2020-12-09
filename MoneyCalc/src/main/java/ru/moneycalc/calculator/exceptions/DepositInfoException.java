package ru.moneycalc.calculator.exceptions;

public class DepositInfoException extends RuntimeException {
    private final String errorMessage;

    public DepositInfoException(final String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}


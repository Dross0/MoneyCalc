package ru.moneycalc.calculator.exceptions;

public class WrongCreditType extends RuntimeException {
    private final String errorMessage;

    public WrongCreditType(final String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

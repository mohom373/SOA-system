package se.liu.ida.tdp024.account.data.api.exceptions;

public class AccountInsufficientHoldingsException extends Exception {
    public AccountInsufficientHoldingsException(String message) {
        super(message);
    }
}

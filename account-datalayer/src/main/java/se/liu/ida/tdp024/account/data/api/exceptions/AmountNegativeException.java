package se.liu.ida.tdp024.account.data.api.exceptions;

public class AmountNegativeException extends Exception {
    public AmountNegativeException(String message) {
        super(message);
    }
}

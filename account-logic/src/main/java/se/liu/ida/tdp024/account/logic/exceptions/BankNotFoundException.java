package se.liu.ida.tdp024.account.logic.exceptions;

public class BankNotFoundException extends Exception{
    public BankNotFoundException(String message) {
        super(message);
    }
}

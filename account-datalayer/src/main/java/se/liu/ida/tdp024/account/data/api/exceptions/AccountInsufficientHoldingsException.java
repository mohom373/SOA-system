package se.liu.ida.tdp024.account.data.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountInsufficientHoldingsException extends Exception {
    public AccountInsufficientHoldingsException(String message) {
        super(message);
    }
}

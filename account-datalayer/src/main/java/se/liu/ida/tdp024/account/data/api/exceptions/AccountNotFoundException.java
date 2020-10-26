package se.liu.ida.tdp024.account.data.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String message) {
        super(message);
    }
}

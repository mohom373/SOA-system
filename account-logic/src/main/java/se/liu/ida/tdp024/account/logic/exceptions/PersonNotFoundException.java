package se.liu.ida.tdp024.account.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonNotFoundException extends Exception{
    public PersonNotFoundException(String message) {
        super(message);
    }
}

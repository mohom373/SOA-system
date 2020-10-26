package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.logic.exceptions.BankNotFoundException;
import se.liu.ida.tdp024.account.logic.exceptions.PersonNotFoundException;

import java.lang.String;
import java.util.List;

public interface AccountLogicFacade {
    
    public String createAccount(String accountType, String person, String bank) throws PersonNotFoundException, BankNotFoundException, AccountServiceConfigurationException;
    
    public List findPerson(String person);
}

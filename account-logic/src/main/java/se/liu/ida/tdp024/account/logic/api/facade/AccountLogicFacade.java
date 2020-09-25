package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;

import java.lang.String;
import java.util.List;

public interface AccountLogicFacade {
    
    public String createAccount(String accountType, String person, String bank);
    
    public List findPerson(String person);

    public String debitAccount(int id, int amount);

    public String creditAccount(int id, int amount);

    public List getTransactions(int id);
}

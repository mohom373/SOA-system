package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;

import java.util.List;

public interface AccountEntityFacade {
    public String create(String accountType, String person, String bank);

    public List<Account> findByPerson(String personKey);
}

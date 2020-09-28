package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public void create(String accountType, String person, String bank);

    public Account findByPerson(String personKey);
}

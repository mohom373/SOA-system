package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;

import java.util.List;

public interface AccountEntityFacade {
    public String create(String accountType, String person, String bank) throws AccountServiceConfigurationException;

    public List<Account> findByPerson(String personKey);
}

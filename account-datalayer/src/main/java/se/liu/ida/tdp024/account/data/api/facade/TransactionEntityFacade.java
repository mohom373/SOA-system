package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountInsufficientHoldingsException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountNotFoundException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exceptions.AmountNegativeException;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public interface TransactionEntityFacade {
    public void createTransaction(String type, int amount, Date created, String status, Account account, EntityManager em);

    public String debit(long id, int amount) throws
            AmountNegativeException,
            AccountNotFoundException,
            AccountServiceConfigurationException,
            AccountInsufficientHoldingsException;
    public String credit(long id, int amount) throws
            AmountNegativeException,
            AccountServiceConfigurationException,
            AccountNotFoundException;

    public List getTransactions(long id);
}

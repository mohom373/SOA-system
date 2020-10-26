package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountInsufficientHoldingsException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountNotFoundException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exceptions.AmountNegativeException;

import java.util.List;

public interface TransactionLogicFacade {
    public String debitAccount(long id, int amount) throws AccountNotFoundException, AccountServiceConfigurationException, AccountInsufficientHoldingsException, AmountNegativeException;

    public String creditAccount(long id, int amount) throws AmountNegativeException, AccountServiceConfigurationException;

    public List getTransactions(long id);
}

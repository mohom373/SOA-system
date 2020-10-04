package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;

import java.util.List;

public interface TransactionLogicFacade {
    public String debitAccount(long id, int amount);

    public String creditAccount(long id, int amount);

    public List getTransactions(long id);
}

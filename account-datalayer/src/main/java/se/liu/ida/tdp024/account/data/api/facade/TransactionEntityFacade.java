package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionEntityFacade {
    public void createTransaction(String type, int amount, Date created, String status, Account account);

    public void debit(long id, int amount);
    public void credit(long id, int amount);

    public List<Transaction> getTransactions(long id);
}

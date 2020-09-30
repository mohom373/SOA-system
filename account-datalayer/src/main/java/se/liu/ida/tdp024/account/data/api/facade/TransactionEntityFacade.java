package se.liu.ida.tdp024.account.data.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public interface TransactionEntityFacade {
    public void createTransaction(String type, int amount, Date created, String status, Account account, EntityManager em);

    public String debit(long id, int amount);
    public String credit(long id, int amount);

    public List getTransactions(long id);
}

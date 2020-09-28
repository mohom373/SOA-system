package se.liu.ida.tdp024.account.data.impl.db.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override
    public void createTransaction(String type, int amount, Date created, String status, Account account) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();

            Transaction transaction = new TransactionDB();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transaction.setCreated(created);
            transaction.setStatus(status);
            transaction.setType(type);

            em.persist(transaction);
            em.getTransaction().commit();

        } catch (Exception e) {

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void debit(long id, int amount) {
        EntityManager em = EMF.getEntityManager();

        if (amount < 0) return;

        String status;
        String type = "DEBIT";
        Date created = new Date();
        try {

            em.getTransaction().begin();


            Account account = em.find(AccountDB.class, id);
            int holdings = account.getHoldings();
            if (holdings >= amount) {
                account.setHoldings((holdings-amount));
                status = "OK";
            } else {
                status = "FAILED";
            }

            createTransaction(type, amount, created, status, account);
            em.merge(account);

            em.getTransaction().commit();

        } catch (Exception e) {

            return;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void credit(long id, int amount) {

    }

    @Override
    public List<Transaction> getTransactions(long id) {
        return null;
    }
}

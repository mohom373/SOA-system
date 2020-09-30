package se.liu.ida.tdp024.account.data.impl.db.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override
    public void createTransaction(String type, int amount, Date created, String status, Account account, EntityManager em) {
        try {
            Transaction transaction = new TransactionDB();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transaction.setCreated(created);
            transaction.setStatus(status);
            transaction.setType(type);

            em.persist(transaction);
        } catch (Exception e) {

        }
    }

    @Override
    public String debit(long id, int amount) {
        EntityManager em = EMF.getEntityManager();

        if (amount < 0) return "FAILED";

        String status;
        String type = "DEBIT";
        Date created = new Date();
        try {

            em.getTransaction().begin();


            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (account == null) {
                return "FAILED";
            }
            int holdings = account.getHoldings();
            if (holdings >= amount) {
                account.setHoldings((holdings-amount));
                status = "OK";
            } else {
                status = "FAILED";

            }

            createTransaction(type, amount, created, status, account, em);
            em.merge(account);

            em.getTransaction().commit();
            return status;
        } catch (Exception e) {

            return null;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public String credit(long id, int amount) {
        EntityManager em = EMF.getEntityManager();

        if (amount < 0) return "FAILED";

        String type = "CREDIT";
        Date created = new Date();
        try {

            em.getTransaction().begin();

            //Account account = em.find(AccountDB.class, id);
            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (account == null) {
                return "FAILED";
            }
            int holdings = account.getHoldings();

            account.setHoldings((holdings+amount));

            createTransaction(type, amount, created, "OK", account, em);
            em.merge(account);

            em.getTransaction().commit();
            return "OK";
        } catch (LockTimeoutException e) {
            System.out.println(e);
            return null;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public List<Transaction> getTransactions(long accountId) {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT a FROM TransactionDB a WHERE a.account.id = :key ");
            query.setParameter("key", accountId);

            return (List<Transaction>) query.getResultList();

        } catch (IllegalArgumentException e) {
            return null;

        } finally {

            em.close();

        }
    }
}

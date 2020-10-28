package se.liu.ida.tdp024.account.data.impl.db.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountInsufficientHoldingsException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountNotFoundException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exceptions.AmountNegativeException;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
//import se.liu.ida.tdp024.account.logging.KafkaLogging;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    //private static final KafkaLogging kafkaLogging = new KafkaLogging();

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
        } catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String debit(long id, int amount) throws
            AmountNegativeException,
            AccountNotFoundException,
            AccountServiceConfigurationException,
            AccountInsufficientHoldingsException {
        EntityManager em = EMF.getEntityManager();

        if (amount < 0) {
            throw new AmountNegativeException("Amount is less than 0");
        }

        String status;
        String type = "DEBIT";
        Date created = new Date();
        try {
            //kafkaLogging.sendToKafka("transaction-events", "Begin transaction");
            em.getTransaction().begin();


            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (account == null) {
                // throw an exception
                throw new AccountNotFoundException("Account is null");
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
            //kafkaLogging.sendToKafka("transaction-events", "Commit transaction");
            em.getTransaction().commit();
            if (status.equals("FAILED")) throw new AccountInsufficientHoldingsException("Not enough money in bank account.");
        } catch (RollbackException | LockTimeoutException | AccountNotFoundException e) {
            if (em.getTransaction().isActive()) {
                //kafkaLogging.sendToKafka("transaction-events", "Rollback transaction");
                em.getTransaction().rollback();
            }
            throw new AccountServiceConfigurationException("Service configuration failed.");
        } finally {
            em.close();
        }
        return status;
    }

    @Override
    public String credit(long id, int amount) throws AmountNegativeException, AccountServiceConfigurationException {
        EntityManager em = EMF.getEntityManager();

        if (amount < 0) {
            throw new AmountNegativeException("Amount is less than 0");
        }
        String type = "CREDIT";
        Date created = new Date();
        try {
            //kafkaLogging.sendToKafka("transaction-events", "Begin transaction");
            em.getTransaction().begin();

            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (account == null) {
                throw new AccountNotFoundException("Account is null.");
            }
            int holdings = account.getHoldings();

            account.setHoldings((holdings+amount));

            createTransaction(type, amount, created, "OK", account, em);
            em.merge(account);
            //kafkaLogging.sendToKafka("transaction-events", "Commit transaction");
            em.getTransaction().commit();
            return "OK";
        } catch (LockTimeoutException | RollbackException | AccountNotFoundException e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                //kafkaLogging.sendToKafka("transaction-events", "Rollback transaction");
                em.getTransaction().rollback();
            }
            throw new AccountServiceConfigurationException("Service configuration failed.");

        } finally {
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
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}

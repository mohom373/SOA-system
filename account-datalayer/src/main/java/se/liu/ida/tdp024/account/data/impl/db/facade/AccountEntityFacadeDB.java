package se.liu.ida.tdp024.account.data.impl.db.facade;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.logging.KafkaLogging;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    private static final KafkaLogging kafkaLogging = new KafkaLogging();

    @Override
    public String create(String accountType, String person, String bank) throws AccountServiceConfigurationException {
        EntityManager em = EMF.getEntityManager();
        try {
            kafkaLogging.sendToKafka("transaction-events", "Begin transaction");
            em.getTransaction().begin();

            Account acc = new AccountDB();
            acc.setBankKey(bank);
            acc.setPersonKey(person);
            acc.setAccountType(accountType);

            em.persist(acc);
            kafkaLogging.sendToKafka("transaction-events", "Commit transaction");
            em.getTransaction().commit();
            return "OK";
        } catch (RollbackException e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                kafkaLogging.sendToKafka("transaction-events", "Rollback transaction");
                em.getTransaction().rollback();
            }
            throw new AccountServiceConfigurationException("Creating the Account failed due to service errors. Please contact your database administrator.");
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findByPerson(String personKey) {
        EntityManager em = EMF.getEntityManager();

        try {
            Query query = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = :personKey ");
            query.setParameter("personKey", personKey);

            return (List<Account>) query.getResultList();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;

        } finally {
            em.close();
        }
    }

}

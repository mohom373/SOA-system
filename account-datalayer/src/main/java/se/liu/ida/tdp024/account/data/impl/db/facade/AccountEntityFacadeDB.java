package se.liu.ida.tdp024.account.data.impl.db.facade;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public void create(String accountType, String person, String bank) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();

            Account acc = new AccountDB();
            acc.setBankKey(bank);
            acc.setPersonKey(person);
            acc.setAccountType(accountType);

            em.persist(acc);
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
    public List<Account> findByPerson(String personKey) {
        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = :personKey ");
            query.setParameter("personKey", personKey);

            return (List<Account>) query.getResultList();

        } catch (Exception e) {

            return null;

        } finally {

            em.close();

        }
    }

}

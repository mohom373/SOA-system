package se.liu.ida.tdp024.account.data.impl.db.facade;

import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public void create(String accountType, String person, String bank) {
        EntityManager em = EMF.getEntityManager();
        try {
            System.out.println("Before create transaction account");
            em.getTransaction().begin();

            Account acc = new AccountDB();
            acc.setBankKey(bank);
            acc.setPersonKey(person);
            acc.setAccountType(accountType);


            System.out.println(acc.getPersonKey());
            System.out.println(acc.getBankKey());
            System.out.println(acc.getAccountType());

            em.persist(acc);
            em.getTransaction().commit();
            System.out.println("After commit transaction account");

        } catch (Exception e) {

        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();

            System.out.println("After close transaction account");
        }
    }

    @Override
    public Account findByPerson(String personKey) {
        EntityManager em = EMF.getEntityManager();

        try {
            System.out.println("This is before query");

            Query query = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = :personKey ");
            query.setParameter("personKey", personKey);
            System.out.println("This is After query");

            System.out.println("This is query" + query.getSingleResult());
            return (Account) query.getSingleResult();

        } catch (Exception e) {

            return null;

        } finally {

            em.close();

        }
    }

}

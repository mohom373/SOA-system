package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

import java.util.List;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        String accountType = "CHECK";
        String person = "1";
        String bank = "Nordea";
        String res = accountEntityFacade.create(accountType, person, bank);

        Assert.assertEquals("OK", res);
    }

    @Test
    public void testFind() {
        accountEntityFacade.create("CHECK", "1", "SWEDBANK");
        accountEntityFacade.create("SAVINGS", "3", "NORDEA");
        accountEntityFacade.create("CHECK", "3", "SWEDBANK");

        String person1 = "1";
        String person2 = "2";
        String person3 = "3";

        List<Account> res1 = accountEntityFacade.findByPerson(person1);
        Assert.assertEquals(1, res1.size());

        List<Account> res2 = accountEntityFacade.findByPerson(person2);
        Assert.assertEquals(0, res2.size());

        List<Account> res3 = accountEntityFacade.findByPerson(person3);
        Assert.assertEquals(2, res3.size());
    }
}
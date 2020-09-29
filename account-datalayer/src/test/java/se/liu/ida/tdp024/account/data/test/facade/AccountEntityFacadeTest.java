package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

import java.util.List;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade;
    private StorageFacade storageFacade;
    
    @After
    public void tearDown() {
       // storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        String accountType1 = "CHECK";
        String accountType2 = "SAVINGS";
        String accountType3 = "RANDOM";
        String person1 = "1";
        String person2 = "10";
        String bank1 = "Nordea";
        String bank2 = "SWEDBANK";
        String bank3 = "SEB";

        String res = accountEntityFacade.create(accountType1, person1, bank1);
        Assert.assertEquals("OK", res);

        String res2 = accountEntityFacade.create(accountType2, person1, bank2);
        Assert.assertEquals("OK", res2);

        String res3 = accountEntityFacade.create(accountType3, person1, bank1);
        Assert.assertEquals("FAILED", res3);

        String res4 = accountEntityFacade.create(accountType1, person2, bank1);
        Assert.assertEquals("FAILED", res4);

        String res5 = accountEntityFacade.create(accountType1, person1, bank3);
        Assert.assertEquals("FAILED", res5);
    }
/*
    @Test
    public void testFind() {
        String person1 = "1";
        String person2 = "2";

        String jsonString = "[{'id': 1, 'personKey': '1', 'accountType': 'CHECK', 'bankKey': '', 'holdings': 0}]";

        List<Account> res1 = accountEntityFacade.findByPerson(person1);
        Assert.assertEquals(list1, res1);
    }*/
}
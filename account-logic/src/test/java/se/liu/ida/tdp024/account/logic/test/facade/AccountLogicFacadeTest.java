package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

import java.util.List;

public class AccountLogicFacadeTest {

    //--- Unit under test ---//
    AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(accountEntityFacade);
    public StorageFacade storageFacade;

    public void setUp() {
      
    }

    @After
    public void tearDown() {
      if (storageFacade != null)
        storageFacade.emptyStorage();
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

        /*
        String res = accountLogicFacade.createAccount(accountType1, person1, bank1);
        Assert.assertEquals("OK", res);

        String res2 = accountLogicFacade.createAccount(accountType2, person1, bank2);
        Assert.assertEquals("OK", res2);

        String res3 = accountLogicFacade.createAccount(accountType3, person1, bank1);
        Assert.assertEquals("FAILED", res3);

        String res4 = accountLogicFacade.createAccount(accountType1, person2, bank1);
        Assert.assertEquals("FAILED", res4);

        String res5 = accountLogicFacade.createAccount(accountType1, person1, bank3);
        Assert.assertEquals("FAILED", res5);

         */
    }
/*
    @Test
    public void testFind() {
        accountLogicFacade.createAccount("CHECK", "1", "NORDEA");
        accountLogicFacade.createAccount("CHECK", "2", "SWEDBANK");
        accountLogicFacade.createAccount("SAVINGS", "2", "NORDEA");

        String person1 = "1";
        String person2 = "2";
        String person3 = "3";

        List<Account> res1 = accountLogicFacade.findPerson(person1);
        Assert.assertEquals(1, res1.size());

        List<Account> res2 = accountLogicFacade.findPerson(person2);
        Assert.assertEquals(2, res2.size());

        List<Account> res3 = accountLogicFacade.findPerson(person3);
        Assert.assertEquals(0, res3.size());

        List<Account> res4 = accountLogicFacade.findPerson("10");
        Assert.assertEquals(0, res4.size());
    }


 */
 }

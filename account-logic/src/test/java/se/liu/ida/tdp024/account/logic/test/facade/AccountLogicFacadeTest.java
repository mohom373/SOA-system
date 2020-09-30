package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

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
    }

    @Test
    public void testFind() {
      /*
        Ska testa:
        - Ska kolla om personen finns i api, om den inte finns returnera []
        - Om personen finns, ska alla konton från personen hämtas och returneras i en lista
        - Om personen finns, men inga konton finns, returnera en tom lista
      */
    }

 }

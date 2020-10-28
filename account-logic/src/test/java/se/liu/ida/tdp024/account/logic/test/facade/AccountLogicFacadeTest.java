package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.exceptions.BankNotFoundException;
import se.liu.ida.tdp024.account.logic.exceptions.PersonNotFoundException;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreate() throws PersonNotFoundException, AccountServiceConfigurationException, BankNotFoundException {

        String accountType1 = "CHECK";
        String accountType2 = "SAVINGS";
        String person1 = "1";
        String person2 = "10";
        String bank1 = "Nordea";
        String bank2 = "SWEDBANK";
        String bank3 = "SEB";

        String res = accountLogicFacade.createAccount(accountType1, person1, bank1);
        Assert.assertEquals("OK", res);

        String res2 = accountLogicFacade.createAccount(accountType2, person1, bank2);
        Assert.assertEquals("OK", res2);

        thrown.expect(PersonNotFoundException.class);
        String res4 = accountLogicFacade.createAccount(accountType1, person2, bank1);

    }

    @Test
    public void testCreate2() throws PersonNotFoundException, AccountServiceConfigurationException, BankNotFoundException {
        String accountType1 = "CHECK";
        String person1 = "1";
        String bank3 = "SEB";

        thrown.expect(BankNotFoundException.class);
        String res5 = accountLogicFacade.createAccount(accountType1, person1, bank3);
    }

    @Test
    public void testFind() throws PersonNotFoundException, AccountServiceConfigurationException, BankNotFoundException {
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


 }

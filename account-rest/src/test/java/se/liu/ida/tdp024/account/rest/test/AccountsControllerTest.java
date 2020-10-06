package se.liu.ida.tdp024.account.rest.test;

import hello.AccountsController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

import java.util.List;

public class AccountsControllerTest {
    public AccountsController accountsController = new AccountsController();
    public StorageFacade storageFacade;


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
        String bank1 = "SWEDBANK";
        String bank2 = "NORDEA";
        String bank3 = "RANDOM";

        String res1 = accountsController.create(accountType1, person1, bank1);
        Assert.assertEquals(res1, "OK");

        String res2 = accountsController.create(accountType2, person1, bank1);
        Assert.assertEquals(res2, "OK");

        String res3 = accountsController.create(accountType3, person1, bank1);
        Assert.assertEquals(res3, "FAILED");

        String res4 = accountsController.create(accountType1, person2, bank2);
        Assert.assertEquals(res4, "FAILED");

        String res5 = accountsController.create(accountType1, person1, bank3);
        Assert.assertEquals(res5, "FAILED");

        String res6 = accountsController.create(null, person1, bank3);
        Assert.assertEquals(res6, "FAILED");
    }

    @Test
    public void testFind() {
        accountsController.create("CHECK", "1", "NORDEA");
        accountsController.create("CHECK", "2", "SWEDBANK");
        accountsController.create("SAVINGS", "2", "NORDEA");

        String person1 = "1";
        String person2 = "2";
        String person3 = "3";

        List<Account> res1 = accountsController.findPerson(person1);
        Assert.assertEquals(1, res1.size());

        List<Account> res2 = accountsController.findPerson(person2);
        Assert.assertEquals(2, res2.size());

        List<Account> res3 = accountsController.findPerson(person3);
        Assert.assertEquals(0, res3.size());
    }
}

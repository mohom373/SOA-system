package se.liu.ida.tdp024.account.rest.test;

import hello.AccountsController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


        ResponseEntity<?> res1 = accountsController.create(accountType1, person1, bank1);
        Assert.assertEquals(res1.getBody(), "OK");

        ResponseEntity<?> res2 = accountsController.create(accountType2, person1, bank1);
        Assert.assertEquals(res2.getBody(), "OK");

        ResponseEntity<?> res3 = accountsController.create(accountType3, person1, bank1);
        Assert.assertEquals(res3.getStatusCode(), HttpStatus.BAD_REQUEST);


        ResponseEntity<?> res4 = accountsController.create(accountType1, person2, bank2);
        Assert.assertEquals(res4.getStatusCode(), HttpStatus.NOT_FOUND);

        ResponseEntity<?> res5 = accountsController.create(accountType1, person1, bank3);
        Assert.assertEquals(res5.getStatusCode(), HttpStatus.NOT_FOUND);

        ResponseEntity<?> res6 = accountsController.create(null, person1, bank3);
        Assert.assertEquals(res6.getStatusCode(), HttpStatus.BAD_REQUEST);


    }

    @Test
    public void testFind() {
        accountsController.create("CHECK", "1", "NORDEA");
        accountsController.create("CHECK", "2", "SWEDBANK");
        accountsController.create("SAVINGS", "2", "NORDEA");

        String person1 = "1";
        String person2 = "2";
        String person3 = "3";

        ResponseEntity<?> res1 = accountsController.findPerson(person1);
        List l = (List) res1.getBody();
        assert l != null;
        Assert.assertEquals(1, l.size());

        ResponseEntity<?> res2 = accountsController.findPerson(person2);
        List l2 = (List) res2.getBody();
        assert l2 != null;
        Assert.assertEquals(2, l2.size());

        ResponseEntity<?> res3 = accountsController.findPerson(person3);
        List l3 = (List) res3.getBody();
        assert l3 != null;
        Assert.assertEquals(0, l3.size());

        ResponseEntity<?> res4 = accountsController.findPerson(null);
        Assert.assertEquals(res4.getStatusCode(), HttpStatus.BAD_REQUEST);

    }


}

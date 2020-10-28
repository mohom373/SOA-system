package se.liu.ida.tdp024.account.rest.test;

import hello.AccountsController;
import hello.TransactionController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

import java.util.List;

public class TransactionControllerTest {
    public TransactionController transactionController = new TransactionController();
    public AccountsController accountsController = new AccountsController();
    public StorageFacade storageFacade;

    @After
    public void tearDown() {
        if (storageFacade != null)
            storageFacade.emptyStorage();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDebit() {
        accountsController.create("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        ResponseEntity<?> res1 = transactionController.debit(id1, amount1);
        Assert.assertEquals(res1.getStatusCode(), HttpStatus.BAD_REQUEST);

        ResponseEntity<?> res2 = transactionController.debit(id1, amount2);
        Assert.assertEquals(res2.getStatusCode(), HttpStatus.BAD_REQUEST);

        transactionController.credit(id1, amount2);
        ResponseEntity<?> res3 = transactionController.debit(id1, amount2);
        Assert.assertEquals("OK", res3.getBody());

        ResponseEntity<?> res4 = transactionController.debit(id2, amount2);
        Assert.assertEquals(res4.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCredit() {
        accountsController.create("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        ResponseEntity<?> res1 = transactionController.credit(id1, amount1);
        Assert.assertEquals(res1.getStatusCode(), HttpStatus.BAD_REQUEST);

        ResponseEntity<?> res2 = transactionController.credit(id1, amount2);
        Assert.assertEquals("OK", res2.getBody());

        ResponseEntity<?> res3 = transactionController.debit(id2, amount2);
        Assert.assertEquals(res3.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTransaction() {
        ResponseEntity r =  accountsController.create("CHECK", "3", "SWEDBANK");
        accountsController.create("CHECK", "5", "SWEDBANK");
        transactionController.credit(3, 20);
        transactionController.debit(3, 5);
        transactionController.debit(5, 5);

        ResponseEntity<?> res1 = transactionController.transactions(3);
        List l1 = (List) res1.getBody();
        assert l1 != null;
        Assert.assertEquals(2, l1.size());

        ResponseEntity<?> res2 = transactionController.transactions(5);
        List l2 = (List) res2.getBody();
        assert l2 != null;
        Assert.assertEquals(1, l2.size());

        ResponseEntity<?> res3 = transactionController.transactions(6);
        List l3 = (List) res3.getBody();
        assert l3 != null;
        Assert.assertEquals(0, l3.size());
    }


}

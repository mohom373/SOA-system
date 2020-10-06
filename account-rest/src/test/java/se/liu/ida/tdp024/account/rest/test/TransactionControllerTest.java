package se.liu.ida.tdp024.account.rest.test;

import hello.AccountsController;
import hello.TransactionController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
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

    @Test
    public void testDebit() {
        accountsController.create("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        String res1 = transactionController.debit(id1, amount1);
        Assert.assertEquals("FAILED", res1);

        String res2 = transactionController.debit(id1, amount2);
        Assert.assertEquals("FAILED", res2);

        transactionController.credit(id1, amount2);
        String res3 = transactionController.debit(id1, amount2);
        Assert.assertEquals("OK", res3);

        String res4 = transactionController.debit(id2, amount2);
        Assert.assertEquals("FAILED", res4);
    }

    @Test
    public void testCredit() {
        accountsController.create("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        String res1 = transactionController.credit(id1, amount1);
        Assert.assertEquals("FAILED", res1);

        String res2 = transactionController.credit(id1, amount2);
        Assert.assertEquals("OK", res2);

        String res3 = transactionController.debit(id2, amount2);
        Assert.assertEquals("FAILED", res3);
    }

    @Test
    public void testTransaction() {
        accountsController.create("CHECK", "4", "SWEDBANK");
        accountsController.create("CHECK", "5", "SWEDBANK");
        transactionController.credit(4, 20);
        transactionController.debit(4, 5);
        transactionController.debit(5, 5);

        List<Transaction> res1 = transactionController.transactions(4);
        Assert.assertEquals(2, res1.size());

        List<Transaction> res2 = transactionController.transactions(5);
        Assert.assertEquals(1, res2.size());

        List<Transaction> res3 = transactionController.transactions(6);
        Assert.assertEquals(0, res3.size());
    }
}

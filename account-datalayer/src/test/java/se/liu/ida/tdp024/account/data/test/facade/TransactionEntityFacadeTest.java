package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

import java.util.List;

public class TransactionEntityFacadeTest {

    //---- Unit under test ----//
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    /*
    @Test
    public void testDebit() {
        accountEntityFacade.create("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        String res1 = transactionEntityFacade.debit(id1, amount1);
        Assert.assertEquals("FAILED", res1);

        String res2 = transactionEntityFacade.debit(id1, amount2);
        Assert.assertEquals("FAILED", res2);

        transactionEntityFacade.credit(id1, amount2);
        String res3 = transactionEntityFacade.debit(id1, amount2);
        Assert.assertEquals("OK", res3);

        String res4 = transactionEntityFacade.debit(id2, amount2);
        Assert.assertEquals("FAILED", res4);
    }

     */

        /*
    @Test
    public void testCredit() {
        accountEntityFacade.create("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        String res1 = transactionEntityFacade.credit(id1, amount1);
        Assert.assertEquals("FAILED", res1);

        String res2 = transactionEntityFacade.credit(id1, amount2);
        Assert.assertEquals("OK", res2);

        String res3 = transactionEntityFacade.debit(id2, amount2);
        Assert.assertEquals("FAILED", res3);

        String res4 = transactionEntityFacade.debit(10, amount2);
        Assert.assertEquals("FAILED", res4);

    }

    @Test
    public void testTransactions() {
        accountEntityFacade.create("CHECK", "1", "SWEDBANK");
        accountEntityFacade.create("CHECK", "2", "SWEDBANK");
        transactionEntityFacade.credit(1, 20);
        //transactionEntityFacade.debit(1, 5);
        //transactionEntityFacade.debit(2, 5);

        List<Transaction> res1 = transactionEntityFacade.getTransactions(1);
        Assert.assertEquals(2, res1.size());

        List<Transaction> res2 = transactionEntityFacade.getTransactions(2);
        Assert.assertEquals(1, res2.size());

        List<Transaction> res3 = transactionEntityFacade.getTransactions(3);
        Assert.assertEquals(0, res3.size());
    }
         */
}

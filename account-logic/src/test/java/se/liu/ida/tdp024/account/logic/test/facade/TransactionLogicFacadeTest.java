package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountInsufficientHoldingsException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountNotFoundException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exceptions.AmountNegativeException;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.exceptions.BankNotFoundException;
import se.liu.ida.tdp024.account.logic.exceptions.PersonNotFoundException;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

import java.util.List;


public class TransactionLogicFacadeTest {

    TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(transactionEntityFacade);
    AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(accountEntityFacade);
    private StorageFacade storageFacade = new StorageFacadeDB();


    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDebit() throws PersonNotFoundException,
            AccountServiceConfigurationException,
            BankNotFoundException, AmountNegativeException,
            AccountNotFoundException, AccountInsufficientHoldingsException {
        accountLogicFacade.createAccount("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        thrown.expect(AmountNegativeException.class);
        String res1 = transactionLogicFacade.debitAccount(id1, amount1);

        thrown.expect(AccountInsufficientHoldingsException.class);
        String res2 = transactionLogicFacade.debitAccount(id1, amount2);

        transactionLogicFacade.creditAccount(id1, amount2);
        String res3 = transactionLogicFacade.debitAccount(id1, amount2);
        Assert.assertEquals("OK", res3);

        thrown.expect(AccountNotFoundException.class);
        String res4 = transactionLogicFacade.debitAccount(id2, amount2);

        thrown.expect(AccountNotFoundException.class);
        String res5 = transactionLogicFacade.debitAccount(10, amount2);
    }

    @Test
    public void testCredit() throws PersonNotFoundException,
            AccountServiceConfigurationException,
            BankNotFoundException,
            AmountNegativeException, AccountInsufficientHoldingsException, AccountNotFoundException {
        accountLogicFacade.createAccount("CHECK", "1", "SWEDBANK");

        long id1 = 1;
        long id2 = 2;
        int amount1 = -1;
        int amount2 = 10;

        thrown.expect(AmountNegativeException.class);
        String res1 = transactionLogicFacade.creditAccount(id1, amount1);

        String res2 = transactionLogicFacade.creditAccount(id1, amount2);
        Assert.assertEquals("OK", res2);

        thrown.expect(AccountNotFoundException.class);
        String res3 = transactionLogicFacade.debitAccount(id2, amount2);

        thrown.expect(AccountNotFoundException.class);
        String res4 = transactionLogicFacade.debitAccount(10, amount2);
    }

    @Test
    public void testTransactions() throws PersonNotFoundException,
            AccountServiceConfigurationException, BankNotFoundException,
            AmountNegativeException, AccountInsufficientHoldingsException,
            AccountNotFoundException {
        accountLogicFacade.createAccount("CHECK", "1", "SWEDBANK");
        accountLogicFacade.createAccount("CHECK", "2", "SWEDBANK");
        transactionLogicFacade.creditAccount(1, 20);
        transactionLogicFacade.debitAccount(1, 5);
        thrown.expect(AccountInsufficientHoldingsException.class);
        transactionLogicFacade.debitAccount(2, 5);

        List<Transaction> res1 = transactionEntityFacade.getTransactions(1);
        Assert.assertEquals(2, res1.size());

        List<Transaction> res2 = transactionEntityFacade.getTransactions(2);
        Assert.assertEquals(1, res2.size());

        List<Transaction> res3 = transactionEntityFacade.getTransactions(3);
        Assert.assertEquals(0, res3.size());

        List<Transaction> res4 = transactionEntityFacade.getTransactions(10);
        Assert.assertEquals(0, res4.size());
    }

}

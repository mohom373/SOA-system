package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.Test;

public class TransactionLogicFacadeTest {

    @Test
    public void testDebit() { // När ska detta faila??
      /*
        Ska testa:
        - Om id inte finns bland konton, returnera FAILED
        - Ev. Om id finns bland konton men summan överstiger beloppet som finns på kontot, returnera FAILED??
        - Om id finns och summan går att debitera, returnera OK och kolla om summan dragits rätt
      */
    }

    @Test
    public void testCredit() {
      /*
        Ska testa:
        - Om inte id finns bland konton, returnera FAILED
        - Om id finns, returnera OK och kolla om summan adderats rätt
      */
    }

    @Test
    public void testTransactions() {
      /*
       Ska testa:
       - Om inte id finns bland konton returnera []
       - Om id finns men listan av transaktioner inte är tom returnera hela listan som JSON object
       - Om id finns men listan är tom returnera []
       */
    }
}

package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;

public class AccountLogicFacadeTest {

    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade;
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
      //accountLogicFacade.createAccount(accounttype, )
      /*
        Ska testa:
        - Om personen och banken finns i api:er, ska OK returneras "createAccount()"
        - Om person eller bank fattas i api:er, ska FAILED returneras
        - Ev. kolla om accounttype är något annat än CHECK eller SAVINGS?
      */
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

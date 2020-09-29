package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;

public class TransactionEntityFacadeTest {

    //---- Unit under test ----//
    private TransactionEntityFacade accountEntityFacade;
    private StorageFacade storageFacade;

    @After
    public void tearDown() {
        // storageFacade.emptyStorage();
    }
}

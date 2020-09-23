package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;

    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    
    /*
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }*/
    public AccountLogicFacadeImpl(String str) {
        String t = str;
    }
     
    public String createAccount(String accountType, String person, String bank) {
        String personResponse = httpHelper.get(FinalConstants.ENDPOINT + "person/find", 
        "name", person);
        System.out.println(personResponse);
        // Gör en rest call till våra tjänster (Bank och Person)

        //return "OK"
        //return "FAILED"
        
    }
    
    public List findPerson(String person) {

    }

    public String debitAccount(int id, int amount) {

    }

    public String creditAccount(int id, int amount) {

    }

    public List getTransactions(int id) {

    }
}

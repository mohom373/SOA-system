package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;

    private static final HTTPHelper httpHelper = new HTTPHelperImpl();

    public static final String PERSONENDPOINT = "http://localhost:8060/";
    public static final String BANKENDPOINT = "http://localhost:8070/";

    public static final String OK = "OK";
    public static final String FAIL = "FAILED";


    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    public String createAccount(String accountType, String person, String bank) {
        if (accountType.equalsIgnoreCase("Savings") || accountType.equalsIgnoreCase("Check")) {

            String personResponse = httpHelper.get(PERSONENDPOINT + "person/find",
                    "key", person);
            String bankResponse;

            if (!personResponse.equals("null")) {
                bankResponse = httpHelper.get(BANKENDPOINT + "bank/find",
                        "name", bank);

                if (!bankResponse.equals("null")) {

                    accountEntityFacade.create(accountType, person, bankResponse);

                    return OK;
                } else {
                    return FAIL;
                }

            } else {
                return FAIL;
            }
        } else {
            return FAIL;
        }
    }

    public List findPerson(String person) {
        String personResponse = httpHelper.get(PERSONENDPOINT + "person/find",
                "key", person);
        if (!personResponse.equals("null")) {
            // TODO: Hämta alla konton från databasen
            List<Account> listOfAccounts = accountEntityFacade.findByPerson(person);
            System.out.println(listOfAccounts);
            return listOfAccounts;
        } else {
            return new ArrayList();
        }
    }

    public String debitAccount(int id, int amount) {
        if (amount < 0) {
            return FAIL;
        } else {
            // TODO: Kolla upp id i databasen.
        }
        return new String();
    }

    public String creditAccount(int id, int amount) {
        if (amount < 0) {
            return FAIL;
        } else {
            // TODO: Kolla upp id i databasen.
        }
        return new String();
    }

    public List getTransactions(int id) {
        return new ArrayList();
    }


}

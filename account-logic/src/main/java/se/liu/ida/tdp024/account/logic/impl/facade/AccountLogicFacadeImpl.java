package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.http.HTTPHelper;
import se.liu.ida.tdp024.account.logic.http.HTTPHelperImpl;

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

                    if (accountEntityFacade.create(accountType, person, bankResponse) == "OK") {
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
        } else {
            return FAIL;
        }
    }

    public List findPerson(String person) {
        String personResponse = httpHelper.get(PERSONENDPOINT + "person/find",
                "key", person);
        if (!personResponse.equals("null")) {
            List<Account> listOfAccounts = accountEntityFacade.findByPerson(person);
            return listOfAccounts;
        } else {
            return new ArrayList();
        }
    }

}

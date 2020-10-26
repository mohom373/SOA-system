package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logging.KafkaLogging;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.exceptions.BankNotFoundException;
import se.liu.ida.tdp024.account.logic.exceptions.PersonNotFoundException;
import se.liu.ida.tdp024.account.logic.http.HTTPHelper;
import se.liu.ida.tdp024.account.logic.http.HTTPHelperImpl;

import java.util.ArrayList;
import java.util.List;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    //private static final KafkaLogging kafkaLogging = new KafkaLogging();
    private static final HTTPHelper httpHelper = new HTTPHelperImpl();
    public static final String PERSONENDPOINT = "http://localhost:8060/";
    public static final String BANKENDPOINT = "http://localhost:8070/";

    public static final String OK = "OK";

    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    public String createAccount(String accountType, String person, String bank) throws
            PersonNotFoundException,
            BankNotFoundException,
            AccountServiceConfigurationException {
        String personResponse = httpHelper.get(PERSONENDPOINT + "person/find",
                "key", person);
        //kafkaLogging.sendToKafka("access-events", personResponse);
        String bankResponse;

        if (!personResponse.equals("null")) {
            bankResponse = httpHelper.get(BANKENDPOINT + "bank/find",
                    "name", bank);
            //kafkaLogging.sendToKafka("access-events", bankResponse);

            if (!bankResponse.equals("null")) {
                try {
                    accountEntityFacade.create(accountType, person, bankResponse);
                    return OK;
                } catch (AccountServiceConfigurationException e) {
                    e.printStackTrace();
                    throw e;
                }

            } else {
                throw new BankNotFoundException("Bank not found in api.");
            }
        } else {
            throw new PersonNotFoundException("Person not found in api.");
        }
    }

    public List findPerson(String person) {
        String personResponse = httpHelper.get(PERSONENDPOINT + "person/find",
                "key", person);
        //kafkaLogging.sendToKafka("access-events", personResponse);
        if (!personResponse.equals("null")) {
            try {
                List<Account> listOfAccounts = accountEntityFacade.findByPerson(person);
                return listOfAccounts;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            return new ArrayList();
        }
    }

}

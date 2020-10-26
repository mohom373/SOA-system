package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import exceptions.InputParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.exceptions.BankNotFoundException;
import se.liu.ida.tdp024.account.logic.exceptions.PersonNotFoundException;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

@RestController
public class AccountsController {

    private AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());


    public static final String ENDPOINT = "/account-rest";

    @RequestMapping(ENDPOINT + "/account/create")
    public String create(@RequestParam(value="accounttype", required = false) String accountType,
                            @RequestParam(value="person", required = false) String person,
                            @RequestParam(value="bank", required = false) String bank) throws InputParameterException {
        if (accountType == null || person == null || bank == null) {
            throw new InputParameterException("None of the input parameters can be null.");
        } else {
            if (!accountType.equalsIgnoreCase("Savings") && !accountType.equalsIgnoreCase("Check")){
                throw new InputParameterException("Account type must either be savings or check.");
            }
            String ret;
            try {
                ret = accountLogicFacade.createAccount(accountType, person, bank);
            } catch (PersonNotFoundException | BankNotFoundException | AccountServiceConfigurationException e) {
                e.printStackTrace();
                return e.toString();
            }

            return ret;
        }

    }


    @RequestMapping(ENDPOINT + "/account/find/person")
    public List findPerson(@RequestParam(value="person", required = false) String person) throws InputParameterException {
        List ret = new ArrayList();

        if (person == null) {
            throw new InputParameterException("None of the input parameters can be null.");
        }
        try {
            ret = accountLogicFacade.findPerson(person);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return ret;
        }
        return ret;
    }
}

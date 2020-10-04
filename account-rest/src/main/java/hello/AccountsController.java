package hello;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

@RestController
public class AccountsController {

    private AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());


    public static final String ENDPOINT = "/account-rest";

    @RequestMapping(ENDPOINT + "/account/create")
    public String create(@RequestParam(value="accounttype", required = false) String accountType,
                            @RequestParam(value="person", required = false) String person,
                            @RequestParam(value="bank", required = false) String bank) {
        if (accountType == null || person == null || bank == null) {
            return "FAILED";
        } else {
            String ret = accountLogicFacade.createAccount(accountType, person, bank);

            return ret;
        }

    }


    @RequestMapping(ENDPOINT + "/account/find/person")
    public List findPerson(@RequestParam(value="person", required = false) String person) {
        List ret = accountLogicFacade.findPerson(person);
        return ret;
    }
}

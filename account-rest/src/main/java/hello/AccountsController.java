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
    public String create(@RequestParam(value="accounttype") String accountType,
                            @RequestParam(value="person") String person,
                            @RequestParam(value="bank") String bank) {
        String ret = accountLogicFacade.createAccount(accountType, person, bank);

        return ret;
    }


    @RequestMapping(ENDPOINT + "/account/find/person")
    public List findPerson(@RequestParam(value="person") String person) {
        List ret = accountLogicFacade.findPerson(person);
        return ret;
    }
}

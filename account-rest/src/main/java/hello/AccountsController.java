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

    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();
    private AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());


    public static final String ENDPOINT = "/account-rest";

    @RequestMapping(ENDPOINT + "/account/create")
    public String str(@RequestParam(value="accounttype") String accountType,
                            @RequestParam(value="person") String person,
                            @RequestParam(value="bank") String bank) {
        String ret = accountLogicFacade.createAccount(accountType, person, bank);

        //System.out.println(ret);
        return ret;
        // Anropa logiklagret och returnera OK eller FAILED
    }


    @RequestMapping(ENDPOINT + "/account/find/person")
    public List account(@RequestParam(value="person") String person) {
        // Anropa logiklagret och returnera en lista på alla konton personen har i JSON
        // Om inga konton hittas, returnera tom lista
        List ret = accountLogicFacade.findPerson(person);
        System.out.println(ret);
        return ret;
    }
    /*
    @RequestMapping("/account/debit")
    public Account account(@RequestParam(value="id") int id, @RequestParam(value="amount") int amount) {
        return new Account(counter.incrementAndGet(),
        // Anropa logiklagret och returnera OK eller FAILED
                            String.format(template, amount));
    }

    @RequestMapping("/account/credit")
    public Account account(@RequestParam(value="id") int id, @RequestParam(value="amount") int amount) {
        // Anropa logiklagret och returnera OK eller FAILED
        return new Account(counter.incrementAndGet(),
                            String.format(template, id));
    }

    @RequestMapping("/account/transactions")
    public Account account(@RequestParam(value="id") int id ){

        // Returnera en lista på transaktioner i JSON format
        // Om inga hittas returnera []

        /* if (json != []) {
            return  transaktion;
        } else {
            return [];
        }
        */
    /*
        return new Account(counter.incrementAndGet(),
                            String.format(template, id));
    }
    */
}

package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

import java.util.List;

@RestController
public class TransactionController {

    private TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());

    public static final String ENDPOINT = "/account-rest";


    @RequestMapping(ENDPOINT + "/account/debit")
    public String debit(@RequestParam(value="id") int id, @RequestParam(value="amount") int amount) {
        String ret = transactionLogicFacade.debitAccount(id, amount);
        return ret;
    }

    @RequestMapping(ENDPOINT + "/account/credit")
    public String credit(@RequestParam(value="id") int id, @RequestParam(value="amount") int amount) {
        String ret = transactionLogicFacade.creditAccount(id, amount);
        return ret;
    }

    @RequestMapping(ENDPOINT + "/account/transactions")
    public List transactions(@RequestParam(value="id") long id ){
        List ret = transactionLogicFacade.getTransactions(id);
        return ret;
    }


}

package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountInsufficientHoldingsException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountNotFoundException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exceptions.AmountNegativeException;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    private TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());

    public static final String ENDPOINT = "/account-rest";

    @RequestMapping(ENDPOINT + "/account/debit")
    public ResponseEntity<?> debit(@RequestParam(value="id") long id, @RequestParam(value="amount") int amount) {
        try {
            transactionLogicFacade.debitAccount(id, amount);
        } catch (AccountNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (AccountServiceConfigurationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AccountInsufficientHoldingsException | AmountNegativeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(ENDPOINT + "/account/credit")
    public ResponseEntity<?> credit(@RequestParam(value="id") long id, @RequestParam(value="amount") int amount) {
        try {
            transactionLogicFacade.creditAccount(id, amount);
        } catch (AmountNegativeException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        } catch (AccountServiceConfigurationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(ENDPOINT + "/account/transactions")
    public ResponseEntity<?> transactions(@RequestParam(value="id") long id ){
        List ret = new ArrayList();

        try {
            ret = transactionLogicFacade.getTransactions(id);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.toString() + " " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List>(ret, HttpStatus.OK);
    }
}

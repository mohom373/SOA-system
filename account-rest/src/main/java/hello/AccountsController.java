package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import exceptions.InputParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@RequestParam(value="accounttype", required = false) String accountType,
                                 @RequestParam(value="person", required = false) String person,
                                 @RequestParam(value="bank", required = false) String bank) {
        if (accountType == null || person == null || bank == null) {
            return new ResponseEntity<>(new InputParameterException("None of the input parameters can be null.").toString() + " " + HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST);
        } else {
            if (!accountType.equalsIgnoreCase("Savings") && !accountType.equalsIgnoreCase("Check")){
                return new ResponseEntity<>(new InputParameterException("Account type must either be savings or check.").toString() + " " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
            }
            try {
                accountLogicFacade.createAccount(accountType, person, bank);
            } catch (PersonNotFoundException | BankNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.toString() + " " + HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
            } catch (AccountServiceConfigurationException e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.toString() + " " + HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }

    }

    @RequestMapping(ENDPOINT + "/account/find/person")
    public ResponseEntity<?> findPerson(@RequestParam(value="person", required = false) String person) {
        List ret = new ArrayList();

        if (person == null) {
            return new ResponseEntity<>(new InputParameterException("None of the input parameters can be null.").toString() + " " + HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        try {
            ret = accountLogicFacade.findPerson(person);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return new ResponseEntity<>(new IllegalArgumentException().toString() + " " + HttpStatus.BAD_REQUEST , HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}

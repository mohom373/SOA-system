package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.exceptions.AccountInsufficientHoldingsException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountNotFoundException;
import se.liu.ida.tdp024.account.data.api.exceptions.AccountServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exceptions.AmountNegativeException;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

import java.util.List;

public class TransactionLogicFacadeImpl implements TransactionLogicFacade {

    private TransactionEntityFacade transactionEntityFacade;

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public String debitAccount(long id, int amount) throws
            AccountNotFoundException,
            AccountServiceConfigurationException,
            AccountInsufficientHoldingsException,
            AmountNegativeException {
        String debitResponse;
        try {
            debitResponse = transactionEntityFacade.debit(id, amount);
        } catch (AccountNotFoundException | AccountServiceConfigurationException | AccountInsufficientHoldingsException | AmountNegativeException e) {
            e.printStackTrace();
            throw e;
        }
        return debitResponse;
    }

    @Override
    public String creditAccount(long id, int amount) throws
            AmountNegativeException,
            AccountServiceConfigurationException,
            AccountNotFoundException {
        String creditResponse;
        try {
            creditResponse = transactionEntityFacade.credit(id, amount);
        } catch (AmountNegativeException | AccountServiceConfigurationException e) {
            e.printStackTrace();
            throw e;
        }
        return creditResponse;
    }

    @Override
    public List getTransactions(long id) {
        List transactionResponse;
        try {
            transactionResponse = transactionEntityFacade.getTransactions(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
        return transactionResponse;
    }
}

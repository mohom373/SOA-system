package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

import java.util.List;

public class TransactionLogicFacadeImpl implements TransactionLogicFacade {

    private TransactionEntityFacade transactionEntityFacade;

    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public String debitAccount(long id, int amount) {
        String debitResponse = transactionEntityFacade.debit(id, amount);
        return debitResponse;
    }

    @Override
    public String creditAccount(long id, int amount) {
        String creditResponse = transactionEntityFacade.credit(id, amount);
        return creditResponse;
    }

    @Override
    public List getTransactions(long id) {
        List transactionResponse = transactionEntityFacade.getTransactions(id);
        return transactionResponse;
    }
}

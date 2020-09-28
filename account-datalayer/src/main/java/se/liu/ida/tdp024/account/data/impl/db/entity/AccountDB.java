package se.liu.ida.tdp024.account.data.impl.db.entity;

import se.liu.ida.tdp024.account.data.api.entity.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AccountDB implements Account {
    @Id
    @GeneratedValue
    private long id;

    private String bankKey;

    private String personKey;

    private String accountType;

    private int holdings = 0;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getBankKey() {
        return bankKey;
    }

    @Override
    public String getPersonKey() {
        return personKey;
    }

    @Override
    public String getAccountType() {
        return accountType;
    }

    @Override
    public int getHoldings() {
        return holdings;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    @Override
    public void setPersonKey(String personKey) {
        this.personKey = personKey;
    }

    @Override
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public void setHoldings(int holdings) {
        this.holdings = holdings;
    }
}

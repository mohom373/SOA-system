package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {

    long getId();

    String getBankKey();

    String getPersonKey();

    String getAccountType();

    int getHoldings();

    void setId(long id);

    void setBankKey(String bankKey);

    void setPersonKey(String personKey);

    void setAccountType(String accountType);

    void setHoldings(int holdings);
}

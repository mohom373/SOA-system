package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.Date;

public interface Transaction extends Serializable {
    public long getId();

    public Account getAccount();

    public Date getCreated();

    public int getAmount();

    public String getStatus();

    public String getType();

    public void setId(long id);

    public void setAccount(Account account);

    public void setAmount(int amount);

    public void setCreated(Date created);

    public void setType(String type);

    public void setStatus(String status);
}

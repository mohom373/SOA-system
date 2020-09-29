package se.liu.ida.tdp024.account.data.impl.db.entity;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
public class TransactionDB implements Transaction {
    @Id
    @GeneratedValue
    private long id;

    private String type;

    private int amount;

    @Temporal(DATE)
    private Date created;

    private String status;

    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}

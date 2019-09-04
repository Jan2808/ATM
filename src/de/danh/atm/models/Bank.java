package de.danh.atm.models;

import java.util.ArrayList;

public class Bank {



    private ArrayList<Account> accounts;

    private static Bank bankInstance = new Bank();

    private Bank() {
        this.accounts = new ArrayList<>();
    }

    public static Bank getBank() {
        return bankInstance;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount);
    }
}

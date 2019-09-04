package de.danh.atm.models;

import de.danh.atm.exceptions.InvalidAmountException;
import de.danh.atm.exceptions.InvalidPinException;

public class Account {
    private double balance;
    private String name;
    private int pin;

    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String ANSI_RESET = "\u001B[0m";

    public Account() {
    }

    public Account(double balance, String name, int pin) {
        this.balance = balance;
        this.name = name;
        this.pin = pin;
    }

    public double showAccountBalance(int pin) {
        if (pin != this.pin) {
            throw new InvalidPinException("The pin you entered was wrong");
        } else
            return this.balance;
    }

    public void showBalance() {
        System.out.println(ANSI_YELLOW + "Your Balance is: " + balance + "€" + ANSI_RESET);
    }

    public void newBalance() {
        System.out.println(ANSI_YELLOW + "Your new Balance is: " + balance + "€" + ANSI_RESET);
    }

    public double withdrawMoney(int pin, int amount) {
        if (pin != this.pin) {
            throw new InvalidPinException("The pin you entered was wrong");
        }
        if (balance < amount) {
            throw new InvalidAmountException("The amount you entered was too high");
        }
        balance = balance - Double.valueOf(amount);
        return balance;
    }

    public double depositMoney(int pin, int amount) {
        if (pin != this.pin) {
            throw new InvalidPinException("The pin you entered was wrong");
        } else if (amount > 0) {
            this.balance += Double.valueOf(amount);
        }
        return this.balance;
    }

    public String getName() {
        return name;
    }

    public boolean checkPIN(int pin) {
        return pin == this.pin;
    }
}
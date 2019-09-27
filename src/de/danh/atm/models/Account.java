package de.danh.atm.models;

import de.danh.atm.exceptions.InvalidAmountException;
import de.danh.atm.exceptions.InvalidPinException;

public class Account {
    private double balance;
    private String name;
    private int tries = 3;
    private int pin;

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Account(double balance, String name, int pin) {
        this.balance = balance;
        this.name = name;
        this.pin = pin;
    }

    /*
    Returns account Balance
     */
    public double showAccountBalance(int pin) {
        if (pin != this.pin) {
            throw new InvalidPinException("The pin you entered was wrong");
        } else
            return this.balance;
    }

    /*
    shows actual Balance
     */
    public void showBalance() {
        System.out.println(ANSI_YELLOW + "Your Balance is: " + balance + "€" + ANSI_RESET);
    }

    /*
    shows new Balance
     */
    public void newBalance() {
        System.out.println(ANSI_YELLOW + "Your new Balance is: " + balance + "€" + ANSI_RESET);
    }

    /*
    returns account balance after it substracts the input amount with the balance
     */
    public double withdrawMoney(double amount) {
        if (balance >= amount && amount >= 0) {
            balance = balance - Double.valueOf(amount);
        }
        if (balance < amount || amount < 0) {
            System.out.println("You entered an invalid withdrawal amount \n" + " or The amount you entered was too high");
        }
        return balance;
    }

    /*
    returns the account balance after adding the input amount to the balance
     */
    public double depositMoney(double amount) {
        if (amount > 0) {
            this.balance += Double.valueOf(amount);
        }
        if (amount < 0) {
            System.out.println("The amount you entered is too low!");
        }
        return this.balance;
    }

    public String getName() {
        return name;
    }

    /*
    checks if the pin input was correct -> else { returns false }
     */
    public boolean checkPIN(int pin) {
        String inputPIN;

        inputPIN = String.valueOf(pin);
        if (inputPIN.matches("^[0-9]*$")) {
            return pin == this.pin;
        } else {
            return false;
        }
    }

    /*
    indicates that you entered the wrong PIN
    -> takes one try out
    if the tries are below zero, then it will reset the amounts of attempts
     */
    public void wrongPIN() {
        System.out.println(ANSI_RED + "The PIN you entered was wrong!" + ANSI_RESET);
        if (tries > 0) {
            tries--;
        } else {
            resetAttempts();
        }
    }

    /*
    resets the amount of attempts that you have
     */
    public void resetAttempts() {
        tries = 3;
    }

    /*
    will show up when you entered an incorrect PIN too many times
    -> "suspends your account" : will exit the programm
     */
    public void outOfAttempts() {
        System.out.println(ANSI_RED + "You've entered an incorrect PIN too many times." + ANSI_RESET);
        System.out.println(ANSI_RED + "Your Account has been suspended!" + ANSI_RESET);
        System.exit(0);
    }

    /*
    returns tries
     */
    public int attempts() {
        return tries;
    }

    /*
    indicates that you have entered and invalid amount
     */
    public void invalidAmount() {
        System.out.println(ANSI_RED + "You've entered an invalid amount" + ANSI_RESET);
    }
}
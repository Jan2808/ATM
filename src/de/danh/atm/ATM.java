package de.danh.atm;

import de.danh.atm.exceptions.InvalidAmountException;
import de.danh.atm.exceptions.InvalidPinException;
import de.danh.atm.models.Account;
import de.danh.atm.models.Bank;

import java.util.Scanner;


public class ATM {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001b[31m";

    private static int atmBalance = 150000;
    private static Account currentAccount;
    private static Scanner scanner = new Scanner(System.in);

    public static void login() {
        System.out.println(ANSI_CYAN + "[Welcome to my ATM Project!]" + ANSI_RESET);
        System.out.println("Please enter your account name:");
        String name = scanner.nextLine();
        for (Account account : Bank.getBank().getAccounts()) {
            if (name.equals(account.getName())) {
                currentAccount = account;
                System.out.println("Welcome: " + ANSI_RED + name + ANSI_RESET + "!");
                showOptions();
                break;
            }
        }
    }

    public static void accountBalance() {
        System.out.println("Please enter your pin: ");
        int pin = scanner.nextInt();
        try {
            currentAccount.showAccountBalance(pin);
            currentAccount.showBalance();
            showOptions();
        } catch (InvalidPinException e) {
            System.out.println("You entered a wrong pin");
        }
    }

    public static void withdrawCash() {
        System.out.println("Please enter your pin: ");
        int pin = scanner.nextInt();
        System.out.println("Please enter the amount you want to withdraw: ");
        int amount = scanner.nextInt();
        try {
            currentAccount.withdrawMoney(pin, amount);
            currentAccount.newBalance();
            showOptions();
        } catch (InvalidPinException e) {
            System.out.println("You entered a wrong pin.");
        } catch (InvalidAmountException e) {
            System.out.println("You entered an invalid withdrawal amount");
        }
    }

    public static void deposit() {
        System.out.println("Please enter your pin: ");
        int pin = scanner.nextInt();
        System.out.println("Please enter the amount you want to deposit: ");
        int amount = scanner.nextInt();
        try {
            currentAccount.depositMoney(pin, amount);
            currentAccount.newBalance();
            showOptions();
        } catch (InvalidPinException e) {
            System.out.println("You entered the wrong pin.");
        } catch (InvalidAmountException e) {
            System.out.println("You entered an invalid deposit amount");
        }
    }

    public static void showOptions() {
        int x;
        Scanner option = new Scanner(System.in);

        System.out.println("--------------------------------------------------------");
        System.out.println("Select your option:");
        System.out.println("[1] - View Balance");
        System.out.println("[2] - Withdraw Funds");
        System.out.println("[3] - Deposit Funds");
        System.out.println("[4] - Exit");
        x = option.nextInt();

        try {
            switch (x) {
                case 1:
                    accountBalance();
                    break;
                case 2:
                    withdrawCash();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    System.out.println("have a nice day!");
                    System.exit(0);

            }
        } catch (InvalidOptionException e) {
            System.out.println("This option is not available.");
        }
    }

    public static void main(String[] args) {
        Bank.getBank().addAccount(new Account(500.0, "danh", 1337));
        login();
    }
}

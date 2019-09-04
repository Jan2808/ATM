package de.danh.atm;

import de.danh.atm.models.Account;

import java.util.Scanner;

public class Optionmenu {
    Account acc = new Account();
    Scanner keyboardInput = new Scanner(System.in);

    public void getLogin() {
        String username;
        String pw;
        String name = "danh";
        String passw = "1337";

        System.out.println("Please enter your username!");
        System.out.print("Username: ");
        username = keyboardInput.nextLine();
        if (username.equalsIgnoreCase(name)) {
            System.out.println("Please enter your password:");
            System.out.print("Password: ");
            pw = keyboardInput.nextLine();
            if (pw.equals(passw)) {
                System.out.println("Welcome: " + name + "!");
                showOptions();
            } else {
                System.out.println("Wrong password!");
                System.out.println("Please try again!");
                getLogin();
            }
        } else {
            System.out.println("Invalid username:" + username);
            System.out.println("Please try again!");
            getLogin();
        }
    }

    public void showOptions() {
        int x;
        Scanner option = new Scanner(System.in);

        System.out.println("--------------------------------------------------------");
        System.out.println("Select your option:");
        System.out.println("[1] - View Balance");
        System.out.println("[2] - Withdraw Funds");
        System.out.println("[3] - Deposit Funds");
        System.out.println("[4] - Exit");
        x = option.nextInt();


        switch (x) {
            case 1:
                acc.showAccountBalance(1232);
                break;
            case 2:
                acc.withdrawMoney(213,333);
                break;
            case 3:
                acc.depositMoney(12312,222);
                break;
            case 4:
                System.out.println("have a nice day!");
                System.exit(0);

        }
    }
}

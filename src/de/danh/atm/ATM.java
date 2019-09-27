package de.danh.atm;

import de.danh.atm.models.Account;
import de.danh.atm.models.Bank;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class ATM {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001b[31m";
    private static Account currentAccount;

    /*
     * checks your Username
     */
    public void login(Scanner scLogin) {
        boolean status = false;

        do {
            System.out.println("Please enter your account name: ");
            String name = " ";
            if (scLogin.hasNextLine()) {
                name = scLogin.nextLine();
            }
            for (Account account : Bank.getBank().getAccounts()) {
                if (name.equals(account.getName())) {
                    currentAccount = account;
                    System.out.println("Welcome: " + ANSI_CYAN + name + ANSI_RESET + "!");
                    status = true;
                    break;
                }
            }
            if (currentAccount == null) {
                System.out.println(ANSI_RED + "Account not found" + ANSI_RESET);
            }
        } while (!status);
    }

    /*
     * shows the Bank options, which are available
     */
    public void showOptions(Scanner option) {
        boolean status = false;
        do {
            String inputOption;

            System.out.println("--------------------------------------------------------");
            System.out.println("Select your option:");
            System.out.println("[1] - View Balance");
            System.out.println("[2] - Withdraw Funds");
            System.out.println("[3] - Deposit Funds");
            System.out.println("[4] - Exit");
            if (option.hasNextLine()) {
                inputOption = option.nextLine();

                try {
                    int x = Integer.parseInt(inputOption);
                    switch (x) {
                        case 1:
                            accountBalance(option);
                            status = true;
                            break;
                        case 2:
                            withdrawCash(option);
                            status = true;
                            break;
                        case 3:
                            deposit(option);
                            status = true;
                            break;
                        case 4:
                            System.out.println("have a nice day! \n");
                            currentAccount.resetAttempts();
                            currentAccount = null;
                            status = true;
                            break;
                        default:
                            System.out.println("Wrong input");
                            System.out.println("Please use the numbers from 1-4");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong input");
                    System.out.println("Please use the numbers from 1-4");
                }
            }
        } while (!status);
    }

    /* requests a PIN
    if the pin was correct --> shows accounts balance
     */
    public void accountBalance(Scanner scBalance) {
        boolean status = false;
        try {
            do {
                System.out.println("Please enter your pin: ");
                int pin = 0;
                try {
                    String input = scBalance.nextLine();
                    pin = Integer.valueOf(input);
                } catch (NumberFormatException e) {
                    if (currentAccount.attempts() == 0) {
                        currentAccount.outOfAttempts();
                        break;
                    } else if (currentAccount.attempts() > 0) {
                        currentAccount.wrongPIN();
                        System.out.println("Your tries: " + currentAccount.attempts());
                    }
                    continue;
                }
                if (currentAccount.checkPIN(pin)) {
                    currentAccount.resetAttempts();
                    currentAccount.showAccountBalance(pin);
                    currentAccount.resetAttempts();
                    currentAccount.showBalance();
                    showOptions(scBalance);
                    status = true;
                } else {
                    if (currentAccount.attempts() == 0) {
                        currentAccount.outOfAttempts();
                        break;
                    } else if (currentAccount.attempts() > 0) {
                        currentAccount.wrongPIN();
                        currentAccount.attempts();
                        System.out.println("Your tries: " + currentAccount.attempts());
                    }
                }
            } while (!status);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    requests a PIN
    if the pin was correct --> withdraws cash from balance
     */
    public void withdrawCash(Scanner scCash) {
        boolean status = false;
        try {
            do {
                System.out.println("Please enter your pin: ");
                int pin = 0;
                try {
                    String input = scCash.nextLine();
                    pin = Integer.valueOf(input);
                } catch (NumberFormatException e) {
                    if (currentAccount.attempts() == 0) {
                        currentAccount.outOfAttempts();
                        currentAccount = null;
                        break;
                    } else if (currentAccount.attempts() > 0) {
                        currentAccount.wrongPIN();
                        System.out.println("Your tries: " + currentAccount.attempts());
                    }
                    continue;
                }

                if (currentAccount.checkPIN(pin)) {
                    currentAccount.resetAttempts();
                    System.out.println("Please enter the amount you want to withdraw: ");
                    double amount;
                    String input = scCash.nextLine();
                    try {
                        amount = Double.parseDouble(input);
                        currentAccount.withdrawMoney(amount);
                        currentAccount.newBalance();
                        showOptions(scCash);

                    } catch (NumberFormatException e) {
                        currentAccount.invalidAmount();
                        showOptions(scCash);
                    }
                    status = true;
                } else {
                    //TODO Verschachtelung
                    if (currentAccount.attempts() == 0) {
                        currentAccount.outOfAttempts();
                        currentAccount = null;
                        break;
                    } else if (currentAccount.attempts() > 0) {
                        currentAccount.wrongPIN();
                        System.out.println("Your tries: " + currentAccount.attempts());
                    }
                }
            } while (!status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * requests a PIN
     * if the pin was correct --> deposits cash to balance
     */
    public void deposit(Scanner scDeposit) {
        boolean status = false;

        try {
            do {
                System.out.println("Please enter your pin: ");
                int pin = 0;
                try {
                    String input = scDeposit.nextLine();
                    pin = Integer.valueOf(input);
                } catch (InputMismatchException e) {
                    scDeposit.nextLine();
                    currentAccount.wrongPIN();
                    continue;
                } catch (NumberFormatException e) {
                    currentAccount.wrongPIN();
                    continue;
                }
                if (currentAccount.checkPIN(pin)) {
                    currentAccount.resetAttempts();
                    System.out.println("Please enter the amount you want to deposit: ");
                    double amount;
                    String input = scDeposit.nextLine();
                    try {
                        amount = Double.parseDouble(input);
                        currentAccount.depositMoney(amount);
                        currentAccount.newBalance();
                        showOptions(scDeposit);

                    } catch (NumberFormatException e) {
                        currentAccount.invalidAmount();
                        showOptions(scDeposit);
                    }
                    status = true;
                } else {
                    if (currentAccount.attempts() == 0) {
                        currentAccount.outOfAttempts();
                        break;
                    } else if (currentAccount.attempts() > 0) {
                        currentAccount.wrongPIN();
                        System.out.println("Your tries: " + currentAccount.attempts());
                    }
                }
            } while (!status);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {

        System.out.println(ANSI_CYAN + "[Welcome to my ATM Project!]" + ANSI_RESET);
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\dnguyen\\Desktop\\ATM\\src\\de\\danh\\atm\\users.txt")));
        String currentLine = null;
        while ((currentLine = br.readLine()) != null) {
            System.out.println(currentLine);
            String[] data = currentLine.split(" ");
            Bank.getBank().addAccount(new Account(Double.valueOf(data[2]), data[0], Integer.valueOf(data[1])));
        }
        br.close();
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            atm.login(scanner);
            atm.showOptions(scanner);
        }
    }
}

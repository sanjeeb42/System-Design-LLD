package LiskovSubsitutionPrinciple;

import java.util.ArrayList;
import java.util.List;

interface DepositOnlyAccount {
    void deposit(double amount);
}

interface WithdrawableAccount extends DepositOnlyAccount {
    void withdraw(double amount);
}

class SavingsAccount implements WithdrawableAccount {
    private double balance;

    public SavingsAccount() {
        this.balance = 0;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("[SavingsAccount] Deposited: " + amount + " | Balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("[SavingsAccount] Withdrawn: " + amount + " | Balance: " + balance);
        } else {
            System.out.println("[SavingsAccount] Withdrawal failed: Insufficient funds!");
        }
    }
}

class CurrentAccount implements WithdrawableAccount {
    private double balance;

    public CurrentAccount() {
        this.balance = 0;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("[CurrentAccount] Deposited: " + amount + " | Balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("[CurrentAccount] Withdrawn: " + amount + " | Balance: " + balance);
        } else {
            System.out.println("[CurrentAccount] Withdrawal failed: Insufficient funds!");
        }
    }
}

class FixedTermAccount implements DepositOnlyAccount {
    private double balance;

    public FixedTermAccount() {
        this.balance = 0;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("[FixedTermAccount] Deposited: " + amount + " | Balance: " + balance);
    }
}

class BankClient {
    private List<WithdrawableAccount> withdrawableAccounts;
    private List<DepositOnlyAccount> depositOnlyAccounts;

    public BankClient(List<WithdrawableAccount> withdrawableAccounts,
                      List<DepositOnlyAccount> depositOnlyAccounts) {
        this.withdrawableAccounts = withdrawableAccounts;
        this.depositOnlyAccounts = depositOnlyAccounts;
    }

    public void processTransactions() {
        for (WithdrawableAccount acc : withdrawableAccounts) {
            acc.deposit(1000);
            acc.withdraw(500);
        }
        for (DepositOnlyAccount acc : depositOnlyAccounts) {
            acc.deposit(5000);
        }
    }
}

public class LiskovSubstitutionPrinciple {
    public static void main(String[] args) {
        List<WithdrawableAccount> withdrawableAccounts = new ArrayList<>();
        withdrawableAccounts.add(new SavingsAccount());
        withdrawableAccounts.add(new CurrentAccount());

        List<DepositOnlyAccount> depositOnlyAccounts = new ArrayList<>();
        depositOnlyAccounts.add(new FixedTermAccount());

        BankClient client = new BankClient(withdrawableAccounts, depositOnlyAccounts);
        client.processTransactions();
    }
}

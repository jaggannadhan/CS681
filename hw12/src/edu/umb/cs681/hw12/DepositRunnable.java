package edu.umb.cs681.hw12;

public class DepositRunnable implements Runnable {

    BankAccount bankAccount;

    DepositRunnable(DeadlockedBankAccount2 bankAccount) {
        this.bankAccount = bankAccount;
    }

    DepositRunnable(ThreadSafeBankAccount2 bankAccount) {
        this.bankAccount = bankAccount;
    }

    DepositRunnable(DeadlockedBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            bankAccount.deposit(100); 
        }
    }

}

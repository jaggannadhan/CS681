package edu.umb.cs681.hw12;

public class WithdrawRunnable implements Runnable {
    BankAccount bankAccount;

    WithdrawRunnable(ThreadSafeBankAccount2 bankAccount) {
        this.bankAccount = bankAccount;
    }

     WithdrawRunnable(DeadlockedBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    WithdrawRunnable(DeadlockedBankAccount2 bankAccount) {
        this.bankAccount = bankAccount;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            bankAccount.withdraw(100); 
        }
    }

}

package com.eteration.simplebanking.model;


// This class is a placeholder you can change the complete implementation
public class WithdrawalTransaction extends Transaction {


    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    @Override
    void perform(BankAccount account) throws InsufficientBalanceException {
        account.debit(amount);
    }
}



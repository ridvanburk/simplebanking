package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation
public class DepositTransaction extends Transaction  {

    public DepositTransaction(double amount) {
        super(amount);
    }


    @Override
    void perform(BankAccount account) {
        account.credit(amount);
    }
}

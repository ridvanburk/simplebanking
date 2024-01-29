package com.eteration.simplebanking.model;

public class PhoneBillPaymentTransaction extends Transaction {
    private String phoneNumber;

    public PhoneBillPaymentTransaction(double amount, String phoneNumber) {
        super(amount);
        this.phoneNumber = phoneNumber;
    }
    @Override
    void perform(BankAccount bankAccount) {
        bankAccount.setBalance(bankAccount.getBalance()-amount);
    }
}

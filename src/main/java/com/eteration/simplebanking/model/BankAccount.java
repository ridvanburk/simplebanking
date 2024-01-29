package com.eteration.simplebanking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {


    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public BankAccount(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }
    private String owner;
    @Id
    private String accountNumber;

    private double balance;
    @OneToMany(mappedBy = "bankAccount",cascade = CascadeType.ALL )
    private List<Transaction>transactions;

    public void post(Transaction transaction) throws InsufficientBalanceException {
        transaction.perform(this);
        transactions.add(transaction);
    }
   public void credit(double amount){
       balance += amount;
       System.out.println("Credited " + amount + " to account " + accountNumber + ". New balance: " + balance);

   }
   public  void debit(double amount) throws InsufficientBalanceException {
       if (amount > 0  && amount <= balance) {
           balance -= amount;
           System.out.println("Debited " + amount + " from account " + accountNumber + ". New balance: " + balance);
       }
       else {
           throw new InsufficientBalanceException();
       }
    }
}

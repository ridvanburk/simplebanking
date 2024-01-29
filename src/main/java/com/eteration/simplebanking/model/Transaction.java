package com.eteration.simplebanking.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// This class is a place holder you can change the complete implementation

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Transaction {
   static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    double amount;
    LocalDateTime transactionDate;
    String approvalCode;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private BankAccount bankAccount;

    abstract void perform(BankAccount bankAccount) throws InsufficientBalanceException;

    public Transaction(double amount){
        this.amount=amount;
        transactionDate=LocalDateTime.parse(LocalDateTime.now().format(formatter));
        this.approvalCode= UUID.randomUUID().toString();
    }
	
}

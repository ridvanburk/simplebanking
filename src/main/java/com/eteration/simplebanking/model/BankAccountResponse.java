package com.eteration.simplebanking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountResponse {
    private String accountNumber;
    private String owner;
    private double balance;
    private Date createDate;
    private List<TransactionResponse> transactions;
}

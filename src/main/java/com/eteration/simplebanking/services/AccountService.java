package com.eteration.simplebanking.services;


import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

// This class is a placeholder you can change the complete implementation
@Service
public class AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;


    public AccountService(BankAccountRepository bankAccountRepository,TransactionRepository transactionRepository){
        this.bankAccountRepository=bankAccountRepository;
        this.transactionRepository=transactionRepository;

    }


    public BankAccountResponse getAccountDetails(String accountNumber) throws AccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findByAccountNumber(accountNumber);
        if(Objects.isNull(bankAccount)){
            throw new AccountNotFoundException("Hesap bulunamadı.");
        }
       return buildAccountResponse(bankAccount);

    }

    @Transactional
    public TransactionResponse deposit(String accountNumber, double amount) throws InsufficientBalanceException {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        DepositTransaction depositTransaction=new DepositTransaction(amount);
        bankAccount.post(depositTransaction);
        transactionRepository.save(depositTransaction);
        bankAccountRepository.save(bankAccount);
        return buildTransactionResponse(depositTransaction);
    }

    @Transactional
    public TransactionResponse withdraw(String accountNumber, double amount) throws InsufficientBalanceException {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
        WithdrawalTransaction withdrawalTransaction=new WithdrawalTransaction(amount);
        if(bankAccount.getBalance()<amount){
            throw new InsufficientBalanceException();
        }
        bankAccount.post(withdrawalTransaction);
        transactionRepository.save(withdrawalTransaction);
        bankAccountRepository.save(bankAccount);
        return buildTransactionResponse(withdrawalTransaction);
    }

    private BankAccountResponse buildAccountResponse(BankAccount bankAccount) {
        BankAccountResponse response = new BankAccountResponse();
        response.setAccountNumber(bankAccount.getAccountNumber());
        response.setOwner(bankAccount.getOwner());
        response.setBalance(bankAccount.getBalance());
        response.setCreateDate(new Date());
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : bankAccount.getTransactions()) {
            transactionResponses.add(buildTransactionResponse(transaction));
        }
        response.setTransactions(transactionResponses);
        return response;
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setDate(transaction.getTransactionDate());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getClass().getSimpleName());
        // Set other transaction-specific fields
        if (transaction instanceof WithdrawalTransaction) {
            response.setApprovalCode(transaction.getApprovalCode());
        } else if (transaction instanceof DepositTransaction) {
            response.setApprovalCode(transaction.getApprovalCode());
        }
        return response;
    }

}

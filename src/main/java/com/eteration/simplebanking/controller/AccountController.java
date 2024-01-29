package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("account/v1")
public class AccountController {

private final AccountService accountService;
public AccountController(AccountService accountService){
    this.accountService=accountService;
}

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionResponse> creditAccount(@PathVariable String accountNumber, @RequestBody TransactionRequest request) throws InsufficientBalanceException {
return new ResponseEntity<>(accountService.deposit(accountNumber, request.getAmount()),HttpStatus.OK);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionResponse> debitAccount(@PathVariable String accountNumber, @RequestBody TransactionRequest request) throws InsufficientBalanceException {
        return new ResponseEntity<>(accountService.withdraw(accountNumber,request.getAmount()),HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccountData(@PathVariable String accountNumber) throws AccountNotFoundException {
          return  new ResponseEntity<>(accountService.getAccountDetails(accountNumber), HttpStatus.OK);
    }

}
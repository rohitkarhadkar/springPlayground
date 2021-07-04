package com.cqrs.axon.command.controller;

import com.cqrs.axon.command.dto.CreateAccountRequest;
import com.cqrs.axon.command.dto.DepositRequest;
import com.cqrs.axon.command.dto.WithdrawalRequest;
import com.cqrs.axon.command.service.AccountCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/bank-account")
public class BankAccountController {

    private final AccountCommandService accountCommandService;

    public BankAccountController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request) {
        try {
            CompletableFuture<String> response =
                    accountCommandService.createAccount(request);

            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest request) {
        try {
            accountCommandService.depositToAccount(request);

            return new ResponseEntity<>("Amount credited", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest request) {
        try {
            accountCommandService.withdrawFromAccount(request);

            return new ResponseEntity<>("Amount debited.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
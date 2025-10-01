package com.zzepish.accounts.controller;

import com.zzepish.accounts.dto.AccountDTO;
import com.zzepish.accounts.dto.CustomerDTO;
import com.zzepish.accounts.dto.ResponseDTO;
import com.zzepish.accounts.repository.AccountRepository;
import com.zzepish.accounts.repository.CustomerRepository;
import com.zzepish.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountController {
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private IAccountService accountService;

    @GetMapping
    public List<AccountDTO> all()
    {
        return this
                .accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::createFromAccount)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> post(@RequestBody CustomerDTO customerDTO)
    {
        try {
            this.accountService.createAccountFromCustomer(customerDTO);
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), exception.getMessage(), LocalDateTime.now()));
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.toString(), "CREATED", LocalDateTime.now()));
    }

    @GetMapping("/customer/{mobileNumber}")
    public ResponseEntity<CustomerDTO> getAccountDetails(@PathVariable String mobileNumber)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.accountService.fetchAccount(mobileNumber));
    }

    @PatchMapping
    public ResponseEntity<ResponseDTO> update(@RequestBody CustomerDTO customerDTO)
    {
        boolean isUpdated = this.accountService.updateAccount(customerDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(HttpStatus.OK.toString(), "Updated", LocalDateTime.now()));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "Bad request", LocalDateTime.now()));
    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable String mobileNumber)
    {
        this.accountService.deleteAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseDTO(HttpStatus.NO_CONTENT.toString(), "Deleted", LocalDateTime.now()));
    }
}

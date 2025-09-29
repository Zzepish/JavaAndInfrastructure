package com.zzepish.accounts.service.impl;

import com.zzepish.accounts.dto.CustomerDTO;
import com.zzepish.accounts.entity.Account;
import com.zzepish.accounts.entity.Customer;
import com.zzepish.accounts.exception.CustomerAlreadyExistsException;
import com.zzepish.accounts.mapper.AccountMapper;
import com.zzepish.accounts.mapper.CustomerMapper;
import com.zzepish.accounts.repository.AccountRepository;
import com.zzepish.accounts.repository.CustomerRepository;
import com.zzepish.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private static final String ACCOUNT_TYPE_SAVINGS = "SAVINGS";
    private static final String ACCOUNT_BRANCH_ADDRESS = "Some branch address";

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccountFromCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExistsException {
        Customer customer = CustomerMapper.mapCustomerDTOToCustomer(customerDTO, new Customer());
        Optional<Customer> existingCustomer = this.customerRepository.findByMobileNumber(customerDTO.getMobileNumber());

        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customerDTO.getMobileNumber() + " already exists");
        }

        this.customerRepository.save(customer);
        Account account = this.createNewAccount(customer);
        this.accountRepository.save(account);
    }

    private Account createNewAccount(Customer  customer) {
        Account account = new Account();

        account.setAccountType(AccountServiceImpl.ACCOUNT_TYPE_SAVINGS);
        account.setBranchAddress(AccountServiceImpl.ACCOUNT_BRANCH_ADDRESS);
        account.setCustomerId(customer.getCustomerId());
        account.setName(customer.getName());

        return account;
    }
}

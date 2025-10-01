package com.zzepish.accounts.service.impl;

import com.zzepish.accounts.dto.AccountDTO;
import com.zzepish.accounts.dto.CustomerDTO;
import com.zzepish.accounts.entity.Account;
import com.zzepish.accounts.entity.Customer;
import com.zzepish.accounts.exception.CustomerAlreadyExistsException;
import com.zzepish.accounts.exception.ResourceNotFoundException;
import com.zzepish.accounts.mapper.AccountMapper;
import com.zzepish.accounts.mapper.CustomerMapper;
import com.zzepish.accounts.repository.AccountRepository;
import com.zzepish.accounts.repository.CustomerRepository;
import com.zzepish.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private static final String ACCOUNT_TYPE_SAVINGS = "SAVINGS";
    private static final String ACCOUNT_BRANCH_ADDRESS = "Some branch address";

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    @Transactional
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


    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile number", mobileNumber)
        );

        Account account = this.accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer ID", customer.getCustomerId().toString())
        );

        CustomerDTO customerDTO = CustomerMapper.mapCustomerToCustomerDTO(customer, new CustomerDTO());
        AccountDTO accountDTO = AccountMapper.mapAccountToAccountDTO(account, new AccountDTO());

        customerDTO.setAccount(accountDTO);

        return customerDTO;
    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;

        AccountDTO accountDTO = customerDTO.getAccount();

        if (accountDTO != null) {
            Account account = this.accountRepository.findByCustomerId(accountDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account Number", accountDTO.getAccountNumber().toString())
            );

            AccountMapper.mapAccountDTOToAccount(accountDTO, account);
            account = this.accountRepository.save(account);

            final Long customerId = account.getCustomerId();
            Customer customer = this.customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "Customer ID", customerId.toString())
            );

            CustomerMapper.mapCustomerDTOToCustomer(customerDTO, customer);
            this.customerRepository.save(customer);
            isUpdated = true;
        }

        return  isUpdated;
    }

    @Override
    @Transactional
    public void deleteAccount(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile number", mobileNumber)
        );

        Account account = this.accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "Customer ID", customer.getCustomerId().toString())
        );

        this.accountRepository.deleteByAccountNumber(account.getAccountNumber());
        if (account.getAccountNumber() == 1) {
            throw new ResourceNotFoundException("Account", "Account Number", account.getAccountNumber().toString());
        }
        this.customerRepository.deleteByCustomerId(customer.getCustomerId());
    }
}

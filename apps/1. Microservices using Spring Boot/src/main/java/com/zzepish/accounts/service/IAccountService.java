package com.zzepish.accounts.service;

import com.zzepish.accounts.dto.CustomerDTO;

import javax.security.auth.login.AccountException;

public interface IAccountService {
    void createAccountFromCustomer(CustomerDTO customerDTO) throws AccountException;
}

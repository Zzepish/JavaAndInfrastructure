package com.zzepish.accounts.mapper;

import com.zzepish.accounts.dto.AccountDTO;
import com.zzepish.accounts.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {
    public static AccountDTO mapAccountToAccountDTO(Account account, AccountDTO accountDTO) {
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setName(account.getName());
        accountDTO.setBranchAddress(account.getBranchAddress());

        return accountDTO;
    }

    public static Account mapAccountDTOToAccount(AccountDTO accountDTO, Account account) {
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setName(accountDTO.getName());
        account.setBranchAddress(accountDTO.getBranchAddress());

        return account;
    }
}

package com.zzepish.accounts.dto;

import com.zzepish.accounts.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long accountNumber;

    private String name;

    private String accountType;

    private String branchAddress;

    public static AccountDTO createFromAccount(Account account) {
        return new AccountDTO(
                account.getAccountNumber(),
                account.getName(),
                account.getAccountType(),
                account.getBranchAddress()
        );
    }
}

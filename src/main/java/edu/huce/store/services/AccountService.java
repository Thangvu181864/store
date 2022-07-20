package edu.huce.store.services;

import edu.huce.store.models.Account;

public interface AccountService {
    Account validateAccount(Account account);

    Account registerAccount(Account account);

    Account findAccount(Integer id);

    Account updateAccount(Account account);

}

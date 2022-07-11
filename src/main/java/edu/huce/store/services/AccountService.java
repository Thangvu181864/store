package edu.huce.store.services;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.models.Account;

public interface AccountService {
    Account validateAccount(Account account) throws EtAuthException;

    Account registerAccount(Account account) throws EtAuthException;

    Account updateAccount(Account account)
            throws EtAuthException;

}

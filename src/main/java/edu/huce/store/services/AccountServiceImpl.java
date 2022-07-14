package edu.huce.store.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.models.Account;
import edu.huce.store.repositories.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account validateAccount(Account account) throws EtAuthException {
        if (account.getUsername() != null)
            account.setUsername(account.getUsername().toLowerCase());
        return accountRepository.findByUsernameAndPassword(account);
    }

    @Override
    public Account registerAccount(Account account)
            throws EtAuthException {
        if (account.getUsername() != null)
            account.setUsername(account.getUsername().toLowerCase());
        Integer count = accountRepository.getCountByUsername(account.getUsername());
        if (count > 0) {
            throw new EtAuthException("Username already in use");
        }
        Integer id = accountRepository.create(account);
        return accountRepository.findById(id);
    }

    @Override
    public Account updateAccount(Account account)
            throws EtAuthException {
        if (account.getUsername() != null)
            account.setUsername(account.getUsername().toLowerCase());
        Integer accountId = accountRepository.update(account);
        return accountRepository.findById(accountId);
    }

    @Override
    public Account findAccount(Integer id) throws EtBadRequestException {
        return accountRepository.findById(id);
    }

}

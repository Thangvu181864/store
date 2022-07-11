package edu.huce.store.repositories;

import edu.huce.store.exceptions.EtAuthException;
import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.models.Account;

public interface AccountRepository {
    Integer create(Account account) throws EtAuthException;

    Integer update(Account account) throws EtBadRequestException;

    Account findByUsernameAndPassword(Account account) throws EtBadRequestException;

    Integer getCountByUsername(String username);

    Account findById(Integer id);

}

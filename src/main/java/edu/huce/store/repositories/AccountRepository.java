package edu.huce.store.repositories;

import edu.huce.store.models.Account;

public interface AccountRepository {
    Integer create(Account account);

    Integer update(Account account);

    Account findByUsernameAndPassword(Account account);

    Integer getCountByUsername(String username);

    Account findById(Integer id);

}

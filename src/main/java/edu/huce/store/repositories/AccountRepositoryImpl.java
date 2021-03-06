package edu.huce.store.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.huce.store.exceptions.EtBadRequestException;
import edu.huce.store.exceptions.EtResourceNotFoundException;
import edu.huce.store.models.Account;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Account account) {
        try {
            String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(10));
            String SQL_CREATE = "INSERT Accounts( username, password, roleId, note) VALUES( ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getUsername());
                ps.setString(2, hashedPassword);
                ps.setInt(3, account.getRoleId());
                ps.setString(4, account.getNote());
                return ps;
            }, keyHolder);

            // khi insert id tự tăng .keyholder lấy id tự tạo
            return Integer.parseInt(keyHolder.getKeys().get("GENERATED_KEYS").toString());
        } catch (Exception e) {
            throw new EtBadRequestException("Invalid details. Failed to create account :" + e.getMessage());
        }
    }

    @Override
    public Account findByUsernameAndPassword(Account account) {
        try {
            String SQL_FIND_BY_USERNAME = "SELECT id, username, password, roleId, note FROM Accounts WHERE destroy = 0 AND username = '"
                    + account.getUsername() + "'";
            // BeanPropertyRowMapper chuyển đổi định dạng bảng kết quả của câu lệnh query
            // sang class
            List<Account> result = jdbcTemplate.query(SQL_FIND_BY_USERNAME,
                    BeanPropertyRowMapper.newInstance(Account.class));

            if (result.size() == 0) {
                throw new EtResourceNotFoundException("Account not found");
            }
            if (!BCrypt.checkpw(account.getPassword(), result.get(0).getPassword()))
                throw new EtBadRequestException("Invalid email/password");
            return result.get(0);
        } catch (EmptyResultDataAccessException e) {
            throw new EtBadRequestException("Invalid email/password " + e.getMessage());
        }
    }

    @Override
    public Integer getCountByUsername(String username) {
        String SQL_COUNT_BY_USERNAME = "SELECT * FROM Accounts WHERE username = '" + username + "'";
        List<Account> account = jdbcTemplate.query(SQL_COUNT_BY_USERNAME,
                BeanPropertyRowMapper.newInstance(Account.class));
        return account.size();
    }

    @Override
    public Account findById(Integer id) {
        try {
            String SQL_FIND_BY_ID = "SELECT id, username, roleId, note FROM Accounts WHERE destroy = 0 AND id = '" + id
                    + "'";
            List<Account> account = jdbcTemplate.query(SQL_FIND_BY_ID,
                    BeanPropertyRowMapper.newInstance(Account.class));
            if (account.size() == 0) {
                throw new EtResourceNotFoundException("Not found");

            } else {

                return account.get(0);
            }
        } catch (Exception e) {
            throw new EtBadRequestException(e.getMessage());
        }

    }

    @Override
    public Integer update(Account account) {
        try {
            String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(10));
            String SQL_UPDATE = "UPDATE Accounts SET username = ?, password = ? ,roleId = ?, note = ? WHERE destroy = 0 AND id = ?";
            jdbcTemplate.update(
                    SQL_UPDATE,
                    new Object[] { account.getUsername(), hashedPassword, account.getRoleId(), account.getNote(),
                            account.getId() });
            return account.getId();
        } catch (Exception e) {
            throw new EtBadRequestException("Update failed: " + e.getMessage());
        }
    }

}

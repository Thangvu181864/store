package edu.huce.store.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.huce.store.Constants;
import edu.huce.store.models.Account;
import edu.huce.store.services.AccountService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> LoginAccount(@RequestBody Account payload) {

        Account account = accountService.validateAccount(payload);
        return new ResponseEntity<>(generateJWTToken(account), HttpStatus.OK);
    }

    @PostMapping("/accounts/register")
    public ResponseEntity<Map<String, Account>> RegisterAccount(@RequestBody Account payload) {
        Map<String, Account> map = new HashMap<>();
        Account account = accountService.registerAccount(payload);
        map.put("data", account);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Map<String, Account>> UpdateAccount(
            @PathVariable("id") Integer id,
            @RequestBody Account payload) {
        payload.setId((Integer) id);
        Map<String, Account> map = new HashMap<>();
        Account account = accountService.updateAccount(payload);
        map.put("data", account);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Map<String, Account>> findAccountById(
            @PathVariable("id") Integer id) {
        Map<String, Account> map = new HashMap<>();
        Account account = accountService.findAccount(id);
        map.put("data", account);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(Account account) {
        long timestamp = System.currentTimeMillis();
        Date expiration = new Date(timestamp + Constants.TOKEN_VALIDITY);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(expiration)
                .claim("id", account.getId())
                .claim("username", account.getUsername())
                .claim("roleId", account.getRoleId())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("id", account.getId().toString());
        map.put("username", account.getUsername());
        map.put("roleId", account.getRoleId().toString());
        map.put("expiration", String.valueOf(expiration.getTime()));
        return map;
    }
}

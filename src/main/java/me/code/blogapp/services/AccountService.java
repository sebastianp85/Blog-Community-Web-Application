package me.code.blogapp.services;

import lombok.AllArgsConstructor;
import me.code.blogapp.models.Account;
import me.code.blogapp.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public void save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    public Optional<Account> findByEmail(String email) {

        return accountRepository.findOneByEmail(email);
    }

    public Optional<Account> findByUsername(String userName) {
        return accountRepository.findOneByUserName(userName);
    }
}

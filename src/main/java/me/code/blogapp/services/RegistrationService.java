package me.code.blogapp.services;

import lombok.AllArgsConstructor;
import me.code.blogapp.models.Account;
import me.code.blogapp.models.Authority;
import me.code.blogapp.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final AccountService accountService;
    private final AuthorityRepository authorityRepository;
    private final ValidationService validationService;

    public void registerNewUser(Account account) {
        if (accountService.findByEmail(account.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (accountService.findByUsername(account.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        validationService.validateInput(account.getUserName());
        validationService.validateInput(account.getEmail());

        Authority userRole = authorityRepository.findByName("ROLE_USER")
                .orElseGet(() -> authorityRepository.save(new Authority("ROLE_USER")));
        accountService.save(account);
    }
}

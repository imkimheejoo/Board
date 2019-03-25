package com.heejoo.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/{id}")
public class ApiAccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public Account getAccount(@PathVariable Long id){
        return accountRepository.findById(id).get();
    }
}

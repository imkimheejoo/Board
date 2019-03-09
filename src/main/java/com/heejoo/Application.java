package com.heejoo;

import com.heejoo.accounts.Account;
import com.heejoo.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {

            @Autowired
            AccountRepository accountRepository;
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .accountId("user")
                        .email("user@email.com")
                        .name("유저")
                        .password("pass")
                        .joinDate(LocalDate.now())
                        .build();

                accountRepository.save(account);

            }
        };

    }

}

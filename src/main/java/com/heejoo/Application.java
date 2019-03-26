package com.heejoo;

import com.heejoo.accounts.Account;
import com.heejoo.accounts.AccountRepository;
import com.heejoo.questions.Question;
import com.heejoo.questions.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountRepository accountRepository;
            @Autowired
            QuestionRepository questionRepository;
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .accountId("user")
                        .email("user@email.com")
                        .name("유저")
                        .password("pass")
                        .joinDate(LocalDate.now())
                        .build();

                Account savedAccount = accountRepository.save(account);

                Account account2 = Account.builder()
                        .accountId("user2")
                        .email("user2@email.com")
                        .name("유저2")
                        .password("pass2")
                        .joinDate(LocalDate.now())
                        .build();

               accountRepository.save(account2);

                Question question=Question.builder()
                        .title("제목")
                        .contents("내용")
//                        .createDate(LocalDateTime.now())
                        .writer(savedAccount)
                        .countOfAnswer(0)
                        .build();

                questionRepository.save(question);

            }
        };

    }

}

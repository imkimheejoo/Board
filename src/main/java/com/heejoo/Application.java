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
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("board")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HeeJoo Board API")
                .description("Board Swagger API")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact("Niklas Heidloff")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("2.0")
                .build();
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

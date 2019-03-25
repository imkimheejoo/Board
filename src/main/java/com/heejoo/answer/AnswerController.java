package com.heejoo.answer;

import com.heejoo.accounts.Account;
import com.heejoo.accounts.HttpSessionUtils;
import com.heejoo.questions.Question;
import com.heejoo.questions.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/question/{id}/answer")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public Answer createAnswer(@PathVariable Long id, String answer, HttpSession session){
        if(!HttpSessionUtils.isLoginAccount(session)){
            return  null;
        }
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        Question question = questionRepository.findById(id).get();

        System.out.println(answer);
        System.out.println(id);

        Answer newAnswer= Answer.builder()
                .contents(answer)
                .question(question)
                .writer(loginAccount)
                .answerDate(LocalDateTime.now())
                .build();


        Answer save = answerRepository.save(newAnswer);
        System.out.println(save);
        return save;
    }

   
}

package com.heejoo.answer;

import com.heejoo.accounts.Account;
import com.heejoo.accounts.HttpSessionUtils;
import com.heejoo.questions.Question;
import com.heejoo.questions.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("question/{id}/answer")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public String createAnswer(@PathVariable Long id, String answer, HttpSession session){

        System.out.println("fffff");
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        Question question = questionRepository.findById(id).get();

        System.out.println("aaaaa");

        Answer newAnswer= Answer.builder()
                .contents(answer)
                .question(question)
                .writer(loginAccount)
                .answerDate(LocalDateTime.now())
                .build();

        answerRepository.save(newAnswer);
        System.out.println(String.format("redirect:/question/contents/%d",id));
        return String.format("redirect:/question/contents/%d",id);

    }
}

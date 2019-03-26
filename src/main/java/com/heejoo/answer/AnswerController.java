package com.heejoo.answer;

import com.heejoo.Result;
import com.heejoo.accounts.Account;
import com.heejoo.accounts.HttpSessionUtils;
import com.heejoo.questions.Question;
import com.heejoo.questions.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/question/{questionId}/answer")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public Answer createAnswer(@PathVariable Long questionId, String answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginAccount(session)) {
            return null;
        }
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        Question question = questionRepository.findById(questionId).get();

        System.out.println(answer);
        System.out.println(questionId);

        Answer newAnswer = Answer.builder()
                .contents(answer)
                .question(question)
                .writer(loginAccount)
//                .answerDate(LocalDateTime.now())
                .build();


        Answer save = answerRepository.save(newAnswer);
        question.addAnswer();
        questionRepository.save(question);
        return save;
    }

    @DeleteMapping("/{id}")
    public Result deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginAccount(session)) {
            return Result.fail("로그인이 필요합니다!");
        }
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        Answer writer = answerRepository.findById(id).get();
        if (!writer.isSameWriter(loginAccount)) {
            return Result.fail("답변을 작성한 유저만 삭제가 가능합니다!");
        }

        answerRepository.delete(writer);
        Question question=questionRepository.findById(questionId).get();
        question.deleteAnswer();
        questionRepository.save(question);
        return Result.ok();
    }


}

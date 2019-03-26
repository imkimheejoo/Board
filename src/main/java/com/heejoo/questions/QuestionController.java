package com.heejoo.questions;

import com.heejoo.Result;
import com.heejoo.accounts.Account;
import com.heejoo.accounts.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping
    public String question(Model model) {
        List<Question> questionsList = questionRepository.findAll();
        model.addAttribute("questions", questionsList);
        return "board";
    }

    @GetMapping("/write")
    public String create(HttpSession session, Model model) {
        Result valid = new Result();
        if (!HttpSessionUtils.isLoginAccount(session)) {
            valid = Result.fail("로그인이 필요합니다.");
        }
        if (!valid.isValid()) {
            model.addAttribute("errorMessage", valid.getErrorMessage());
            return "login";
        }
        return "questionForm";
    }

    @PostMapping("/write")
    public String create(Question board, HttpSession session) {

        Account writerAccount = HttpSessionUtils.getAccountFromSession(session);
        Question newBoard = Question.builder()
                .title(board.getTitle())
                .contents(board.getContents())
                .writer(writerAccount)
                .countOfAnswer(0)
                .build();

        Question save = questionRepository.save(newBoard);
        System.out.println("게시글 저장완료! " + save);
        return "redirect:/question";

    }

    @GetMapping("/contents/{id}")
    public String showContents(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).get();
        System.out.println("상세보기: " + question.toString());
        //없는 글의 상세보기를 할 때
        if (question == null) {
            return "redirect:/question";
        }

        model.addAttribute("questionData", question);
        return "show";
    }

    //접근에 대한 조건 메소드
    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginAccount(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);

        if (!question.sampleWriterCorrect(loginAccount)) {
            return Result.fail("이 글의 글쓴이만 수정,삭제가 가능합니다.");
        }
        return Result.ok();
    }

    //글 수정
    @GetMapping("/{id}")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result valid = valid(session, question);

        if (!valid.isValid()) {
            model.addAttribute("errorMessage", valid.getErrorMessage());
            return "login";
        }
        model.addAttribute("updateData", question);
        return "updateQuestionForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question update) {
        Question updateQuestion = questionRepository.findById(id).get();
        updateQuestion.setTitle(update.getTitle());
        updateQuestion.setContents(update.getContents());

        questionRepository.save(updateQuestion);
        return "redirect:/question";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Question question = questionRepository.findById(id).get();
        Result valid = valid(session, question);
        if (!valid.isValid()) {
            model.addAttribute("errorMessage", valid.getErrorMessage());
            return "login";
        }
        questionRepository.deleteById(id);
        return "redirect:/question";
    }

}

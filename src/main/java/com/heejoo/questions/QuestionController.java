package com.heejoo.questions;

import com.heejoo.accounts.Account;
import com.heejoo.accounts.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    public String create(HttpSession session) {
        if (session == null) {
            System.out.println("로그인없이 글을 쓸 수 없습니다.");
            return "redirect:/user/login";
        }
        return "questionForm";
    }

    @PostMapping("/write")
    public String create(Question board, HttpSession session) {
        System.out.println(board + " 전달 완료");
        Account writerAccount = HttpSessionUtils.getAccountFromSession(session);
        Question newBoard = Question.builder()
                .title(board.getTitle())
                .contents(board.getContents())
                .createTime(LocalDateTime.now())
                .writer(writerAccount)
                .build();
        Question save = questionRepository.save(newBoard);
        System.out.println("게시글 저장완료! " + save);
        return "redirect:/question";

    }

    @GetMapping("/contents/{id}")
    public String showContents(@PathVariable  Long id,Model model){
        Question question = questionRepository.findById(id).get();
        System.out.println("상세보기: "+question.toString());
        //없는 글의 상세보기를 할 때
        if(question == null){
            return "redirect:/question";
        }

        model.addAttribute("questionData",question);
        return "show";
    }
    //글 수정
    @GetMapping("/{id}")
    public String updateForm(@PathVariable Long id,Model model,HttpSession session){
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        Question question=questionRepository.findById(id).get();

        if(!question.sampleWriterCorrect(loginAccount)){
            System.out.println("이글의 글쓴이가 아니므로 수정이 불가 합니다.");
            return "redirect:/question";
        }
        model.addAttribute("updateData",question);
        return "updateQuestionForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question update){
        Question updateQuestion = questionRepository.findById(id).get();
        updateQuestion.setTitle(update.getTitle());
        updateQuestion.setContents(update.getContents());


        questionRepository.save(updateQuestion);
        return "redirect:/question";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,HttpSession session){
        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        Question question=questionRepository.findById(id).get();

        if(!question.sampleWriterCorrect(loginAccount)){
            System.out.println("이글의 글쓴이가 아니므로 삭제가 불가 합니다.");
            return "redirect:/question";
        }
        questionRepository.deleteById(id);
        return "redirect:/question";
    }

}

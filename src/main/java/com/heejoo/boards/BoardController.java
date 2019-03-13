package com.heejoo.boards;

import com.heejoo.accounts.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardRepository boardRepository;
//
//    @GetMapping
//    public String board(String title,String contents, HttpSession session){
//
//        if(!)
//    List<Board> boardList=boardRepository.findAll();
//        model.addAttribute("boards",boardList);
//        return "boardList";
//}
    @GetMapping("/write")
    public String boardForm(HttpSession session){
        if(session == null){
            System.out.println("로그인없이 글을 쓸 수 없습니다.");
            return "redirect:/user/login";
        }
        return "boardForm";
    }
    @PostMapping("/write")
    public String boardForm(Board board, HttpSession session){
        System.out.println(board+" 전달 완료");
        Account writerAccount = (Account)session.getAttribute("account");
        Board newBoard = Board.builder()
                .title(board.getTitle())
                .contents(board.getContents())
                .loadTime(LocalDateTime.now())
                .build();
        Board save = boardRepository.save(newBoard);
        System.out.println("게시글 저장완료! "+save);
        return "redirect:/board";

    }



}

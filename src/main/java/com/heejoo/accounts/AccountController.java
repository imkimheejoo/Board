package com.heejoo.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("user")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/list")
    public String userList(Model model) {
        System.out.println("넘어갔니?");
        List<Account> accounts = accountRepository.findAll();
        System.out.println(accounts);
        model.addAttribute("accounts",accounts);
        return "userList";
    }


    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String accountId, @RequestParam String password, HttpSession session) {
        Account loginAccount = accountRepository.findByAccountId(accountId);

        if (loginAccount == null) {     // 아이디가 존재하지않습니다.
            throw new IllegalStateException("Login Fail!");
        }
        if (!loginAccount.matchPassword(password)) {
            throw new IllegalStateException("Password is not Right!");
        }
        session.setAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY, loginAccount);
        System.out.println(accountId+"님 환영합니다!");
        return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){

        if(HttpSessionUtils.isLoginAccount(session))
        {
            session.removeAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY);
        }

        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }

    @PostMapping("/join")
    public String join(Account joinAccount) {

        Account newAccount = Account.builder()
                .accountId(joinAccount.getAccountId())
                .password(joinAccount.getPassword())
                .name(joinAccount.getName())
                .email(joinAccount.getEmail())
                .joinDate(LocalDate.now())
                .build();

        Account save = accountRepository.save(newAccount);

        System.out.println(save);

        return "redirect:/user/list";
    }


    @GetMapping("/{id}")
    public String updateForm(@PathVariable Long id, HttpSession session,Model model) {
        System.out.println("수정시작하려고 하는데,,");
        //로그인 확인
        if(!HttpSessionUtils.isLoginAccount(session))  {
            // 로그인을 안했을때 login 페이지로
            System.out.println("로그인이 필요합니다.");
            return "login";
        }

        Account loginAccount = HttpSessionUtils.getAccountFromSession(session);
        if(!loginAccount.matchId(id)) {
            System.out.println("다른 사용자의 정보는 수정할 수 없습니다.");
            return "index";
        }

        //수정이 될 수 있는 케이스에 수정하고싶은 유저정보 저장
        Account updateAccount = accountRepository.findById(id).get();
        model.addAttribute("accountData",updateAccount);
        return "updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Account updateAccount,HttpSession session) {
        System.out.println("전달 된 update: "+id+ " "+updateAccount);

        Account account = accountRepository.findById(id).get();

        account.setAccountId(updateAccount.getAccountId());
        account.setPassword(updateAccount.getPassword());
        account.setEmail(updateAccount.getEmail());
        account.setName(updateAccount.getName());

        Account saveAccount = accountRepository.save(account);
        System.out.println("바껴랏:"+saveAccount);
        session.removeAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY);
        session.setAttribute(HttpSessionUtils.ACCOUNT_SESSION_KEY,saveAccount);

        return "redirect:/user/list";
    }

}

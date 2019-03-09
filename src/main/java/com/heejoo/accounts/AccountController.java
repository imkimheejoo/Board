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
    @ResponseBody
    public List<Account> userList() {
        List<Account> accounts = accountRepository.findAll();

        return accounts;
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
        } else if (!password.equals(loginAccount.getPassword())) {
            throw new IllegalStateException("Login Fail!");
        }
        session.setAttribute("account", loginAccount);
        return "index";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }

    @PostMapping("/join")
    public String join(Account account, Model model, HttpSession session) {

        Account newAccount = Account.builder()
                .accountId(account.getAccountId())
                .password(account.getPassword())
                .name(account.getName())
                .name(account.getEmail())
                .joinDate(LocalDate.now())
                .build();

        accountRepository.save(newAccount);

        return "index";
    }



    @GetMapping("/{id}")
    public String updateForm(@PathVariable Long id, HttpSession session,Model model) {
        Account loginAccount = (Account)session.getAttribute("account");
        Account updateAccount = accountRepository.findById(id).get();

        if(loginAccount==null)      // 로그인을 안했을때 login 페이지로
            return "login";

        if(updateAccount==null)     // 수정할 id가 존재하지않습니다.
            return "index";

        if(loginAccount.getId()!=updateAccount.getId()) {
            return "index";
        }

        model.addAttribute("accountData",updateAccount);

        return "updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Account updateAccount,HttpSession session) {

        Account account = accountRepository.findById(id).get();

        account.setPassword(updateAccount.getPassword());
        account.setEmail(updateAccount.getEmail());
        account.setName(updateAccount.getName());

        Account saveAccount = accountRepository.save(account);

        session.removeAttribute("account");
        session.setAttribute("account",saveAccount);

        return "accountList";
    }
}

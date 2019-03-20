package com.heejoo.accounts;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
   public static final String ACCOUNT_SESSION_KEY="sessionAccount";

   //로그인 유무 메소드
    public static boolean isLoginAccount(HttpSession session){
        Object sessionAccount =session.getAttribute(ACCOUNT_SESSION_KEY);
        if(sessionAccount == null){
            return false;
        }
        return true;
    }

    //로그인 사용자일 경우 유저객체를 사용
    public static Account getAccountFromSession(HttpSession session){
        if(!isLoginAccount(session)){       // 로그인상태가 아sla
            return null;
        }
        return (Account)session.getAttribute(ACCOUNT_SESSION_KEY);
    }

}

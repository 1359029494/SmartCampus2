package com.qilu.intercepter;

import com.qilu.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user =(User) request.getSession().getAttribute("user");
        if(Objects.nonNull(user)){
            return true;
        }else{
                response.sendRedirect("/login.html");
            return false;
        }

    }
}

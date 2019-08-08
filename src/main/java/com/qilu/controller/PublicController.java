package com.qilu.controller;

import com.qilu.dto.UserDTO;
import com.qilu.po.User;
import com.qilu.service.UserService;
import com.qilu.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/pub")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public JsonData login(@RequestBody UserDTO userDTO, HttpServletRequest request) throws Exception {
        User user = userService.LoginService(userDTO);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return JsonData.buildSuccess(user,"登陆成功");
    }
}

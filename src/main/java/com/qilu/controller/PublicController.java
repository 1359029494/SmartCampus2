package com.qilu.controller;

import com.qilu.dto.UserDTO;
import com.qilu.po.Student;
import com.qilu.po.Teacher;
import com.qilu.po.User;
import com.qilu.service.UserService;
import com.qilu.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pub")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public JsonData login(UserDTO userDTO, HttpServletRequest request) throws Exception {
        User user = userService.LoginService(userDTO);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return JsonData.buildSuccess(user,"登陆成功");
    }

    @GetMapping("get_user_info")
    public JsonData getUserInfo(HttpServletRequest request)throws Exception{

        User user=(User)request.getSession().getAttribute("user");

        Map<String,Object> map=new HashMap<>();
        if(0==(user.getRole())){
            Student student = userService.getStudent(user.getUsername());
            map.put("name",student.getName());
            map.put("phone",student.getPhone());
        }else if(1==(user.getRole())){
            Teacher teacher = userService.getTeacher(user.getUsername());
            map.put("name",teacher.getName());
            map.put("phone",teacher.getPhone());
        }

        return JsonData.buildSuccess(map);
    }


}

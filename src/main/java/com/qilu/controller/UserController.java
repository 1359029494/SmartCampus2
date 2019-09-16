package com.qilu.controller;

import com.qilu.dto.UserDTO;
import com.qilu.po.User;
import com.qilu.service.UserService;
import com.qilu.utils.JsonData;
import com.qilu.utils.ResponseBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    //修改密码
    /*@PostMapping("modify_password")
    public JsonData modifyPassword(@RequestBody UserDTO userDTO) throws Exception {

        userService.modifyPasswordService(userDTO);

        return JsonData.buildSuccess("密码修改成功");
    }*/
    //发送短信验证码
    @GetMapping("send_code")
    public JsonData sendCode(@RequestParam("phone") String phone, HttpServletRequest request) throws Exception {
        //User user=(User)request.getSession().getAttribute("user");
        ResponseBean json = userService.getValiCode(phone);
        return JsonData.buildSuccess(json);
    }
    //并且验证验证码是否正确以及过期
    @GetMapping("check_code")
    public JsonData checkCode(@Param("code") String code,@Param("password")String password, HttpServletRequest request) throws Exception {
        User user=(User)request.getSession().getAttribute("user");
        userService.checkPhoneCodeService(code, password,user);
        return JsonData.buildSuccess("密码修改成功");
    }

    @PostMapping("modify_password")
    public JsonData modifyPasswordByPhone(@RequestParam("phone")String phone,
                                          @RequestParam("valiCode")String valiCode,
                                          @RequestParam("password")String password){

        userService.modifyPasswordByPhone(valiCode,phone,password);
        return JsonData.buildSuccess("密码修改成功");
    }
}

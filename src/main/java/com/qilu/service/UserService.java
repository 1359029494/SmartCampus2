package com.qilu.service;

import com.qilu.dto.UserDTO;
import com.qilu.po.*;
import com.qilu.utils.ResponseBean;

import java.io.UnsupportedEncodingException;


public interface UserService {

    //验证登录
    User LoginService(UserDTO userDTO) throws Exception;
    //修改密码 已作废
    void modifyPasswordService(UserDTO userDTO) throws Exception;
    //验证码业务
    ResponseBean messageSendCodeService(String phone, User user) throws Exception;
    //检验验证码
    void checkPhoneCodeService(String code,String password ,User user)throws Exception;
    //发送验证码
    ResponseBean getValiCode(String phone) throws Exception;
    //修改密码----
    void modifyPasswordByPhone(String code, String phone, String password);

    Teacher getTeacher(String teaNo);
    Student getStudent(String stuNo);
}

package com.qilu.service;

import com.qilu.dto.UserDTO;
import com.qilu.po.*;
import com.qilu.utils.ResponseBean;


public interface UserService {

    //验证登录
    User LoginService(UserDTO userDTO) throws Exception;
    //修改密码
    void modifyPasswordService(UserDTO userDTO) throws Exception;
    //验证码业务
    ResponseBean messageSendCodeService(String phone, User user) throws Exception;
    //检验验证码
    void checkPhoneCodeService(String code,String password ,User user)throws Exception;

}

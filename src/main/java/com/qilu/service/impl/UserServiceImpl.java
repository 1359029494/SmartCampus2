package com.qilu.service.impl;

import com.google.gson.Gson;
import com.qilu.dto.UserDTO;
import com.qilu.exception.CustomException;
import com.qilu.mapper.*;
import com.qilu.po.*;
import com.qilu.service.UserService;
import com.qilu.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private MaintainerMapper maintainerMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private EvaluateMapper evaluateMapper;

    @Autowired
    private Gson gson;

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public User LoginService(UserDTO userDTO) throws Exception {
        if(Objects.isNull(userDTO.getUsername())||Objects.isNull(userDTO.getPassword())){
            throw new CustomException(-1,"登录参数不正确");
        }
        User user = userMapper.selectUserByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
        if(Objects.isNull(user)){
            throw new CustomException(-2,"用户名或密码不正确");
        }
        int role=user.getRole();
        if(role==1){
            //学生
            Student student = studentMapper.selectByStuNo(user.getUsername());
            user.setStudent(student);
        }else if(role==2){
            //老师
            Teacher teacher = teacherMapper.selectByTeaNo(user.getUsername());
            user.setTeacher(teacher);
        }else if(role==3){
            //维修工
            Maintainer maintainer=maintainerMapper.selectByMaiNo(user.getUsername());
            user.setMaintainer(maintainer);
        }else{
            //管理员
        }


        return user;
    }

    @Override
    public void modifyPasswordService(UserDTO userDTO) throws Exception {
        User user = userMapper.selectUserByUsernameAndPassword(userDTO.getUsername(), userDTO.getOldPassword());
        if(Objects.isNull(user)){
            throw new CustomException(-2, "原密码输入不正确");
        }
        userMapper.modifyPasswordByUsername(userDTO.getUsername(), userDTO.getPassword());
    }

    @Override
    public ResponseBean messageSendCodeService(String phone, User user)throws Exception {

        String code= GetCodeUtils.randomCode();
        StringBuilder sb = new StringBuilder();
        sb.append("accountSid").append("=").append(Config.accountId);
        sb.append("&to").append("=").append(phone);
        sb.append("&param").append("=").append(URLEncoder.encode(code,"UTF-8"));
        sb.append("&templateid").append("=").append("1326");
        String body = sb.toString() + HttpUtil.createCommonParam(Config.accountId, Config.authToken);
        String result = HttpUtil.post(Config.baseUrl, body);
        ResponseBean responseBean = gson.fromJson(result, ResponseBean.class);
        if(Objects.equals(responseBean.getRespCode(), "0000")){
            redisUtils.set(user.getUsername(),code , 60l);
            responseBean.setCode(code);
        }
        return responseBean;
    }

    @Override
    public void checkPhoneCodeService(String code,String password,User user) {
        String redisCode =(String) redisUtils.get(user.getUsername());
        if(Objects.isNull(redisCode)){
            throw new CustomException(-3, "验证码已过期");
        }
        if(!Objects.equals(code, redisCode)){
            throw new CustomException(-4, "验证码不正确");
        }
        userMapper.modifyPasswordByUsername(user.getUsername(), password);
    }


}

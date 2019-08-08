package com.qilu.mapper;

import com.qilu.po.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    //用户登录
    @Select("select * from t_user where username=#{username} and password=#{password}")
    User selectUserByUsernameAndPassword(@Param("username") String username,@Param("password")String password);

    //修改用户密码
    @Update("update t_user set password=#{password} where username=#{username}")
    int modifyPasswordByUsername(@Param("username")String username,@Param("password")String password);
}

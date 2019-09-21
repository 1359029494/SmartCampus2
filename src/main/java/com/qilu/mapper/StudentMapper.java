package com.qilu.mapper;

import com.qilu.po.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StudentMapper {

    //根据学号查询学生
    @Select("select * from t_student where stu_no=#{stuNo}")
    Student selectByStuNo(@Param("stuNo") String stuNo);

    //根据id查询
    @Select("select * from t_student where id=#{id}")
    Student selectById(@Param("id") int id);

    //更新头像
    @Update("update t_student set photo=#{photo} where id=#{id}")
    public int uploadPhoto(String photo, int id);
}

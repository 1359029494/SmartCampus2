package com.qilu.mapper;

import com.qilu.po.Student;
import com.qilu.po.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {
    //根据老师编号查询老师
    @Select("select * from t_teacher where tea_no=#{teaNo}")
    Teacher selectByTeaNo(@Param("teaNo") String teaNo);

    //根据id查询
    @Select("select * from t_teacher where id=#{id}")
    Teacher selectById(@Param("id") int id);

}

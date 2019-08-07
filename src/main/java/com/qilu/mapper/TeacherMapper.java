package com.qilu.mapper;

import com.qilu.po.Teacher;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {
    /**
     * 功能描述:学生查看个人资料
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Select("select * from t_teacher where id=#{id}")
    public Teacher findById(int id);
}

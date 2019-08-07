package com.qilu.mapper;

import com.qilu.po.Student;
import org.apache.ibatis.annotations.Select;

public interface StudentMapper {
    /**
     * 功能描述:学生查看个人资料
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Select("select * from t_student where id=#{id}")
    public Student findById(int id);
}

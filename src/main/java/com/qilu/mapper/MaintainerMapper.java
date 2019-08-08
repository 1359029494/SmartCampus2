package com.qilu.mapper;

import com.qilu.po.Maintainer;
import com.qilu.po.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MaintainerMapper {

    //根据维修工编号查询维修工
    @Select("select * from t_maintainer where mai_no=#{maiNo}")
    Maintainer selectByMaiNo(@Param("maiNo") String maiNo);

    //根据id查询
    @Select("select * from t_maintainer where id=#{id}")
    Maintainer selectById(@Param("id") int id);
}

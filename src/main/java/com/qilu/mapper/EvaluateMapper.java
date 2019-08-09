package com.qilu.mapper;

import com.qilu.po.Evaluate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface EvaluateMapper {
    /**
     * 功能描述:师生通过评价id查询评价
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Select("select * from t_evaluate where id=#{id}")
    public Evaluate findByIdAboutStudent(int id);

    /**
     * 功能描述:师生评价
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Insert("insert into t_evaluate values(default,#{role},#{userId},#{content},#{star})")
    public int insEvaluate(Evaluate e);
}

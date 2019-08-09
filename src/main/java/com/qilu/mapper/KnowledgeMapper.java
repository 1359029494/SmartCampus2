package com.qilu.mapper;

import com.qilu.po.Knowledge;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KnowledgeMapper {
    /**
     * 功能描述:查询所有校园小知识
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Select("select * from t_knowledge")
    public List<Knowledge> findAll();

    /**
     * 功能描述:通过id查询小知识
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Select("select * from t_knowledge where id=#{id}")
    public Knowledge findByid(int id);

}

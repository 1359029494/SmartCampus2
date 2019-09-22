package com.qilu.service;

import com.qilu.po.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface StudentAndTeacherService {
    /**
     * 功能描述:查询报修进度
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public List<Repair> findProgress(HttpSession session);

    /**
     * 功能描述:校园随手拍
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public int repirSchool(HttpSession session, String type, String local, String describe, HttpServletRequest request);

    /**
     * 功能描述:通过评价表id查询评价
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public Evaluate findOneEvaByid4Student(int id);

    /**
     * 功能描述:学生查看自己的信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public Student findMyInfo4Student(HttpSession session);

    /**
     * 功能描述:老师查看自己的信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public Teacher findMyInfo4Teacher(HttpSession session);

    /**
     * 功能描述:师生对报修进行评价
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public int insEvaluate(HttpSession session, String name, String content, int star);

    /**
     * 功能描述:校园安全小知识
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public List<Knowledge> findAllKnowledge();

    /**
     * 功能描述:根据id查询校园安全小知识的文章
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */

    public Knowledge findOneKnowledgeByid(int id);
    
    /**
     * 功能描述:通过主键id查询报修信息
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    public Repair findRepairById(int id);
    
    /**
     * 功能描述:上传头像
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    public String uploadPhoto(HttpServletRequest request, HttpSession session);
    
    /**
     * 功能描述:更新订单为已支付
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    public int updOrder2HasPay(int repairId);

    public void doRepair(Repair repair);
    
    /**
     * 功能描述:查询自己所有的缴费
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    public List<Repair> findMyFine(HttpSession session);
}

package com.qilu.Service;

import com.qilu.po.Evaluate;
import com.qilu.po.Repair;
import com.qilu.po.Student;
import com.qilu.po.Teacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    /**
     * 功能描述:查询报修进度
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public List<Repair> findProgress(int roleType, int id);

    /**
     * 功能描述:校园随手拍
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public int repirSchool(HttpSession session, String type, String local, HttpServletRequest request);

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
}

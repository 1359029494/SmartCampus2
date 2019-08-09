package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.po.User;
import com.qilu.service.StudentAndTeacherService;
import com.qilu.po.Repair;
import com.qilu.utils.JsonData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stuAndtea")
public class StudentAndTeacherController {
    @Resource
    private StudentAndTeacherService stuService;

    /**
     * 功能描述:查询保修进度
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */

    @GetMapping("findProgress")
    public PageInfo<Repair> findProgress(HttpSession session, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 2);
        List<Repair> list = stuService.findProgress(session);
        System.out.println(list.size());
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }

    /**
     * 功能描述:通过id查询评价
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @GetMapping("findEvaluateById")
    public JsonData findEvaluateById(int id){
        return JsonData.buildSuccess(stuService.findOneEvaByid4Student(id));
    }

    /**
     * 功能描述:对报修进行评价
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @PostMapping("insEvaluate")
    public JsonData insEvaluate(HttpSession session, String content, int star){
        int flag = stuService.insEvaluate(session, content, star);
        if (flag > 0){
            return JsonData.buildSuccess("评价成功");
        }else {
            return JsonData.buildError("评价失败");
        }
    }

    /**
     * 功能描述:校园随手拍
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @PostMapping("insSchoolPhoto")
    public JsonData insSchoolPhoto(HttpSession session, String type, String local, HttpServletRequest request){
        int flag = stuService.repirSchool(session, type, local, request);
        if (flag > 0){
            return JsonData.buildSuccess("提交成功");
        }else {
            return JsonData.buildError("提交失败");
        }
    }

    /**
     * 功能描述:师生查看自己的信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @GetMapping("findMyInfo4StudentAndTeacher")
    public JsonData findMyInfo4Student(HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        JsonData success = null;
        if (user.getRole() == 1){
            success = JsonData.buildSuccess(stuService.findMyInfo4Student(session));
            System.out.println(success);
        }
        if (user.getRole() == 2){
            success = JsonData.buildSuccess(stuService.findMyInfo4Teacher(session));
        }
        return success;
    }
}

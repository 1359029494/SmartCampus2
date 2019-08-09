package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.Service.StudentAndTeacherService;
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
    public JsonData findProgress(HttpSession session, @RequestParam(defaultValue = "1") int pageNum){
        PageHelper.startPage(pageNum, 2);
        List<Repair> list = stuService.findProgress(session);
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        return JsonData.buildSuccess(pageInfo);
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
     * 功能描述:学生查看自己的信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @GetMapping("findMyInfo4Student")
    public JsonData findMyInfo4Student(HttpSession session){
        return JsonData.buildSuccess(stuService.findMyInfo4Student(session));
    }

    /**
     * 功能描述:学生查看自己的信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @GetMapping("findMyInfo4Teacher")
    public JsonData findMyInfo4Teacher(HttpSession session){
        return JsonData.buildSuccess(stuService.findMyInfo4Teacher(session));
    }
}

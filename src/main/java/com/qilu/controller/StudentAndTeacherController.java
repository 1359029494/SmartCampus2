package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.mapper.RepairMapper;
import com.qilu.po.Knowledge;
import com.qilu.po.User;
import com.qilu.service.StudentAndTeacherService;
import com.qilu.po.Repair;
import com.qilu.utils.JsonData;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stuAndtea")
public class StudentAndTeacherController {
    @Resource
    private StudentAndTeacherService stuService;
    @Resource
    private RepairMapper repairMapper;

    /**
     * 功能描述:查询保修进度
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */

    @GetMapping("findProgress")
    public JsonData findProgress(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Repair> list = stuService.findProgress(session);
        return JsonData.buildSuccess(list);
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
    public JsonData insEvaluate(HttpSession session, String name, String content, int star){
        int flag = stuService.insEvaluate(session, name, content, star);
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
    public JsonData insSchoolPhoto(HttpSession session, String type, String location, String describe, HttpServletRequest request){
        int flag = stuService.repirSchool(session, type, location, describe, request);
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

    /**
     * 功能描述:查看校园安全小知识
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @GetMapping("findAllKnowledge")
    public JsonData findAllKnowledge(@RequestParam(defaultValue = "1") int pageNum){
        PageHelper.startPage(pageNum, 2);
        List<Knowledge> list = stuService.findAllKnowledge();
        PageInfo<Knowledge> pageInfo = new PageInfo<>(list);
        return JsonData.buildSuccess(pageInfo);
    }

    /**
     * 功能描述:查看校园安全小知识通过id
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @GetMapping("findOneKnowledgeByid")
    public JsonData findOneKnowledgeByid(int id){
        return JsonData.buildSuccess(stuService.findOneKnowledgeByid(id), 1);
    }
    
    /**
     * 功能描述:查询是否对该订单评论过
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    @GetMapping("isHasEvaluate")
    public JsonData isHasEvaluate(int id){
        return JsonData.buildSuccess(stuService.findRepairById(id));
    }
    
    /**
     * 功能描述:上传头像
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    @PostMapping("uploadPhoto")
    public JsonData uploadPhoto(HttpServletRequest request, HttpSession session){
        return JsonData.buildSuccess("success", stuService.uploadPhoto(request, session));
    }
    
    /**
     * 功能描述:更新订单为已支付
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    public JsonData updateOrder(int repairId){
        int flag = stuService.updOrder2HasPay(repairId);
        if (flag > 0){
            return JsonData.buildSuccess("success", 1);
        }else {
            return JsonData.buildError("success", 0);
        }
    }

    @PostMapping("do_repair")
    public JsonData doRepair(@RequestParam("name")String name,
                             @RequestParam("phone")String phone,
                             @RequestParam("local")String local,
                             @RequestParam("type")String type,
                             @RequestParam("remark")String remark,
                             HttpServletRequest request){

        String allFileNames = "";//需要存入数据库的路径
        String path = "";
        User user = (User) request.getSession().getAttribute("user");
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        // 获取文件名集合放入迭代器
        Iterator<String> files = mRequest.getFileNames();
        while (files.hasNext()) {
            MultipartFile mFile = mRequest.getFile(files.next());

            byte[] bytes = null;
            try {
                bytes = mFile.getBytes();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            File serverFile = null;
            

            if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")) {
                // 文件夹是否存在，不存在就创建
                File dir = null;
                if (user.getRole() == 1){
                    dir = new File("G:/student/repair/" + user.getStudent().getStuNo());
                    path = "G:/student/repair/" + user.getStudent().getStuNo();
                }
                if (user.getRole() == 2){
                    new File("G:/teacher/repair/" + user.getTeacher().getTeaNo());
                    path = "G:/teacher/repair/" + user.getTeacher().getTeaNo();
                }
                        
                if (!dir.exists())
                    dir.mkdirs();

                String filename1 = mFile.getOriginalFilename();
                String fileExtension = filename1.substring(filename1.lastIndexOf(".")+1);

                // 生成UUID样式的文件名
                String filename = java.util.UUID.randomUUID().toString() + "." + fileExtension;
                
                if (user.getRole() == 1){
                    allFileNames = "G:/student/repair/" + user.getStudent().getStuNo() + "/" + filename;
                }
                if (user.getRole() == 2){
                    allFileNames = "G:/teacher/repair/" + user.getTeacher().getTeaNo() + "/" + filename;
                }
                // 文件全名
                path = path + "/" + filename + ";";
                // 保存图片
                serverFile = new File(allFileNames);
            }else {
                // 文件夹是否存在，不存在就创建
                File dir = null;
                if (user.getRole() == 1){
                    dir = new File("/usr/local/static/student/repair"  + user.getStudent().getStuNo());
                    path = "/usr/local/static/student/repair"  + user.getStudent().getStuNo();
                }
                if (user.getRole() == 2){
                    new File("/usr/local/static/student/repair"  + user.getTeacher().getTeaNo());
                    path = "/usr/local/static/teacher/repair"  + user.getTeacher().getTeaNo();
                }

                if (!dir.exists())
                    dir.mkdirs();

                String filename1 = mFile.getOriginalFilename();
                String fileExtension = filename1.substring(filename1.lastIndexOf(".")+1);

                // 生成UUID样式的文件名
                String filename = java.util.UUID.randomUUID().toString() + "." + fileExtension;

                if (user.getRole() == 1){
                    allFileNames = "/usr/local/static/student/repair" + user.getStudent().getStuNo() + "/" + filename;
                }
                if (user.getRole() == 2){
                    allFileNames = "/usr/local/static/teacher/repair"+ user.getTeacher().getTeaNo() + "/" + filename;
                }
                // 文件全名
                path = path + "/" + filename + ";";
                // 保存图片
                serverFile = new File(allFileNames);
            }
            
            
            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            }catch (Exception e) {
                // TODO: handle exception
            }
        }

        Repair repair=new Repair();
        repair.setPhone(phone);
        repair.setType(type);
        repair.setLocal(local);
        repair.setPhoto(path);
        repair.setRepairDate(new Date());
        repair.setRepairStatus(0);
        repair.setRemarks(remark);
        repair.setRole(user.getRole());
        repair.setUserId(user.getId());


        stuService.doRepair(repair);


        return JsonData.buildSuccess();
    }
}

package com.qilu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.dto.UserDTO;
import com.qilu.exception.CustomException;
import com.qilu.po.User;
import com.qilu.response.RepairResp;
import com.qilu.service.AdminService;
import com.qilu.service.UserService;
import com.qilu.utils.ExclUtils;
import com.qilu.utils.JsonData;
import com.qilu.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author 孙连辉
 * @version 1.0
 * @date 2019/9/15 12:22
 */
@RestController
@RequestMapping("/admin/api")
@Slf4j
@Api("管理员")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @ApiOperation("管理员登录")
    @PostMapping("login")
    public JsonData adminLogin(UserDTO userDTO, HttpServletRequest request) throws Exception {

        User user = userService.LoginService(userDTO);
        request.getSession().setAttribute("user",user);
        return JsonData.buildSuccess(user);
    }

    //文件上传excl
    @ApiOperation("管理员上传excl，添加学生")
    @PostMapping("upload_excl")
    public JsonData uploadExcl(@RequestParam("excl") MultipartFile file) throws Exception {
        int sum = adminService.insertStudent(file);
        return JsonData.buildSuccess(sum);
    }

    @ApiOperation("获取所有的保修信息")
    @GetMapping("getAllRepair")
    public JsonData getAllRepair(@RequestParam(value = "pageSize",defaultValue = "1") Integer pageSize){
        Page<RepairResp> helps = PageHelper.startPage(pageSize, PageUtils.PAGE_SIZE);
        List<RepairResp> allRepair = adminService.getAllRepair();
        PageInfo<RepairResp> info =new PageInfo<>(allRepair);
        return JsonData.buildSuccess(info);
    }


}

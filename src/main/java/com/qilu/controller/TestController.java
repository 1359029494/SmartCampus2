package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.mapper.DeptMapper;
import com.qilu.po.Dept;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private DeptMapper deptMapper;

    @RequestMapping("findAll")
    public PageInfo<Dept> findAll(){
        PageHelper.startPage(1, 2);
        PageInfo<Dept> pageInfo = new PageInfo<>(deptMapper.findAll());
        return pageInfo;
    }
}

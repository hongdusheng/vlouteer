package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.DisciplineCategory;
import com.itcao.volunteer_back_system.pojo.Major;
import com.itcao.volunteer_back_system.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业管理控制器
 */
@RestController
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    /**
     * 分页获取专业列表，支持按照多种条件筛选
     * 
     * @param pageNum    当前页码，从1开始
     * @param pageSize   每页条数
     * @param majorCode  专业代码（可选，精确查询）
     * @param majorName  专业名称（可选，模糊查询）
     * @param schoolCode 院校代码（可选，精确查询）
     * @param schoolName 院校名称（可选，模糊查询）
     * @return 专业分页列表
     */
    @GetMapping("/list")
    public Result<PageResult<Major>> getMajorPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "majorCode", required = false) String majorCode,
            @RequestParam(value = "majorName", required = false) String majorName,
            @RequestParam(value = "schoolCode", required = false) String schoolCode,
            @RequestParam(value = "schoolName", required = false) String schoolName) {
        return majorService.getMajorPage(majorCode, majorName, schoolCode, schoolName, pageNum, pageSize);
    }

    /**
     * 获取指定ID的专业详细信息
     * 
     * @param id 专业ID
     * @return 专业详情
     */
    @GetMapping("/detail")
    public Result<Major> getMajorDetail(@RequestParam("id") String id) {
        return majorService.getMajorDetail(id);
    }

    /**
     * 根据专业代码查询专业信息
     * 
     * @param code 专业代码
     * @return 专业信息
     */
    @GetMapping("/getByCode")
    public Result<Major> getMajorByCode(@RequestParam("code") String code) {
        return majorService.getMajorByCode(code);
    }

    /**
     * 新增或更新专业信息，有id时为更新，无id时为新增
     * 
     * @param major 专业信息
     * @return 操作结果
     */
    @PostMapping("/save")
    public Result<Void> saveMajor(@RequestBody Major major) {
        return majorService.saveMajor(major);
    }

    /**
     * 删除指定ID的专业，支持批量删除，多个ID用逗号分隔
     * 
     * @param ids 专业ID列表，逗号分隔
     * @return 操作结果
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Void> deleteMajor(@PathVariable("ids") String ids) {
        return majorService.deleteMajor(ids);
    }

    /**
     * 获取所有专业分类列表，用于下拉选择
     * 
     * @return 专业分类列表
     */
    @GetMapping("/categories")
    public Result<List<DisciplineCategory>> getDisciplineCategories() {
        return majorService.getDisciplineCategories();
    }
}
package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.pojo.University;
import com.itcao.volunteer_back_system.pojo.UniversityType;
import com.itcao.volunteer_back_system.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    /**
     * 查询所有大学列表（分页）
     * 
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 大学分页列表
     */
    @GetMapping
    public Result<PageResult<University>> getAllUniversities(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return universityService.getUniversityPage(null, null, null, null, null, pageNum, pageSize);
    }

    /**
     * 条件查询大学列表（分页）
     * 
     * @param schoolCode 学校代码
     * @param schoolName 学校名称
     * @param level      学校层次
     * @param typeId     类型ID
     * @param province   省份
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @return 大学分页列表
     */
    @GetMapping("/search")
    public Result<PageResult<University>> searchUniversities(
            @RequestParam(value = "schoolCode", required = false) String schoolCode,
            @RequestParam(value = "schoolName", required = false) String schoolName,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return universityService.getUniversityPage(schoolCode, schoolName, level, typeId, province, pageNum, pageSize);
    }

    /**
     * 获取所有大学类型
     * 
     * @return 大学类型列表
     */
    @GetMapping("/types")
    public Result<List<UniversityType>> getUniversityTypes() {
        return universityService.getUniversityTypes();
    }
}

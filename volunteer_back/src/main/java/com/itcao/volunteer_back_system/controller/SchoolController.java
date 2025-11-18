package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.University;
import com.itcao.volunteer_back_system.pojo.UniversityType;
import com.itcao.volunteer_back_system.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private UniversityService universityService;

    /**
     * 分页查询大学列表
     *
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param schoolCode 学校代码
     * @param schoolName 学校名称
     * @param level      学校层次
     * @param typeId     类型ID
     * @param province   省份名称
     * @return 大学分页列表
     */
    @GetMapping("/list")
    public Result<PageResult<University>> getUniversityPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "universityId", required = false) String schoolCode,
            @RequestParam(value = "universityName", required = false) String schoolName,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "typeId", required = false) Integer typeId,
            @RequestParam(value = "province", required = false) String province) {
        return universityService.getUniversityPage(schoolCode, schoolName, level, typeId, province, pageNum, pageSize);
    }

    /**
     * 获取大学详情
     *
     * @param id 大学ID
     * @return 大学详情
     */
    @GetMapping("/detail")
    public Result<University> getUniversityDetail(@RequestParam("id") String id) {
        return universityService.getUniversityDetail(id);
    }

    /**
     * 根据编码获取大学信息
     *
     * @param code 大学编码
     * @return 大学信息
     */
    @GetMapping("/getByCode")
    public Result<University> getUniversityByCode(@RequestParam("code") String code) {
        return universityService.getUniversityByCode(code);
    }

    /**
     * 保存大学信息
     *
     * @param university 大学信息
     * @return 操作结果
     */
    @PostMapping("/save")
    public Result<Void> saveUniversity(@RequestBody University university) {
        return universityService.saveUniversity(university);
    }

    /**
     * 删除大学信息
     *
     * @param ids 大学ID列表，逗号分隔
     * @return 操作结果
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Void> deleteUniversity(@PathVariable("ids") String ids) {
        return universityService.deleteUniversity(ids);
    }

    /**
     * 获取大学类型列表
     *
     * @return 大学类型列表
     */
    @GetMapping("/types")
    public Result<List<UniversityType>> getUniversityTypes() {
        return universityService.getUniversityTypes();
    }
}
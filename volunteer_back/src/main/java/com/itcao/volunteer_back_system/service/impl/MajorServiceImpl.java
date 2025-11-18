package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.mapper.MajorMapper;
import com.itcao.volunteer_back_system.pojo.DisciplineCategory;
import com.itcao.volunteer_back_system.pojo.Major;
import com.itcao.volunteer_back_system.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 专业服务实现类
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    /**
     * 分页获取专业列表
     * 
     * @param majorCode  专业代码
     * @param majorName  专业名称
     * @param schoolCode 学校代码
     * @param schoolName 学校名称
     * @param pageNum    当前页码
     * @param pageSize   每页大小
     * @return 专业分页列表
     */
    @Override
    public Result<PageResult<Major>> getMajorPage(String majorCode, String majorName, String schoolCode,
            String schoolName, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<Major> majors = majorMapper.getMajorPage(majorCode, majorName, schoolCode, schoolName, offset, pageSize);
        Long total = majorMapper.getMajorCount(majorCode, majorName, schoolCode, schoolName);
        PageResult<Major> pageResult = new PageResult<>(majors, total, pageSize, pageNum);
        return Result.success(pageResult);
    }

    /**
     * 获取专业详情
     * 
     * @param id 专业ID
     * @return 专业详情
     */
    @Override
    public Result<Major> getMajorDetail(String id) {
        Major major = majorMapper.getMajorById(id);
        if (major != null) {
            return Result.success(major);
        }
        return Result.error("专业不存在");
    }

    /**
     * 根据编码获取专业信息
     * 
     * @param code 专业编码
     * @return 专业信息
     */
    @Override
    public Result<Major> getMajorByCode(String code) {
        Major major = majorMapper.getMajorByCode(code);
        if (major != null) {
            return Result.success(major);
        }
        return Result.error("专业不存在");
    }

    /**
     * 保存专业信息
     * 
     * @param major 专业信息
     * @return 操作结果
     */
    @Override
    public Result<Void> saveMajor(Major major) {
        int result;
        if (major.getMajorId() != null && !major.getMajorId().isEmpty()) {
            // 更新专业
            result = majorMapper.updateMajor(major);
        } else {
            // 新增专业
            result = majorMapper.insertMajor(major);
        }

        if (result > 0) {
            return Result.success();
        }
        return Result.error("操作失败");
    }

    /**
     * 删除专业
     * 
     * @param ids 专业ID列表，逗号分隔
     * @return 操作结果
     */
    @Override
    public Result<Void> deleteMajor(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int result = majorMapper.deleteMajors(idList);
        if (result > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 获取学科类别列表
     * 
     * @return 学科类别列表
     */
    @Override
    public Result<List<DisciplineCategory>> getDisciplineCategories() {
        List<DisciplineCategory> categories = majorMapper.getDisciplineCategories();
        return Result.success(categories);
    }
}
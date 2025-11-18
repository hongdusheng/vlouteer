package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.DisciplineCategory;
import com.itcao.volunteer_back_system.pojo.Major;

import java.util.List;

/**
 * 专业服务接口
 */
public interface MajorService {

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
    Result<PageResult<Major>> getMajorPage(String majorCode, String majorName, String schoolCode,
            String schoolName, Integer pageNum, Integer pageSize);

    /**
     * 获取专业详情
     * 
     * @param id 专业ID
     * @return 专业详情
     */
    Result<Major> getMajorDetail(String id);

    /**
     * 根据编码获取专业信息
     * 
     * @param code 专业编码
     * @return 专业信息
     */
    Result<Major> getMajorByCode(String code);

    /**
     * 保存专业信息
     * 
     * @param major 专业信息
     * @return 操作结果
     */
    Result<Void> saveMajor(Major major);

    /**
     * 删除专业
     * 
     * @param ids 专业ID列表，逗号分隔
     * @return 操作结果
     */
    Result<Void> deleteMajor(String ids);

    /**
     * 获取学科类别列表
     * 
     * @return 学科类别列表
     */
    Result<List<DisciplineCategory>> getDisciplineCategories();
}
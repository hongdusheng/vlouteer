package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.Dto.ApplicationListDTO;
import com.itcao.volunteer_back_system.Dto.VolunteerDetailDTO;
import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.Application;

/**
 * 志愿申请服务接口
 */
public interface ApplicationService {

        /**
         * 保存志愿信息
         * 
         * @param application 志愿信息
         * @return 操作结果
         */
        Result<Application> saveApplication(Application application);

        /**
         * 分页获取志愿信息列表
         * 
         * @param studentId   学生ID
         * @param studentName 学生姓名
         * @param province    省份
         * @param pageNum     当前页码
         * @param pageSize    每页大小
         * @return 志愿信息分页列表
         */
        Result<PageResult<ApplicationListDTO>> getApplicationList(String studentId, String studentName,
                        String province, Integer pageNum, Integer pageSize);

        /**
         * 删除志愿信息
         * 
         * @param id 志愿ID
         * @return 操作结果
         */
        Result<Void> deleteApplication(Integer id);

        /**
         * 分页获取志愿管理列表
         * 
         * @param studentId   学生ID
         * @param studentName 学生姓名
         * @param province    省份
         * @param pageNum     当前页码
         * @param pageSize    每页大小
         * @return 志愿管理分页列表
         */
        Result<PageResult<ApplicationListDTO>> getApplicationManagePage(String studentId, String studentName,
                        String province, Integer pageNum, Integer pageSize);

        /**
         * 获取志愿详情
         * 
         * @param studentId 学生ID
         * @return 志愿详情
         */
        Result<VolunteerDetailDTO> getVolunteerDetail(String studentId);
}
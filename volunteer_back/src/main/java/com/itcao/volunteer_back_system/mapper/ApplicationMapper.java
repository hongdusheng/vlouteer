package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.Dto.ApplicationListDTO;
import com.itcao.volunteer_back_system.pojo.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 志愿申请数据访问层
 */
@Mapper
public interface ApplicationMapper {

        /**
         * 新增志愿信息
         * 
         * @param application 志愿信息
         * @return 影响行数
         */
        int insertApplication(Application application);

        /**
         * 更新志愿信息
         * 
         * @param application 志愿信息
         * @return 影响行数
         */
        int updateApplication(Application application);

        /**
         * 删除志愿信息
         * 
         * @param id 志愿ID
         * @return 影响行数
         */
        int deleteApplication(Integer id);

        /**
         * 根据ID查询志愿信息
         * 
         * @param id 志愿ID
         * @return 志愿信息
         */
        Application getApplicationById(Integer id);

        /**
         * 根据学生ID和志愿顺序查询志愿信息
         * 
         * @param studentId      学生ID
         * @param volunteerOrder 志愿顺序
         * @return 志愿信息
         */
        Application getApplicationByStudentIdAndOrder(@Param("studentId") String studentId,
                        @Param("volunteerOrder") Integer volunteerOrder);

        /**
         * 检查院校是否存在
         * 
         * @param universityId 院校ID
         * @return 存在返回1，不存在返回0
         */
        int checkUniversityExists(@Param("universityId") String universityId);

        /**
         * 检查专业是否存在
         * 
         * @param majorId 专业ID
         * @return 存在返回1，不存在返回0
         */
        int checkMajorExists(@Param("majorId") String majorId);

        /**
         * 分页查询志愿管理列表
         * 
         * @param studentId   学生ID
         * @param studentName 学生姓名
         * @param province    省份
         * @param offset      偏移量
         * @param pageSize    每页大小
         * @return 志愿管理列表
         */
        List<ApplicationListDTO> getApplicationManagePage(@Param("studentId") String studentId,
                        @Param("studentName") String studentName,
                        @Param("province") String province,
                        @Param("offset") Integer offset,
                        @Param("pageSize") Integer pageSize);

        /**
         * 查询志愿管理总数
         * 
         * @param studentId   学生ID
         * @param studentName 学生姓名
         * @param province    省份
         * @return 志愿管理总数
         */
        Long getApplicationManageCount(@Param("studentId") String studentId,
                        @Param("studentName") String studentName,
                        @Param("province") String province);

        /**
         * 根据学生ID查询该学生的所有志愿信息
         * 
         * @param studentId 学生ID
         * @return 志愿信息列表
         */
        List<Application> getApplicationsByStudentId(@Param("studentId") String studentId);
}
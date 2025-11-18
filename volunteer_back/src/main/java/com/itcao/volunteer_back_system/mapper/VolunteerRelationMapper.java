package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.Dto.VolunteerInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 志愿关系数据访问层
 * 负责处理学生志愿信息的数据库操作
 * 
 * @author Your Name
 * @version 1.0
 * @since 2025-06-20
 */
@Mapper
public interface VolunteerRelationMapper {
    
    /**
     * 查询指定学生的所有志愿信息
     * 调用存储过程 get_student_volunteers 获取学生的志愿列表
     * 
     * @param studentId 学生ID，不能为空
     * @return 返回该学生的志愿信息列表，包含志愿顺序、大学名称、专业名称
     *         如果学生不存在或没有志愿信息，返回空列表
     * 
     * @example
     * 调用示例：
     * List<VolunteerInfoDTO> volunteers = getStudentVolunteers("20230001");
     * 
     * 返回数据格式：
     * [
     *   {
     *     "volunteerOrder": 1,
     *     "universityName": "清华大学",
     *     "majorName": "计算机科学与技术"
     *   },
     *   {
     *     "volunteerOrder": 2,
     *     "universityName": "北京大学", 
     *     "majorName": "软件工程"
     *   }
     * ]
     */
    @Select("call get_student_volunteers(#{studentId})")
    public List<VolunteerInfoDTO> getStudentVolunteers(@Param("studentId") String studentId);






}

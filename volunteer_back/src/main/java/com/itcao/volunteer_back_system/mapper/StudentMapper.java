package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

        /**
         * 分页查询学生列表
         * 
         * @param studentId   学生ID
         * @param studentName 学生姓名
         * @param gender      性别
         * @param provinceId  省份ID
         * @param offset      偏移量
         * @param pageSize    每页大小
         * @return 学生列表
         */
        List<Student> getStudentPage(String studentId, String studentName, String gender,
                        Integer provinceId, Integer offset, Integer pageSize);

        /**
         * 查询符合条件的学生总数
         * 
         * @param studentId   学生ID
         * @param studentName 学生姓名
         * @param gender      性别
         * @param provinceId  省份ID
         * @return 学生总数
         */
        Long getStudentCount(String studentId, String studentName, String gender, Integer provinceId);

        /**
         * 根据ID查询学生信息
         * 
         * @param id 学生ID
         * @return 学生信息
         */
        Student getStudentById(String id);

        /**
         * 新增学生信息
         * 
         * @param student 学生信息
         * @return 影响行数
         */
        int insertStudent(Student student);

        /**
         * 更新学生信息
         * 
         * @param student 学生信息
         * @return 影响行数
         */
        int updateStudent(Student student);

        /**
         * 批量删除学生信息
         * 
         * @param ids 学生ID列表
         * @return 影响行数
         */
        int deleteStudents(List<String> ids);
}

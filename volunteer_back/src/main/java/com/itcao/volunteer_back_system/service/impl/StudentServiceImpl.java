package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.mapper.StudentMapper;
import com.itcao.volunteer_back_system.pojo.Student;
import com.itcao.volunteer_back_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Result<PageResult<Student>> getStudentPage(String studentId, String studentName, String gender,
            Integer provinceId, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<Student> students = studentMapper.getStudentPage(studentId, studentName, gender, provinceId, offset,
                pageSize);
        Long total = studentMapper.getStudentCount(studentId, studentName, gender, provinceId);
        PageResult<Student> pageResult = new PageResult<>(students, total, pageSize, pageNum);
        return Result.success(pageResult);
    }

    @Override
    public Result<Student> getStudentDetail(String id) {
        Student student = studentMapper.getStudentById(id);
        if (student != null) {
            return Result.success(student);
        }
        return Result.error("学生不存在");
    }

    @Override
    public Result<Void> saveStudent(Student student) {
        int result;
        if (student.getStudentId() != null && !student.getStudentId().isEmpty()) {
            // 更新学生
            result = studentMapper.updateStudent(student);
        } else {
            // 新增学生
            result = studentMapper.insertStudent(student);
        }

        if (result > 0) {
            return Result.success();
        }
        return Result.error("操作失败");
    }

    @Override
    public Result<Void> deleteStudent(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int result = studentMapper.deleteStudents(idList);
        if (result > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
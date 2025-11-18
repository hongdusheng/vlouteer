package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.Student;

public interface StudentService {

    Result<PageResult<Student>> getStudentPage(String studentId, String studentName, String gender,
            Integer provinceId, Integer pageNum, Integer pageSize);

    Result<Student> getStudentDetail(String id);

    Result<Void> saveStudent(Student student);

    Result<Void> deleteStudent(String ids);
}
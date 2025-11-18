package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.Student;
import com.itcao.volunteer_back_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public Result<PageResult<Student>> getStudentPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "provinceId", required = false) Integer provinceId) {
        return studentService.getStudentPage(studentId, studentName, gender, provinceId, pageNum, pageSize);
    }

    @GetMapping("/detail")
    public Result<Student> getStudentDetail(@RequestParam("id") String id) {
        return studentService.getStudentDetail(id);
    }

    @PostMapping("/save")
    public Result<Void> saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/delete/{ids}")
    public Result<Void> deleteStudent(@PathVariable("ids") String ids) {
        return studentService.deleteStudent(ids);
    }
}
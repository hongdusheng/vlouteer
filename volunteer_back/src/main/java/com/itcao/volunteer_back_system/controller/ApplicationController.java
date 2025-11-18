package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.Dto.ApplicationListDTO;
import com.itcao.volunteer_back_system.Dto.VolunteerDetailDTO;
import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.Application;
import com.itcao.volunteer_back_system.pojo.User;
import com.itcao.volunteer_back_system.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

/**
 * 志愿填报控制器
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 保存学生填报的志愿信息
     * 
     * @param application 志愿信息
     * @param session     HTTP会话
     * @return 操作结果
     */
    @PostMapping("/save")
    public Result<Application> saveApplication(@RequestBody Application application, HttpSession session) {
        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("用户未登录，session ID: " + session.getId());
            return Result.error(401, "用户未登录");
        }

        // 检查是否是学生用户
        if (!"student".equals(user.getRole())) {
            System.out.println("非学生用户尝试填报志愿，用户角色: " + user.getRole());
            return Result.error(403, "只有学生用户才能填报志愿");
        }

        // 检查用户是否关联了学生ID
        if (user.getStudentId() == null || user.getStudentId().isEmpty()) {
            System.out.println("用户未关联学生信息，用户ID: " + user.getUserId());
            return Result.error(400, "当前用户未关联学生信息，请联系管理员");
        }

        // 检查请求中是否提供了学生ID，如果提供了则验证是否与当前用户匹配
        if (application.getStudentId() != null && !application.getStudentId().isEmpty() &&
                !application.getStudentId().equals(user.getStudentId())) {
            System.out.println("提供的学生ID与当前用户不匹配，提供的ID: " + application.getStudentId() +
                    ", 用户关联的ID: " + user.getStudentId());
            return Result.error(403, "无权为其他学生填报志愿");
        }

        // 设置学生ID为当前用户关联的学生ID
        application.setStudentId(user.getStudentId());

        System.out.println("用户 " + user.getUsername() + " 正在填报志愿，学生ID: " + user.getStudentId() +
                ", 院校ID: " + application.getUniversityId() +
                ", 专业ID: " + application.getMajorId() +
                ", 志愿顺序: " + application.getVolunteerOrder());

        // 检查志愿顺序是否在1-30之间
        if (application.getVolunteerOrder() == null ||
                application.getVolunteerOrder() < 1 ||
                application.getVolunteerOrder() > 30) {
            return Result.error(400, "志愿顺序必须在1-30之间");
        }

        // 保存志愿信息
        return applicationService.saveApplication(application);
    }

    /**
     * 获取学生填报的志愿信息列表
     * 
     * @param pageNum     当前页码，从1开始
     * @param pageSize    每页条数
     * @param studentId   学生ID（可选，精确查询）
     * @param studentName 学生姓名（可选，模糊查询）
     * @param province    省份（可选，模糊查询）
     * @param session     HTTP会话
     * @return 志愿信息列表
     */
    @GetMapping("/list")
    public Result<PageResult<ApplicationListDTO>> getApplicationList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "studentNo", required = false) String studentId,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "province", required = false) String province,
            HttpSession session) {

        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("用户未登录");
        }

        // 如果是学生用户，只能查看自己的志愿信息
        if ("student".equals(user.getRole())) {
            studentId = user.getStudentId();
            // 学生用户不能按照姓名和省份筛选
            studentName = null;
            province = null;
        }

        System.out.println(studentId + studentName + province);
        return applicationService.getApplicationList(studentId, studentName, province, pageNum, pageSize);
    }

    /**
     * 获取单个志愿信息详情
     * 
     * @param studentId 学生ID
     * @param session   HTTP会话
     * @return 志愿信息
     */
    @GetMapping("/detail")
    public Result<VolunteerDetailDTO> getApplicationDetail(
            @RequestParam(value = "id", required = false) String studentId, HttpSession session) {
        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("用户未登录");
        }

        // 如果是学生用户，只能查看自己的志愿信息
        if ("student".equals(user.getRole())) {
            studentId = user.getStudentId();
        }

        // 如果是管理员，可以通过参数指定查看哪个学生的志愿信息
        return applicationService.getVolunteerDetail(studentId);
    }

    /**
     * 删除指定ID的志愿信息
     * 
     * @param id      志愿ID
     * @param session HTTP会话
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteApplication(@PathVariable("id") Integer id, HttpSession session) {
        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("用户未登录");
        }

        return applicationService.deleteApplication(id);
    }

    /**
     * 获取志愿管理列表
     * 
     * @param pageNum     当前页码，从1开始
     * @param pageSize    每页条数
     * @param studentId   学生ID（可选，模糊查询）
     * @param studentName 学生姓名（可选，模糊查询）
     * @param province    省份（可选，模糊查询）
     * @param session     HTTP会话
     * @return 志愿管理列表
     */
    @GetMapping("/manage/list")
    public Result<PageResult<ApplicationListDTO>> getApplicationManagePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "province", required = false) String province,
            HttpSession session) {

        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("用户未登录");
        }

        // 只有管理员才能访问志愿管理列表
        if (!"admin".equals(user.getRole())) {
            return Result.error("无权限访问");
        }

        return applicationService.getApplicationManagePage(studentId, studentName, province, pageNum, pageSize);
    }

    /**
     * 获取志愿详情
     * 
     * @param studentId 学生ID
     * @param session   HTTP会话
     * @return 志愿详情
     */
    @GetMapping("/manage/detail")
    public Result<VolunteerDetailDTO> getVolunteerDetail(
            @RequestParam("studentId") String studentId,
            HttpSession session) {

        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("用户未登录");
        }

        // 只有管理员才能通过管理接口查看志愿详情
        if (!"admin".equals(user.getRole())) {
            return Result.error("无权限访问");
        }

        return applicationService.getVolunteerDetail(studentId);
    }
}
package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.User;
import com.itcao.volunteer_back_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前登录用户关联的学生ID
     * 
     * @param session HTTP会话
     * @return 学生ID
     */
    @GetMapping("/currentStudentId")
    public Result<Integer> getCurrentStudentId(HttpSession session) {
        // 从session中获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error(401, "用户未登录");
        }

        System.out.println("获取当前用户学生ID，用户名: " + user.getUsername() +
                ", 角色: " + user.getRole() +
                ", 学生ID: " + user.getStudentId());

        Integer studentId = Integer.parseInt(user.getStudentId());

        // 返回用户关联的学生ID，如果没有则为null
        return Result.success(studentId);
    }

    /**
     * 获取所有用户列表，不分页，主要用于下拉选择等场景
     * 
     * @return 用户列表
     */
    @GetMapping("/userList")
    public Result<List<User>> getUserList() {
        return userService.getUserList();
    }

    /**
     * 分页获取用户列表，支持按用户名、角色筛选
     * 
     * @param pageNum  当前页码，从1开始
     * @param pageSize 每页条数
     * @param username 用户名（可选，模糊查询）
     * @param role     用户角色（可选，精确匹配）
     * @return 用户分页列表
     */
    @GetMapping("/getUserPage")
    public Result<PageResult<User>> getUserPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "role", required = false) String role) {
        return userService.getUserPage(username, role, pageNum, pageSize);
    }

    /**
     * 获取指定ID的用户详细信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/getUser")
    public Result<User> getUser(@RequestParam("id") Integer id) {
        return userService.getUser(id);
    }

    /**
     * 新增或更新用户信息，有id时为更新，无id时为新增
     * 
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping("/updateUser")
    public Result<Void> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 删除指定ID的用户，支持批量删除，多个ID用逗号分隔
     * 
     * @param ids 用户ID，多个ID用逗号分隔
     * @return 操作结果
     */
    @DeleteMapping("/deleteUser/{ids}")
    public Result<Void> deleteUser(@PathVariable("ids") String ids) {
        return userService.deleteUser(ids);
    }

    /**
     * 更新用户状态，启用或禁用
     * 
     * @param params 包含userId和status的参数
     * @return 操作结果
     */
    @PostMapping("/updateStatus")
    public Result<Void> updateStatus(@RequestBody Map<String, Integer> params) {
        Integer userId = params.get("userId");
        Integer status = params.get("status");
        return userService.updateStatus(userId, status);
    }
}
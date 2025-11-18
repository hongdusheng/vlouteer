package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.User;
import com.itcao.volunteer_back_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制器
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口，验证用户名和密码
     * 
     * @param loginInfo 包含用户名和密码的Map
     * @param session   HTTP会话
     * @param response  HTTP响应
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<Void> login(@RequestBody Map<String, String> loginInfo, HttpSession session,
            HttpServletResponse response) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");

        System.out.println("用户尝试登录: " + username + ", session ID: " + session.getId());

        Result<User> result = userService.login(username, password);
        if (result.getCode() == 200) {
            // 登录成功，将用户信息存入session
            User user = result.getData();
            session.setAttribute("user", user);

            // 设置响应头，确保前端能获取到sessionID
            response.setHeader("X-Auth-Token", session.getId());

            System.out.println("用户登录成功: " + username +
                    ", 角色: " + user.getRole() +
                    ", 学生ID: " + user.getStudentId() +
                    ", session ID: " + session.getId());

            return Result.success("登录成功");
        }

        System.out.println("用户登录失败: " + username + ", 原因: " + result.getMessage());
        return Result.error(result.getMessage());
    }

    /**
     * 用户登出接口，清除服务端session信息
     * 
     * @param session HTTP会话
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            System.out.println("用户登出: " + user.getUsername() + ", session ID: " + session.getId());
        }

        session.removeAttribute("user");
        return Result.success("登出成功");
    }

    /**
     * 获取当前登录用户的详细信息，包括权限等
     * 
     * @param session HTTP会话
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            System.out.println("获取用户信息: " + user.getUsername() + ", session ID: " + session.getId());
            return userService.getUserInfo(user.getUserId());
        }

        System.out.println("获取用户信息失败: 未登录, session ID: " + session.getId());
        return Result.error("未登录");
    }

    /**
     * 获取当前会话状态，用于调试
     * 
     * @param session HTTP会话
     * @return 会话状态
     */
    @GetMapping("/session-status")
    public Result<Map<String, Object>> getSessionStatus(HttpSession session) {
        Map<String, Object> status = new HashMap<>();
        status.put("sessionId", session.getId());
        status.put("isNew", session.isNew());
        status.put("creationTime", session.getCreationTime());
        status.put("lastAccessedTime", session.getLastAccessedTime());

        User user = (User) session.getAttribute("user");
        if (user != null) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", user.getUserId());
            userInfo.put("username", user.getUsername());
            userInfo.put("role", user.getRole());
            userInfo.put("studentId", user.getStudentId());
            status.put("user", userInfo);
        } else {
            status.put("user", null);
        }

        System.out.println("获取会话状态: " + session.getId() + ", 是否已登录: " + (user != null));
        return Result.success(status);
    }
}
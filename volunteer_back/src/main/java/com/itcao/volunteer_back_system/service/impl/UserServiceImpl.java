package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.mapper.UserMapper;
import com.itcao.volunteer_back_system.pojo.User;
import com.itcao.volunteer_back_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @Override
    public Result<User> login(String username, String password) {
        User user = userMapper.login(username, password);
        if (user != null) {
            return Result.success("登录成功", user);
        }
        return Result.error("用户名或密码错误");
    }

    /**
     * 获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public Result<User> getUserInfo(Integer userId) {
        User user = userMapper.getUserById(userId);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("获取用户信息失败");
    }

    /**
     * 获取所有用户列表
     * 
     * @return 用户列表
     */
    @Override
    public Result<List<User>> getUserList() {
        List<User> userList = userMapper.getUserList();
        return Result.success(userList);
    }

    /**
     * 分页获取用户列表
     * 
     * @param username 用户名（模糊查询）
     * @param role     角色
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @return 用户分页列表
     */
    @Override
    public Result<PageResult<User>> getUserPage(String username, String role, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<User> users = userMapper.getUserPage(username, role, offset, pageSize);
        Long total = userMapper.getUserCount(username, role);
        PageResult<User> pageResult = new PageResult<>(users, total, pageSize, pageNum);
        return Result.success(pageResult);
    }

    /**
     * 获取单个用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public Result<User> getUser(Integer id) {
        User user = userMapper.getUserById(id);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    /**
     * 新增或更新用户信息
     * 
     * @param user 用户信息
     * @return 操作结果
     */
    @Override
    public Result<Void> updateUser(User user) {
        int result;
        if (user.getUserId() != null) {
            // 更新用户
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                // 如果提供了密码，则更新密码
                userMapper.updatePassword(user);
            }
            result = userMapper.updateUser(user);
        } else {
            // 新增用户
            result = userMapper.insertUser(user);
        }

        if (result > 0) {
            return Result.success();
        }
        return Result.error("操作失败");
    }

    /**
     * 删除用户
     * 
     * @param ids 用户ID列表，逗号分隔
     * @return 操作结果
     */
    @Override
    public Result<Void> deleteUser(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int result = userMapper.deleteUsers(idList);
        if (result > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 更新用户状态
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 操作结果
     */
    @Override
    public Result<Void> updateStatus(Integer userId, Integer status) {
        int result = userMapper.updateStatus(userId, status);
        if (result > 0) {
            return Result.success();
        }
        return Result.error("更新状态失败");
    }
}
package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.User;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户登录
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    Result<User> login(String username, String password);

    /**
     * 获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    Result<User> getUserInfo(Integer userId);

    /**
     * 获取所有用户列表
     * 
     * @return 用户列表
     */
    Result<List<User>> getUserList();

    /**
     * 分页获取用户列表
     * 
     * @param username 用户名（模糊查询）
     * @param role     角色
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @return 用户分页列表
     */
    Result<PageResult<User>> getUserPage(String username, String role, Integer pageNum, Integer pageSize);

    /**
     * 获取单个用户信息
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    Result<User> getUser(Integer id);

    /**
     * 新增或更新用户信息
     * 
     * @param user 用户信息
     * @return 操作结果
     */
    Result<Void> updateUser(User user);

    /**
     * 删除用户
     * 
     * @param ids 用户ID列表，逗号分隔
     * @return 操作结果
     */
    Result<Void> deleteUser(String ids);

    /**
     * 更新用户状态
     * 
     * @param userId 用户ID
     * @param status 状态
     * @return 操作结果
     */
    Result<Void> updateStatus(Integer userId, Integer status);
}
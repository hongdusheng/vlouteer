package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {

        /**
         * 用户登录
         * 
         * @param username 用户名
         * @param password 密码
         * @return 用户信息
         */
        User login(String username, String password);

        /**
         * 根据ID获取用户
         * 
         * @param userId 用户ID
         * @return 用户信息
         */
        User getUserById(Integer userId);

        /**
         * 获取所有用户列表
         * 
         * @return 用户列表
         */
        List<User> getUserList();

        /**
         * 分页获取用户列表
         * 
         * @param username 用户名（模糊查询）
         * @param role     角色
         * @param offset   偏移量
         * @param pageSize 每页大小
         * @return 用户列表
         */
        List<User> getUserPage(String username, String role, Integer offset, Integer pageSize);

        /**
         * 获取用户总数
         * 
         * @param username 用户名（模糊查询）
         * @param role     角色
         * @return 用户总数
         */
        Long getUserCount(String username, String role);

        /**
         * 新增用户
         * 
         * @param user 用户信息
         * @return 影响行数
         */
        int insertUser(User user);

        /**
         * 更新用户
         * 
         * @param user 用户信息
         * @return 影响行数
         */
        int updateUser(User user);

        /**
         * 更新用户密码
         * 
         * @param user 用户信息
         * @return 影响行数
         */
        int updatePassword(User user);

        /**
         * 更新用户状态
         * 
         * @param userId 用户ID
         * @param status 状态
         * @return 影响行数
         */
        int updateStatus(Integer userId, Integer status);

        /**
         * 删除用户
         * 
         * @param ids 用户ID列表
         * @return 影响行数
         */
        int deleteUsers(List<Integer> ids);
}
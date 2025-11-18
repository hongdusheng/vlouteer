import request from '@/utils/request'

// 用户登录
export function login(data) {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

// 用户登出
export function logout() {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}

// 获取用户信息
export function getUserInfo() {
  return request({
    url: '/admin/info',
    method: 'get'
  })
}

// 获取当前登录用户的学生ID
export function getCurrentStudentId() {
  return request({
    url: '/user/currentStudentId',
    method: 'get'
  })
}

// 获取用户列表
export function getUserList() {
  return request({
    url: '/user/userList',
    method: 'get'
  })
}

// 获取用户分页列表
export function getUserPage(params) {
  return request({
    url: '/user/getUserPage',
    method: 'get',
    params
  })
}

// 获取单个用户信息
export function getUser(userId) {
  return request({
    url: '/user/getUser',
    method: 'get',
    params: { id: userId }
  })
}

// 更新用户信息
export function updateUser(data) {
  return request({
    url: '/user/updateUser',
    method: 'post',
    data
  })
}

// 删除用户
export function deleteUsers(ids) {
  return request({
    url: '/user/deleteUser/' + ids,
    method: 'delete'
  })
}

// 更新用户状态
export function updateUserStatus(data) {
  return request({
    url: '/user/updateStatus',
    method: 'post',
    data
  })
} 
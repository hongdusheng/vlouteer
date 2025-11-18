import request from '@/utils/request'

// 获取学生分页列表
export function getStudentPage(params) {
  return request({
    url: '/student/list',
    method: 'get',
    params
  })
}

// 获取单个学生信息
export function getStudent(studentId) {
  return request({
    url: '/student/detail',
    method: 'get',
    params: { id: studentId }
  })
}

// 新增或更新学生信息
export function saveOrUpdateStudent(data) {
  return request({
    url: '/student/save',
    method: 'post',
    data
  })
}

// 删除学生
export function deleteStudents(ids) {
  return request({
    url: '/student/delete/' + ids,
    method: 'delete'
  })
}

// 设置学生标签
export function setStudentTags(data) {
  return request({
    url: '/student/tags',
    method: 'post',
    data
  })
}

// 获取省份列表
export function getProvinceList() {
  return request({
    url: '/common/provinces',
    method: 'get'
  })
} 
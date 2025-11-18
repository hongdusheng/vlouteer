import request from '@/utils/request'

// 获取院校分页列表
export function getSchoolPage(params) {
  // 移除空参数
  const validParams = {}
  for (const key in params) {
    if (params[key] !== null && params[key] !== undefined && params[key] !== '') {
      validParams[key] = params[key]
    }
  }

  return request({
    url: '/school/list',
    method: 'get',
    params: validParams
  })
}

// 获取单个院校信息
export function getSchool(universityId) {
  return request({
    url: '/school/detail',
    method: 'get',
    params: { id: universityId }
  })
}

// 新增或更新院校信息
export function saveOrUpdateSchool(data) {
  return request({
    url: '/school/save',
    method: 'post',
    data
  })
}

// 删除院校
export function deleteSchools(ids) {
  return request({
    url: '/school/delete/' + ids,
    method: 'delete'
  })
}

// 获取院校类型列表
export function getSchoolTypes() {
  return request({
    url: '/school/types',
    method: 'get'
  })
}
import request from '@/utils/request'

// 保存志愿信息
export function saveApplication(data) {
  console.log('发送志愿提交请求:', data)
  return request({
    url: '/application/save',
    method: 'post',
    data,
    // 添加错误处理
    validateStatus: function (status) {
      return status >= 200 && status < 500; // 默认只接受2xx的状态码
    }
  })
}

// 获取志愿信息列表
export function getApplicationList(params) {
  return request({
    url: '/application/list',
    method: 'get',
    params
  })
}

// 获取单个志愿信息
export function getApplication(id) {
  return request({
    url: '/application/detail',
    method: 'get',
    params: { id }
  })
}

// 删除志愿信息
export function deleteApplication(id) {
  return request({
    url: '/application/delete/' + id,
    method: 'delete'
  })
}

// 提交志愿表
export function submitApplication(id) {
  return request({
    url: '/application/submit/' + id,
    method: 'put'
  })
}

// 通过院校代码查询院校信息
export function getSchoolByCode(code) {
  return request({
    url: '/school/getByCode',
    method: 'get',
    params: { code }
  })
}

// 通过专业代码查询专业信息
export function getMajorByCode(code) {
  return request({
    url: '/major/getByCode',
    method: 'get',
    params: { code }
  })
} 
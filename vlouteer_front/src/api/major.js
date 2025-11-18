import request from '@/utils/request'

// 获取专业分页列表
export function getMajorPage(params) {
  return request({
    url: '/major/list',
    method: 'get',
    params
  })
}

// 获取单个专业信息
export function getMajor(majorId) {
  return request({
    url: '/major/detail',
    method: 'get',
    params: { id: majorId }
  })
}

// 新增或更新专业信息
export function saveOrUpdateMajor(data) {
  return request({
    url: '/major/save',
    method: 'post',
    data
  })
}

// 删除专业
export function deleteMajors(ids) {
  return request({
    url: '/major/delete/' + ids,
    method: 'delete'
  })
}

// 获取专业分类列表
export function getMajorCategories() {
  return request({
    url: '/major/categories',
    method: 'get'
  })
}

// 设置专业标签
export function setMajorTags(data) {
  return request({
    url: '/major/tags',
    method: 'post',
    data
  })
} 
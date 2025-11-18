import request from '@/utils/request'

// 获取志愿推荐
export function getVolunteerRecommendation(studentId) {
  return request({
    url: '/recommendation/volunteer',
    method: 'get',
    params: { studentId }
  })
}

// 检测志愿冲突
export function checkVolunteerConflict(studentId) {
  return request({
    url: '/recommendation/conflict',
    method: 'get',
    params: { studentId }
  })
}

// 预测录取概率
export function predictAdmissionProbability(studentId, universityId, majorId) {
  return request({
    url: '/recommendation/probability',
    method: 'get',
    params: { studentId, universityId, majorId }
  })
}

// 分析专业就业前景
export function analyzeMajorEmployment(majorId) {
  return request({
    url: '/recommendation/employment',
    method: 'get',
    params: { majorId }
  })
}

// 分析省份分数线
export function analyzeProvinceScore(provinceId, disciplineCategoryId) {
  return request({
    url: '/recommendation/province',
    method: 'get',
    params: { provinceId, disciplineCategoryId }
  })
}

// 志愿综合分析
export function analyzeVolunteerComprehensive(studentId) {
  return request({
    url: '/recommendation/comprehensive',
    method: 'get',
    params: { studentId }
  })
}

// 院校专业匹配
export function matchUniversityMajor(studentId, majorPreference, locationPreference, matchCount, matchType) {
  return request({
    url: '/recommendation/match',
    method: 'get',
    params: { 
      studentId, 
      majorPreference: majorPreference.join(','), 
      locationPreference: locationPreference.join(','),
      matchCount,
      matchType
    }
  })
}

// 获取学科门类
export function getDisciplineCategories() {
  return request({
    url: '/recommendation/categories',
    method: 'get'
  })
} 
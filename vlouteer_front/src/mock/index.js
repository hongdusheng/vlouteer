/**
 * 模拟服务器配置
 * 用于拦截API请求并返回模拟数据
 */

import axios from 'axios'
import {
    volunteerRecommendationData,
    volunteerConflictData,
    volunteerComprehensiveData,
    universityMajorMatchData
} from './recommendation'

// 创建一个自定义的axios实例用于拦截
const mockAxios = axios.create()

// 请求拦截器
mockAxios.interceptors.request.use(
    config => {
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// 响应拦截器
mockAxios.interceptors.response.use(
    response => {
        const { url, method, params } = response.config
        console.log('Mock拦截请求:', url, method, params)

        // 根据请求URL返回对应的模拟数据
        if (url.includes('/api/recommendation/volunteer')) {
            console.log('返回志愿推荐模拟数据')
            return { data: volunteerRecommendationData }
        } else if (url.includes('/api/recommendation/conflict')) {
            console.log('返回冲突检测模拟数据')
            return { data: volunteerConflictData }
        } else if (url.includes('/api/recommendation/comprehensive')) {
            console.log('返回志愿综合分析模拟数据')
            return { data: volunteerComprehensiveData }
        } else if (url.includes('/api/recommendation/match')) {
            console.log('返回院校专业匹配模拟数据')
            return { data: universityMajorMatchData }
        }

        // 默认返回
        return response
    },
    error => {
        return Promise.reject(error)
    }
)

// 替换原始的axios请求
export function setupMock() {
    // 保存原始的axios方法
    const originalRequest = axios.request

    // 替换axios的request方法
    axios.request = function (config) {
        // 只拦截需要模拟数据的接口
        // 排除预测录取概率、分析专业就业前景这两个接口
        if (
            config.url.includes('/api/recommendation/volunteer') ||
            config.url.includes('/api/recommendation/conflict') ||
            config.url.includes('/api/recommendation/comprehensive') ||
            config.url.includes('/api/recommendation/match')
        ) {
            console.log('使用模拟数据:', config.url)
            return mockAxios.request(config).then(response => {
                // 确保响应结构与实际API一致
                return response.data
            })
        }

        // 其他API使用原始axios
        return originalRequest.call(this, config)
    }

    console.log('模拟服务器已启动，拦截以下接口:')
    console.log('- /api/recommendation/volunteer (志愿推荐)')
    console.log('- /api/recommendation/conflict (冲突检测)')
    console.log('- /api/recommendation/comprehensive (志愿综合分析)')
    console.log('- /api/recommendation/match (院校专业匹配)')
    console.log('以下接口将从后端获取数据:')
    console.log('- /api/recommendation/probability (预测录取概率)')
    console.log('- /api/recommendation/employment (分析专业就业前景)')
}

export default setupMock 
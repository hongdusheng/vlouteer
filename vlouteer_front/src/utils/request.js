import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建axios实例
const service = axios.create({
  baseURL: '/api', // 修改为代理地址
  timeout: 5000, // 请求超时时间
  withCredentials: true // 添加跨域请求时携带cookie
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    console.log('发送请求:', config.url, config.method, config.data || config.params)

    // 从localStorage获取token
    const token = localStorage.getItem('token')
    if (token) {
      // 将token添加到请求头
      config.headers['Authorization'] = `Bearer ${token}`
    }

    // 添加常用请求头
    config.headers['Content-Type'] = config.headers['Content-Type'] || 'application/json'
    config.headers['X-Requested-With'] = 'XMLHttpRequest'

    return config
  },
  error => {
    console.log('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    console.log('收到响应:', response.config.url, response.status, response.data)
    const res = response.data

    // 如果接口返回未授权状态码
    if (res.code === 401) {
      ElMessage.error('登录已过期，请重新登录')
      // 清除本地存储的token
      localStorage.removeItem('token')
      // 跳转到登录页
      router.push('/login')
      return Promise.reject(new Error('未授权，请重新登录'))
    }

    // 如果返回500错误码
    if (res.code === 500) {
      ElMessage.error(res.message || '服务器内部错误')
      return Promise.reject(new Error(res.message || '服务器内部错误'))
    }

    // 如果响应成功
    return res
  },
  error => {
    console.log('响应错误:', error.config?.url, error.message, error.response?.status)
    // 处理HTTP错误状态码
    if (error.response) {
      if (error.response.status === 401) {
        ElMessage.error('登录已过期，请重新登录')
        // 清除本地存储的token
        localStorage.removeItem('token')
        // 跳转到登录页
        router.push('/login')
      } else if (error.response.status === 500) {
        ElMessage.error('服务器内部错误，请联系管理员')
      } else if (error.response.status === 404) {
        ElMessage.error('请求的资源不存在')
      } else {
        ElMessage.error(error.response.data?.message || `请求失败(${error.response.status})`)
      }
    } else if (error.request) {
      ElMessage.error('网络错误，服务器未响应')
    } else {
      ElMessage.error('网络错误，请检查您的网络连接')
    }
    return Promise.reject(error)
  }
)

export default service 
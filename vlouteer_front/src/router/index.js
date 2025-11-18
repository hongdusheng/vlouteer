import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/login/index.vue')
    },
    {
      path: '/',
      name: 'layout',
      component: Layout,
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('../views/home/index.vue'),
          meta: { title: '首页', icon: 'House' }
        },
        {
          path: '/student',
          name: 'student',
          component: () => import('../views/student/index.vue'),
          meta: { title: '学生管理', icon: 'User' }
        },
        {
          path: '/school',
          name: 'school',
          component: () => import('../views/school/index.vue'),
          meta: { title: '院校管理', icon: 'School' },
          redirect: '/school/index',
          children: [
            {
              path: '/school/index',
              name: 'schoolIndex',
              component: () => import('../views/school/index.vue'),
              meta: { title: '院校列表' }
            },
            {
              path: '/school/major',
              name: 'major',
              component: () => import('../views/school/major/index.vue'),
              meta: { title: '专业管理' }
            },
            {
              path: '/school/query',
              name: 'schoolQuery',
              component: () => import('../views/school/query/index.vue'),
              meta: { title: '院校查询' }
            }
          ]
        },
        {
          path: '/application',
          name: 'application',
          component: () => import('../views/application/index.vue'),
          meta: { title: '志愿填报', icon: 'Edit' },
          redirect: '/application/index',
          children: [
            {
              path: '/application/index',
              name: 'applicationIndex',
              component: () => import('../views/application/index.vue'),
              meta: { title: '志愿概览' }
            },
            {
              path: '/application/apply',
              name: 'apply',
              component: () => import('../views/application/apply/index.vue'),
              meta: { title: '填报志愿' }
            },
            {
              path: '/application/manage',
              name: 'manage',
              component: () => import('../views/application/manage/index.vue'),
              meta: { title: '志愿管理' }
            }
          ]
        },
        {
          path: '/recommendation',
          name: 'recommendation',
          component: () => import('../views/recommendation/index.vue'),
          meta: { title: '智能推荐', icon: 'Cpu' },
          redirect: '/recommendation/volunteer',
          children: [
            {
              path: '/recommendation/volunteer',
              name: 'volunteer',
              component: () => import('../views/recommendation/volunteer/index.vue'),
              meta: { title: '志愿推荐' }
            },
            {
              path: '/recommendation/conflict',
              name: 'conflict',
              component: () => import('../views/recommendation/conflict/index.vue'),
              meta: { title: '冲突检测' }
            },
            {
              path: '/recommendation/probability',
              name: 'probability',
              component: () => import('../views/recommendation/probability/index.vue'),
              meta: { title: '录取概率' }
            },
            {
              path: '/recommendation/employment',
              name: 'employment',
              component: () => import('../views/recommendation/employment/index.vue'),
              meta: { title: '就业前景' }
            },
            {
              path: '/recommendation/province',
              name: 'province',
              component: () => import('../views/recommendation/province/index.vue'),
              meta: { title: '分数线分析' }
            },
            {
              path: '/recommendation/comprehensive',
              name: 'comprehensive',
              component: () => import('../views/recommendation/comprehensive/index.vue'),
              meta: { title: '志愿综合分析' }
            },
            {
              path: '/recommendation/match',
              name: 'match',
              component: () => import('../views/recommendation/match/index.vue'),
              meta: { title: '院校专业匹配' }
            }
          ]
        },
        {
          path: '/system',
          name: 'system',
          component: () => import('../views/system/index.vue'),
          meta: { title: '系统管理', icon: 'Setting' }
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 检查token是否存在
  const token = localStorage.getItem('token')
  
  try {
    if (to.path === '/login') {
      // 如果已登录且前往登录页，重定向到首页
      if (token) {
        next({ path: '/' })
      } else {
        next()
      }
    } else {
      // 如果前往的不是登录页，检查是否已登录
      if (!token) {
        // 未登录则重定向到登录页
        next({ path: '/login' })
      } else {
        // 已登录则允许访问
        next()
      }
    }
  } catch (error) {
    console.error('路由导航错误:', error)
    // 发生错误时重定向到登录页
    localStorage.removeItem('token')
    next({ path: '/login' })
  }
})

// 路由错误处理
router.onError((error) => {
  console.error('路由错误:', error)
  // 可以在这里添加全局错误处理，如显示错误通知等
})

export default router 
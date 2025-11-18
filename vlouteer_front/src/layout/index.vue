<template>
  <div class="app-wrapper">
    <!-- 左侧导航栏 -->
    <div class="sidebar-container">
      <div class="logo-container">
        <h1 class="logo-title">高考志愿填报辅助系统</h1>
      </div>
      <el-menu 
        :default-active="activeMenu"
        class="sidebar-menu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        :unique-opened="true"
        router>
        <el-menu-item index="/home">
          <el-icon><HomeFilled /></el-icon>
          <template #title>首页</template>
        </el-menu-item>
        <el-menu-item index="/system">
          <el-icon><User /></el-icon>
          <template #title>用户管理</template>
        </el-menu-item>
        <el-menu-item index="/student">
          <el-icon><UserFilled /></el-icon>
          <template #title>学生管理</template>
        </el-menu-item>
        <el-sub-menu index="/school">
          <template #title>
            <el-icon><School /></el-icon>
            <span>院校管理</span>
          </template>
          <el-menu-item index="/school/query">
            <el-icon><Search /></el-icon>
            院校查询
          </el-menu-item>
          <el-menu-item index="/school/major">
            <el-icon><Collection /></el-icon>
            专业查询
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/application">
          <template #title>
            <el-icon><DocumentAdd /></el-icon>
            <span>填报管理</span>
          </template>
          <el-menu-item index="/application/apply">
            <el-icon><EditPen /></el-icon>
            志愿填报
          </el-menu-item>
          <el-menu-item index="/application/manage">
            <el-icon><Files /></el-icon>
            志愿管理
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/recommendation">
          <template #title>
            <el-icon><MagicStick /></el-icon>
            <span>智能推荐</span>
          </template>
          <el-menu-item index="/recommendation/volunteer">
            <el-icon><Promotion /></el-icon>
            志愿推荐
          </el-menu-item>
          <el-menu-item index="/recommendation/conflict">
            <el-icon><Warning /></el-icon>
            志愿冲突检测
          </el-menu-item>
          <el-menu-item index="/recommendation/probability">
            <el-icon><DataAnalysis /></el-icon>
            录取概率预测
          </el-menu-item>
          <el-menu-item index="/recommendation/employment">
            <el-icon><TrendCharts /></el-icon>
            专业就业前景
          </el-menu-item>
          <el-menu-item index="/recommendation/province">
            <el-icon><Histogram /></el-icon>
            省份分数线分析
          </el-menu-item>
          <el-menu-item index="/recommendation/comprehensive">
            <el-icon><PieChart /></el-icon>
            志愿综合分析
          </el-menu-item>
          <el-menu-item index="/recommendation/match">
            <el-icon><Connection /></el-icon>
            院校专业匹配
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>
    
    <!-- 右侧内容区 -->
    <div class="main-container">
      <!-- 顶部导航栏 -->
      <div class="navbar">
        <div class="left-menu">
          <el-icon class="hamburger" :size="20"><Location /></el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.path !== '/home'">{{ currentPageTitle }}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentSubTitle">{{ currentSubTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <div class="right-menu">
          <el-dropdown trigger="click">
            <div class="avatar-wrapper">
              <el-avatar :size="40" :src="avatarUrl" />
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToUserCenter">
                  <el-icon><User /></el-icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="logout" divided>
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      
      <!-- 主内容区 -->
      <div class="app-main">
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <component :is="Component" v-if="Component" />
            <div v-else class="loading-placeholder">
              <el-empty description="加载中..."></el-empty>
            </div>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onErrorCaptured } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { 
  HomeFilled, 
  Setting, 
  User, 
  School, 
  DocumentAdd, 
  Menu, 
  SwitchButton, 
  Location,
  UserFilled,
  Search,
  Collection,
  EditPen,
  Files,
  MagicStick,
  Promotion,
  Warning,
  DataAnalysis,
  TrendCharts,
  Histogram,
  PieChart,
  Connection
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const route = useRoute()

// 错误处理
onErrorCaptured((err, instance, info) => {
  console.error('组件错误:', err)
  console.error('错误来源:', instance)
  console.error('错误信息:', info)
  ElMessage.error('页面发生错误，请刷新重试')
  return false // 阻止错误继续传播
})

// 头像URL - 使用相对路径避免加载失败
const avatarUrl = ref('https://austain-java-ai-web.oss-cn-beijing.aliyuncs.com/2025/02/2e17d44a-66a1-47c4-951a-7c4616f2b3f6.jpg')

// 当前激活的菜单
const activeMenu = computed(() => {
  try {
    return route.path
  } catch (error) {
    console.error('获取当前路径错误:', error)
    return '/home'
  }
})

// 当前页面标题
const currentPageTitle = computed(() => {
  try {
    const pathMap = {
      '/home': '首页',
      '/system': '用户管理',
      '/student': '学生管理',
      '/school/query': '院校管理',
      '/school/major': '院校管理',
      '/application/apply': '填报管理',
      '/application/manage': '填报管理',
      '/recommendation/volunteer': '智能推荐',
      '/recommendation/conflict': '智能推荐',
      '/recommendation/probability': '智能推荐',
      '/recommendation/employment': '智能推荐',
      '/recommendation/province': '智能推荐',
      '/recommendation/comprehensive': '智能推荐',
      '/recommendation/match': '智能推荐'
    }
    return pathMap[route.path] || '首页'
  } catch (error) {
    console.error('获取页面标题错误:', error)
    return '首页'
  }
})

// 当前页面子标题
const currentSubTitle = computed(() => {
  try {
    if (route.meta && route.meta.subTitle) {
      return route.meta.subTitle
    }
    return ''
  } catch (error) {
    console.error('获取页面子标题错误:', error)
    return ''
  }
})

// 跳转到个人中心
const goToUserCenter = () => {
  try {
    router.push('/user-center')
  } catch (error) {
    console.error('导航错误:', error)
    ElMessage.error('导航失败，请稍后重试')
  }
}

// 退出登录
const logout = () => {
  ElMessageBox.confirm('确认退出系统吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    try {
      // 清除token
      localStorage.removeItem('token')
      // 跳转到登录页
      router.push('/login')
      ElMessage.success('退出成功')
    } catch (error) {
      console.error('登出错误:', error)
      // 确保token被清除
      localStorage.removeItem('token')
      // 强制刷新页面
      window.location.href = '/login'
    }
  }).catch(() => {})
}
</script>

<style scoped>
.app-wrapper {
  display: flex;
  position: relative;
  height: 100vh;
  width: 100%;
}

/* 侧边栏样式 */
.sidebar-container {
  width: 210px;
  height: 100%;
  background-color: #1f2d3d;
  transition: width 0.3s;
  overflow-x: hidden;
}

.logo-container {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #1f2d3d;
  border-bottom: 1px solid #283446;
}

.logo-title {
  color: white;
  font-size: 16px;
  margin: 0;
  white-space: nowrap;
}

.sidebar-menu {
  border-right: none;
  background-color: #1f2d3d;
}

:deep(.el-menu-item) {
  height: 56px;
  line-height: 56px;
}

:deep(.el-menu-item.is-active) {
  background-color: #1890ff !important;
  color: white !important;
}

:deep(.el-menu-item:hover) {
  background-color: #283446 !important;
}

:deep(.el-icon) {
  margin-right: 10px;
  font-size: 18px;
}

/* 主内容区样式 */
.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

/* 顶部导航栏样式 */
.navbar {
  height: 60px;
  background-color: #f5f5f5;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.left-menu {
  display: flex;
  align-items: center;
}

.hamburger {
  margin-right: 10px;
  cursor: pointer;
}

.right-menu {
  display: flex;
  align-items: center;
}

.avatar-wrapper {
  cursor: pointer;
  display: flex;
  align-items: center;
}

/* 主内容区样式 */
.app-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f0f2f5;
}

/* 过渡动画 */
.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

/* 加载占位符 */
.loading-placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
}
</style> 
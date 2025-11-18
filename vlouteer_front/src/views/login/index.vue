<template>
  <div class="login-container">
    <div class="login-box">
      <div class="title-container">
        <h3 class="title">高考志愿填报辅助系统</h3>
      </div>
      <el-form :model="loginForm" ref="loginFormRef" :rules="loginRules">
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
        </el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          class="login-button"
          @click="handleLogin"
        >
          登录
        </el-button>
        <div class="register-link">
          <a @click="goRegister">立即注册</a>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { login, getUserInfo, getCurrentStudentId } from '@/api/user'

// 路由实例
const router = useRouter()

// 加载状态
const loading = ref(false)

// 登录表单ref
const loginFormRef = ref()

// 登录表单数据
const loginForm = reactive({
  username: '', // 移除默认值，由用户输入
  password: '', // 移除默认值，由用户输入
  rememberMe: false
})

// 登录表单校验规则
const loginRules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})

// 处理登录
const handleLogin = async () => {
  try {
    await loginFormRef.value.validate(async (valid) => {
      if (valid) {
        loading.value = true
        try {
          // 调用实际的登录接口
          const res = await login(loginForm)
          if (res.code === 200) {
            // 登录成功，保存token
            localStorage.setItem('token', res.data?.token || 'default-token')
            // 保存用户ID
            if (res.data?.userId) {
              localStorage.setItem('userId', res.data.userId)
            }
            
            // 设置登录状态
            localStorage.setItem('isLogin', 'true')
            
            // 获取用户信息
            try {
              const userInfoRes = await getUserInfo()
              if (userInfoRes.code === 200) {
                // 保存用户角色
                const userRole = userInfoRes.data?.role || ''
                localStorage.setItem('userRole', userRole)
                
                // 如果是学生角色，获取学生ID
                if (userRole === 'student') {
                  const studentIdRes = await getCurrentStudentId()
                  if (studentIdRes.code === 200 && studentIdRes.data) {
                    localStorage.setItem('studentId', studentIdRes.data)
                  }
                } else {
                  // 如果不是学生角色，清除可能存在的学生ID
                  localStorage.removeItem('studentId')
                }
              }
            } catch (infoError) {
              console.error('获取用户信息错误', infoError)
            }
            
            ElMessage.success('登录成功')
            // 登录成功后跳转到首页
            router.push('/home')
          } else {
            ElMessage.error(res.message || '登录失败')
          }
        } catch (error) {
          console.error('登录错误', error)
          ElMessage.error('登录失败，请检查网络或服务器状态')
        } finally {
          loading.value = false
        }
      }
    })
  } catch (error) {
    console.error('表单验证错误', error)
    loading.value = false
    ElMessage.error('表单验证失败，请检查输入')
  }
}

// 注册跳转
const goRegister = () => {
  // TODO: 跳转到注册页面
  console.log('跳转到注册页面')
}
</script>

<style>
html, body, #app {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow: hidden;
}
</style>

<style scoped>
.login-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background: url('@/assets/login-back-image.png') no-repeat center center;
  background-size: cover;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.login-box {
  width: 400px;
  padding: 30px 35px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.1);
  margin-right: 10%;
}

.title-container {
  text-align: center;
  margin-bottom: 40px;
}

.title {
  margin: 0;
  color: #409EFF;
  font-size: 22px;
  font-weight: bold;
}

.login-button {
  width: 100%;
  margin-top: 10px;
  border-radius: 4px;
  padding: 12px 15px;
  height: auto;
  font-size: 16px;
}

.register-link {
  margin-top: 15px;
  text-align: right;
}

.register-link a {
  color: #409EFF;
  text-decoration: none;
  cursor: pointer;
}

:deep(.el-input__wrapper) {
  padding: 1px 15px;
}

:deep(.el-input__inner) {
  height: 40px;
}

:deep(.el-checkbox__label) {
  font-size: 14px;
}
</style> 
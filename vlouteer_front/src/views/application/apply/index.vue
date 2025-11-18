<template>
  <div class="application-apply-container">
    <!-- 第一志愿 -->
    <div class="volunteer-section">
      <div class="section-title">第一志愿</div>
      <div class="volunteer-form">
        <div class="form-item">
          <el-input
            v-model="volunteerForm.first.universityId"
            placeholder="请输入院校代码"
            clearable
            @change="handleSchoolCodeChange(1)"
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.first.universityName"
            placeholder="请输入院校名称"
            clearable
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.first.majorId"
            placeholder="请输入专业代码"
            clearable
            @change="handleMajorCodeChange(1)"
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.first.majorName"
            placeholder="请输入专业名称"
            clearable
          />
        </div>
        <div class="form-item">
          <el-button type="primary" @click="handleSubmit(1)">提交</el-button>
        </div>
      </div>
    </div>

    <!-- 第二志愿 -->
    <div class="volunteer-section">
      <div class="section-title">第二志愿</div>
      <div class="volunteer-form">
        <div class="form-item">
          <el-input
            v-model="volunteerForm.second.universityId"
            placeholder="请输入院校代码"
            clearable
            @change="handleSchoolCodeChange(2)"
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.second.universityName"
            placeholder="请输入院校名称"
            clearable
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.second.majorName"
            placeholder="请输入专业名称"
            clearable
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.second.majorId"
            placeholder="请输入专业代码"
            clearable
            @change="handleMajorCodeChange(2)"
          />
        </div>
        <div class="form-item">
          <el-button type="primary" @click="handleSubmit(2)">提交</el-button>
        </div>
      </div>
    </div>
    
    <!-- 第三志愿 -->
    <div class="volunteer-section">
      <div class="section-title">第三志愿</div>
      <div class="volunteer-form">
        <div class="form-item">
          <el-input
            v-model="volunteerForm.third.universityId"
            placeholder="请输入院校代码"
            clearable
            @change="handleSchoolCodeChange(3)"
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.third.universityName"
            placeholder="请输入院校名称"
            clearable
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.third.majorName"
            placeholder="请输入专业名称"
            clearable
          />
        </div>
        <div class="form-item">
          <el-input
            v-model="volunteerForm.third.majorId"
            placeholder="请输入专业代码"
            clearable
            @change="handleMajorCodeChange(3)"
          />
        </div>
        <div class="form-item">
          <el-button type="primary" @click="handleSubmit(3)">提交</el-button>
        </div>
      </div>
    </div>

    <div class="submit-all">
      <el-button type="success" size="large" @click="handleSubmitAll">提交所有志愿</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { saveApplication, getSchoolByCode, getMajorByCode } from '@/api/application'
import { getCurrentStudentId } from '@/api/user'

const router = useRouter()

// 学生ID
const studentId = ref('')

// 登录状态检查
const checkLoginStatus = () => {
  const isLogin = localStorage.getItem('isLogin') === 'true'
  const token = localStorage.getItem('token')
  
  if (!isLogin || !token) {
    ElMessage.warning('您尚未登录或登录已过期，请先登录')
    router.push('/login')
    return false
  }
  
  // 检查是否为学生用户
  const userRole = localStorage.getItem('userRole')
  if (userRole !== 'student') {
    ElMessage.warning('只有学生用户才能填报志愿')
    return false
  }
  
  return true
}

// 获取当前学生ID
const fetchCurrentStudentId = async () => {
  try {
    // 先从localStorage获取
    let id = localStorage.getItem('studentId')
    
    // 如果没有，则从后端获取
    if (!id) {
      const res = await getCurrentStudentId()
      if (res.code === 200 && res.data) {
        id = res.data
        localStorage.setItem('studentId', id)
      }
    }
    
    if (!id) {
      ElMessage.warning('无法获取学生信息，请确认您是学生用户')
      return false
    }
    
    studentId.value = id
    return true
  } catch (error) {
    console.error('获取学生ID失败', error)
    ElMessage.error('获取学生信息失败，请重新登录')
    return false
  }
}

// 页面加载时检查登录状态和获取学生ID
onMounted(async () => {
  if (checkLoginStatus()) {
    await fetchCurrentStudentId()
  }
})

// 志愿表单数据
const volunteerForm = reactive({
  first: {
    universityId: '',
    universityName: '',
    majorName: '',
    majorId: '',
    volunteerOrder: 1
  },
  second: {
    universityId: '',
    universityName: '',
    majorName: '',
    majorId: '',
    volunteerOrder: 2
  },
  third: {
    universityId: '',
    universityName: '',
    majorName: '',
    majorId: '',
    volunteerOrder: 3
  }
})

// 根据志愿序号获取对应的志愿对象
const getVolunteerByPriority = (priority) => {
  switch (priority) {
    case 1: return volunteerForm.first
    case 2: return volunteerForm.second
    case 3: return volunteerForm.third
    default: return null
  }
}

// 院校代码变更事件
const handleSchoolCodeChange = async (priority) => {
  const volunteer = getVolunteerByPriority(priority)
  if (!volunteer || !volunteer.universityId) return
  
  try {
    // 查询院校信息
    const res = await getSchoolByCode(volunteer.universityId)
    if (res.code === 200 && res.data) {
      volunteer.universityName = res.data.universityName
    } else {
      ElMessage.warning(res.message || '获取院校信息失败')
      volunteer.universityName = ''
    }
  } catch (error) {
    console.error('获取院校信息失败', error)
    ElMessage.error('获取院校信息失败，请检查网络或服务器状态')
    volunteer.universityName = ''
  }
}

// 专业代码变更事件
const handleMajorCodeChange = async (priority) => {
  const volunteer = getVolunteerByPriority(priority)
  if (!volunteer || !volunteer.majorId) return
  
  try {
    // 查询专业信息
    const res = await getMajorByCode(volunteer.majorId)
    if (res.code === 200 && res.data) {
      volunteer.majorName = res.data.majorName
    } else {
      ElMessage.warning(res.message || '获取专业信息失败')
      volunteer.majorName = ''
    }
  } catch (error) {
    console.error('获取专业信息失败', error)
    ElMessage.error('获取专业信息失败，请检查网络或服务器状态')
    volunteer.majorName = ''
  }
}

// 提交单个志愿
const handleSubmit = async (priority) => {
  // 检查登录状态
  if (!checkLoginStatus()) return
  
  // 确保有学生ID
  if (!studentId.value && !(await fetchCurrentStudentId())) {
    return
  }``
  
  const volunteer = getVolunteerByPriority(priority)
  
  // 表单验证
  if (!volunteer.universityId || !volunteer.universityName || !volunteer.majorId || !volunteer.majorName) {
    ElMessage.warning('请填写完整的志愿信息')
    return
  }
  
  // 构造提交数据
  const submitData = {
    volunteerOrder: volunteer.volunteerOrder,
    universityId: volunteer.universityId,
    majorId: volunteer.majorId,
    studentId: studentId.value
  }
  
  console.log('准备提交志愿数据:', submitData)
  
  // 提交数据
  try {
    const res = await saveApplication(submitData)
    console.log('志愿提交响应:', res)
    
    if (res && res.code === 200) {
      ElMessage.success('志愿提交成功')
    } else {
      // 处理后端未返回值或返回错误的情况
      if (!res) {
        ElMessage.error('志愿提交失败：未收到服务器响应')
      } else {
        ElMessage.error(res.message || '志愿提交失败')
      }
    }
  } catch (error) {
    console.error('志愿提交错误', error)
    ElMessage.error('志愿提交失败，请检查网络或服务器状态')
  }
}

// 提交所有志愿
const handleSubmitAll = async () => {
  // 检查登录状态
  if (!checkLoginStatus()) return
  
  // 确保有学生ID
  if (!studentId.value && !(await fetchCurrentStudentId())) {
    return
  }
  
  // 检查所有志愿是否都填写完整
  const volunteers = [volunteerForm.first, volunteerForm.second, volunteerForm.third]
  const isValid = volunteers.every(v => v.universityId && v.universityName && v.majorId && v.majorName)
  
  if (!isValid) {
    ElMessage.warning('请填写完整的志愿信息')
    return
  }
  
  // 构造提交数据
  const applicationData = volunteers.map(v => ({
    volunteerOrder: v.volunteerOrder,
    universityId: v.universityId,
    majorId: v.majorId,
    studentId: studentId.value
  }))
  
  console.log('准备提交所有志愿数据:', applicationData)
  
  // 提交数据
  try {
    const results = await Promise.all(applicationData.map(data => saveApplication(data)))
    console.log('所有志愿提交响应:', results)
    
    // 检查结果是否都存在且成功
    const allSuccess = results.every(res => res && res.code === 200)
    if (allSuccess) {
      ElMessage.success('所有志愿提交成功')
    } else {
      // 统计成功和失败的数量
      const successCount = results.filter(res => res && res.code === 200).length
      ElMessage.warning(`${successCount}个志愿提交成功，${results.length - successCount}个提交失败，请重试`)
    }
  } catch (error) {
    console.error('志愿提交错误', error)
    ElMessage.error('志愿提交失败，请检查网络或服务器状态')
  }
}
</script>

<style scoped>
.application-apply-container {
  padding: 10px;
  background-color: #f5f7fa;
}

.volunteer-section {
  margin-bottom: 30px;
  background-color: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
  color: #303133;
  border-bottom: 1px solid #ebeef5;
  padding-bottom: 10px;
}

.volunteer-form {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.form-item {
  margin-right: 10px;
  margin-bottom: 15px;
  width: 160px;
}

.form-item:last-child {
  margin-right: 0;
}

.submit-all {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
 
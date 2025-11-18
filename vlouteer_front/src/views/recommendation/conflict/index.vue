<template>
  <div class="conflict-detection-container">
    <div class="page-header">
      <h2>志愿冲突检测</h2>
      <p class="description">
        检测您填报的志愿是否存在梯度不合理、志愿倒挂等问题
      </p>
    </div>
    
    <div class="search-section">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="学生编号">
          <el-input 
            v-model="queryParams.studentId" 
            placeholder="请输入学生编号" 
            clearable 
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">检测冲突</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="studentInfo.exists" class="student-info">
      <el-alert
        title="学生志愿信息"
        type="info"
        :closable="false">
        <template #default>
          <div class="student-stats">
            <div class="stat-item">
              <span class="stat-label">学生ID:</span>
              <span class="stat-value">{{ queryParams.studentId }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">志愿数量:</span>
              <span class="stat-value">{{ studentInfo.volunteerCount }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">匹配的历史录取数据:</span>
              <span class="stat-value">{{ studentInfo.admissionCount }}</span>
            </div>
          </div>
        </template>
      </el-alert>
    </div>
    
    <!-- 志愿信息表格 -->
    <div class="table-section" v-if="volunteerData.length > 0">
      <h3>已填报志愿</h3>
      <el-table
        :data="volunteerData"
        stripe
        border
        style="width: 100%; margin-top: 10px">
        <el-table-column prop="volunteer_order" label="志愿顺序" width="100" />
        <el-table-column prop="university_name" label="院校名称" min-width="180" />
        <el-table-column prop="major_name" label="专业名称" min-width="180" />
        <el-table-column prop="year" label="数据年份" width="100" />
        <el-table-column prop="admission_number" label="录取分数" width="100" />
      </el-table>
    </div>
    
    <!-- 冲突检测结果表格 -->
    <div class="table-section" v-if="conflictData.length > 0">
      <h3>冲突检测结果</h3>
      <el-table
        :data="conflictData"
        stripe
        borde
        style="width: 100%; margin-top: 10px">
        <el-table-column prop="order1" label="志愿1顺序" width="100" />
        <el-table-column prop="uni1" label="志愿1院校" min-width="150" />
        <el-table-column prop="major1" label="志愿1专业" min-width="150" />
        <el-table-column prop="score1" label="志愿1分数" width="100" />
        <el-table-column prop="order2" label="志愿2顺序" width="100" />
        <el-table-column prop="uni2" label="志愿2院校" min-width="150" />
        <el-table-column prop="major2" label="志愿2专业" min-width="150" />
        <el-table-column prop="score2" label="志愿2分数" width="100" />
        <el-table-column prop="advice" label="问题建议" min-width="150">
          <template #default="scope">
            <el-tag :type="getTagType(scope.row.advice)">{{ scope.row.advice }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
    <div class="conflict-summary" v-if="conflictData.length > 0">
      <el-alert
        :title="getSummaryTitle()"
        :type="getSummaryType()"
        :closable="false"
        show-icon>
        <template #default>
          <div class="summary-content">
            {{ getSummaryContent() }}
          </div>
        </template>
      </el-alert>
    </div>
    
    <div class="empty-block" v-if="!loading && volunteerData.length === 0 && queryParams.studentId">
      <el-empty description="未找到该学生的志愿信息，请先填报志愿" />
    </div>
    
    <div class="empty-block" v-if="!loading && !queryParams.studentId">
      <el-empty description="请输入学生编号进行志愿冲突检测" />
    </div>
    
    <el-dialog
      v-model="dialogVisible"
      title="志愿调整建议"
      width="50%">
      <div class="suggestion-content">
        <h4>存在的问题：</h4>
        <p>{{ currentProblem }}</p>
        
        <h4>调整建议：</h4>
        <ul>
          <li v-for="(suggestion, index) in suggestions" :key="index">
            {{ suggestion }}
          </li>
        </ul>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="goToVolunteerEdit">
            去调整志愿
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { checkVolunteerConflict } from '@/api/recommendation'
import { useRouter } from 'vue-router'

const router = useRouter()

// 查询参数
const queryParams = reactive({
  studentId: ''
})

// 表格数据
const volunteerData = ref([])
const conflictData = ref([])
const loading = ref(false)
const studentInfo = reactive({
  exists: false,
  volunteerCount: 0,
  admissionCount: 0
})

// 弹窗相关
const dialogVisible = ref(false)
const currentProblem = ref('')
const suggestions = ref([])

// 查询数据
const handleQuery = async () => {
  if (!queryParams.studentId) {
    ElMessage.warning('请输入学生编号')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用，使用测试数据
    // const res = await checkVolunteerConflict(queryParams.studentId)
    
    // 使用测试数据
    const mockData = {
      code: 200,
      message: '操作成功',
      data: {
        student_exists: 1,
        volunteer_count: 3,
        admission_count: 3,
        year_match_count: 2,
        volunteers: [
          {
            volunteer_order: 1,
            university_name: '北京大学',
            major_name: '计算机科学与技术',
            year: 2022,
            admission_number: 680
          },
          {
            volunteer_order: 2,
            university_name: '清华大学',
            major_name: '软件工程',
            year: 2022,
            admission_number: 685
          },
          {
            volunteer_order: 3,
            university_name: '浙江大学',
            major_name: '人工智能',
            year: 2022,
            admission_number: 660
          }
        ],
        conflicts: [
          {
            order1: 1,
            uni1: '北京大学',
            major1: '计算机科学与技术',
            score1: 680,
            order2: 2,
            uni2: '清华大学',
            major2: '软件工程',
            score2: 685,
            advice: '风险：志愿倒挂'
          },
          {
            order1: 2,
            uni1: '清华大学',
            major1: '软件工程',
            score1: 685,
            order2: 3,
            uni2: '浙江大学',
            major2: '人工智能',
            score2: 660,
            advice: '提示：梯度不足'
          }
        ]
      }
    };
    
    const res = mockData;
    
    if (res.code === 200) {
      // 设置学生信息
      studentInfo.exists = res.data.student_exists > 0
      studentInfo.volunteerCount = res.data.volunteer_count
      studentInfo.admissionCount = res.data.admission_count
      
      // 设置志愿数据
      volunteerData.value = res.data.volunteers || []
      
      // 设置冲突数据
      conflictData.value = res.data.conflicts || []
      
      if (volunteerData.value.length === 0) {
        ElMessage.warning('该学生尚未填报志愿')
      } else if (conflictData.value.length === 0) {
        ElMessage.success('未检测到志愿冲突，志愿填报合理')
      } else {
        ElMessage.warning(`检测到 ${conflictData.value.length} 处志愿填报问题，请查看详情`)
      }
    } else {
      ElMessage.error(res.message || '查询失败')
      resetData()
    }
  } catch (error) {
    console.error('检测志愿冲突失败', error)
    ElMessage.error('检测志愿冲突失败，请检查网络或服务器状态')
    resetData()
  } finally {
    loading.value = false
  }
}

// 重置数据
const resetData = () => {
  volunteerData.value = []
  conflictData.value = []
  studentInfo.exists = false
  studentInfo.volunteerCount = 0
  studentInfo.admissionCount = 0
}

// 获取标签类型
const getTagType = (advice) => {
  if (advice.includes('风险')) return 'danger'
  if (advice.includes('提示')) return 'warning'
  return 'success'
}

// 获取冲突总结标题
const getSummaryTitle = () => {
  const riskCount = conflictData.value.filter(item => item.advice.includes('风险')).length
  const warningCount = conflictData.value.filter(item => item.advice.includes('提示')).length
  
  if (riskCount > 0) {
    return `检测到 ${riskCount} 处严重问题，${warningCount} 处潜在问题`
  } else if (warningCount > 0) {
    return `检测到 ${warningCount} 处潜在问题`
  } else {
    return '志愿填报合理'
  }
}

// 获取冲突总结类型
const getSummaryType = () => {
  const hasRisk = conflictData.value.some(item => item.advice.includes('风险'))
  const hasWarning = conflictData.value.some(item => item.advice.includes('提示'))
  
  if (hasRisk) return 'error'
  if (hasWarning) return 'warning'
  return 'success'
}

// 获取冲突总结内容
const getSummaryContent = () => {
  const hasRisk = conflictData.value.some(item => item.advice.includes('风险'))
  const hasWarning = conflictData.value.some(item => item.advice.includes('提示'))
  
  if (hasRisk) {
    return '您的志愿填报存在严重问题，可能会影响录取结果，建议立即调整志愿顺序。'
  } else if (hasWarning) {
    return '您的志愿填报存在潜在问题，建议适当调整志愿顺序，提高录取成功率。'
  } else {
    return '您的志愿填报合理，预祝录取成功！'
  }
}

// 显示建议弹窗
const showSuggestionDialog = (row) => {
  currentProblem.value = row.advice
  
  if (row.advice.includes('志愿倒挂')) {
    suggestions.value = [
      `建议调整志愿顺序，将 ${row.uni2} 的 ${row.major2} 专业（分数线${row.score2}分）放在 ${row.uni1} 的 ${row.major1} 专业（分数线${row.score1}分）之前`,
      '或者考虑用其他更合适的专业替换其中一个志愿'
    ]
  } else if (row.advice.includes('梯度不足')) {
    suggestions.value = [
      `当前两个志愿的分数线梯度较小，仅相差 ${Math.abs(row.score1 - row.score2)} 分`,
      '建议适当增加梯度差异，选择分数线差异更明显的专业，提高录取保障'
    ]
  } else {
    suggestions.value = ['您的志愿填报合理，无需调整']
  }
  
  dialogVisible.value = true
}

// 跳转到志愿编辑页面
const goToVolunteerEdit = () => {
  router.push('/application/apply')
  dialogVisible.value = false
}
</script>

<style scoped>
.conflict-detection-container {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0 0 10px 0;
  font-size: 22px;
  color: #303133;
}

.description {
  color: #606266;
  font-size: 14px;
  margin: 0;
}

.search-section {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.student-info {
  margin-bottom: 20px;
}

.student-stats {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
}

.stat-label {
  font-weight: bold;
  margin-right: 5px;
}

.table-section {
  margin-bottom: 20px;
}

.table-section h3 {
  margin: 20px 0 10px 0;
  font-size: 18px;
  color: #303133;
}

.conflict-summary {
  margin: 20px 0;
}

.summary-content {
  margin-top: 10px;
  font-size: 14px;
}

.suggestion-content h4 {
  margin: 10px 0;
  color: #303133;
}

.suggestion-content ul {
  padding-left: 20px;
}

.suggestion-content li {
  margin-bottom: 8px;
}

.empty-block {
  margin-top: 40px;
}
</style> 
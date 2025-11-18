<template>
  <div class="volunteer-recommendation-container">
    <div class="page-header">
      <h2>智能志愿推荐</h2>
      <p class="description">
        基于您的高考成绩和历年录取数据，为您推荐最适合的院校和专业组合
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
          <el-button type="primary" @click="handleQuery">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="studentScore" class="student-info">
      <el-alert
        title="学生信息"
        type="info"
        :closable="false">
        <template #default>
          <div class="student-score">
            <span>高考分数：<strong>{{ studentScore }}</strong></span>
          </div>
        </template>
      </el-alert>
    </div>
    
    <el-table
      v-loading="loading"
      :data="tableData"
      stripe
      border
      style="width: 100%; margin-top: 20px">
      <el-table-column prop="university_name" label="院校名称" min-width="180" />
      <el-table-column prop="major_name" label="专业名称" min-width="180" />
      <el-table-column prop="last_year_admission" label="去年录取分数" min-width="120" />
      <el-table-column prop="score_difference" label="分数差距" min-width="100">
        <template #default="scope">
          <span :class="getScoreDifferenceClass(scope.row.score_difference)">
            {{ formatScoreDifference(scope.row.score_difference) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="推荐指数" min-width="150">
        <template #default="scope">
          <el-rate
            v-model="scope.row.recommendationRate"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button 
            type="primary" 
            link 
            @click="handleAddToVolunteer(scope.row)">
            加入志愿
          </el-button>
          <el-button 
            type="success" 
            link 
            @click="handleViewDetails(scope.row)">
            查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="empty-block" v-if="!loading && tableData.length === 0">
      <el-empty description="暂无推荐数据，请输入学生编号进行查询" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getVolunteerRecommendation } from '@/api/recommendation'
import { saveApplication } from '@/api/application'

// 查询参数
const queryParams = reactive({
  studentId: ''
})

// 表格数据
const tableData = ref([])
const loading = ref(false)
const studentScore = ref(null)

// 查询数据
const handleQuery = async () => {
  if (!queryParams.studentId) {
    ElMessage.warning('请输入学生编号')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用，使用测试数据
    // const res = await getVolunteerRecommendation(queryParams.studentId)
    
    // 使用测试数据
    const mockData = {
      code: 200,
      message: '操作成功',
      data: {
        student_score: 650.5,
        recommendations: [
          {
            university_name: '北京大学',
            major_name: '计算机科学与技术',
            last_year_admission: 680,
            score_difference: -29.5
          },
          {
            university_name: '清华大学',
            major_name: '软件工程',
            last_year_admission: 685,
            score_difference: -34.5
          },
          {
            university_name: '浙江大学',
            major_name: '人工智能',
            last_year_admission: 660,
            score_difference: -9.5
          },
          {
            university_name: '复旦大学',
            major_name: '数据科学与大数据技术',
            last_year_admission: 655,
            score_difference: -4.5
          },
          {
            university_name: '上海交通大学',
            major_name: '信息安全',
            last_year_admission: 670,
            score_difference: -19.5
          },
          {
            university_name: '南京大学',
            major_name: '软件工程',
            last_year_admission: 645,
            score_difference: 5.5
          },
          {
            university_name: '武汉大学',
            major_name: '计算机科学与技术',
            last_year_admission: 635,
            score_difference: 15.5
          }
        ]
      }
    };
    
    const res = mockData;
    
    if (res.code === 200) {
      // 设置学生分数
      if (res.data.student_score) {
        studentScore.value = res.data.student_score
      }
      
      // 设置推荐数据
      tableData.value = res.data.recommendations.map(item => {
        // 计算推荐指数（1-5分）
        const scoreDiff = Math.abs(item.score_difference)
        let recommendationRate = 5
        if (scoreDiff > 50) recommendationRate = 1
        else if (scoreDiff > 30) recommendationRate = 2
        else if (scoreDiff > 20) recommendationRate = 3
        else if (scoreDiff > 10) recommendationRate = 4
        
        return {
          ...item,
          recommendationRate
        }
      })
    } else {
      ElMessage.error(res.message || '查询失败')
      tableData.value = []
    }
  } catch (error) {
    console.error('获取推荐数据失败', error)
    ElMessage.error('获取推荐数据失败，请检查网络或服务器状态')
    tableData.value = []
  } finally {
    loading.value = false
  }
}

// 格式化分数差距
const formatScoreDifference = (diff) => {
  if (diff > 0) {
    return `+${diff}分`
  } else if (diff < 0) {
    return `${diff}分`
  } else {
    return '0分'
  }
}

// 获取分数差距的样式类
const getScoreDifferenceClass = (diff) => {
  if (diff > 20) return 'score-high'
  if (diff < -20) return 'score-low'
  return 'score-medium'
}

// 添加到志愿
const handleAddToVolunteer = (row) => {
  ElMessageBox.confirm(`确认将 ${row.university_name} 的 ${row.major_name} 专业添加到志愿中吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info'
  }).then(() => {
    // 提取院校代码和专业代码
    const universityId = row.university_id || extractId(row.university_name)
    const majorId = row.major_id || extractId(row.major_name)
    
    // 保存志愿
    saveApplication({
      studentId: queryParams.studentId,
      universityId,
      majorId,
      volunteerOrder: 1 // 默认设为第一志愿，用户可以在志愿填报页面修改
    }).then(res => {
      if (res.code === 200) {
        ElMessage.success('已成功添加到志愿')
      } else {
        ElMessage.error(res.message || '添加志愿失败')
      }
    }).catch(error => {
      console.error('添加志愿错误', error)
      ElMessage.error('添加志愿失败，请检查网络或服务器状态')
    })
  }).catch(() => {})
}

// 从名称中提取ID（如果后端没有返回ID）
const extractId = (name) => {
  // 简单实现，实际应该调用后端接口获取ID
  return name.substring(0, 6).replace(/\D/g, '')
}

// 查看详情
const handleViewDetails = (row) => {
  // 跳转到专业详情页面或显示详情弹窗
  ElMessage.info('查看详情功能开发中...')
}
</script>

<style scoped>
.volunteer-recommendation-container {
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

.student-score {
  font-size: 16px;
}

.score-high {
  color: #f56c6c;
  font-weight: bold;
}

.score-medium {
  color: #e6a23c;
  font-weight: bold;
}

.score-low {
  color: #67c23a;
  font-weight: bold;
}

.empty-block {
  margin-top: 40px;
}
</style>
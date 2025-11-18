<template>
  <div class="probability-prediction-container">
    <div class="page-header">
      <h2>录取概率预测</h2>
      <p class="description">
        基于历年录取数据和您的高考成绩，预测被特定院校和专业录取的概率
      </p>
    </div>
    
    <div class="search-section">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="学生编号">
          <el-input 
            v-model="queryParams.studentId" 
            placeholder="请输入学生编号" 
            clearable 
          />
        </el-form-item>
        <el-form-item label="院校代码">
          <el-input 
            v-model="queryParams.universityId" 
            placeholder="请输入院校代码" 
            clearable 
          />
        </el-form-item>
        <el-form-item label="专业代码">
          <el-input 
            v-model="queryParams.majorId" 
            placeholder="请输入专业代码" 
            clearable 
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">预测概率</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="predictionResult" class="result-section">
      <el-card class="prediction-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>录取概率预测结果</h3>
          </div>
        </template>
        
        <div class="university-info">
          <div class="info-item">
            <span class="label">院校名称：</span>
            <span class="value">{{ predictionResult.university_name }}</span>
          </div>
          <div class="info-item">
            <span class="label">专业名称：</span>
            <span class="value">{{ predictionResult.major_name }}</span>
          </div>
        </div>
        
        <el-divider />
        
        <div class="score-comparison">
          <div class="score-item">
            <div class="score-label">您的分数</div>
            <div class="score-value">{{ predictionResult.your_score }}</div>
          </div>
          <div class="score-arrow">
            <el-icon :size="30" color="#409EFF"><ArrowRight /></el-icon>
          </div>
          <div class="score-item">
            <div class="score-label">平均录取分数</div>
            <div class="score-value">{{ predictionResult.avg_admission_score }}</div>
          </div>
          <div class="score-diff" :class="getScoreDiffClass()">
            <span>{{ getScoreDiffText() }}</span>
          </div>
        </div>
        
        <el-divider />
        
        <div class="probability-section">
          <div class="probability-title">录取概率</div>
          <el-progress 
            :percentage="predictionResult.admission_probability" 
            :color="getProbabilityColor()" 
            :status="getProbabilityStatus()"
            :stroke-width="20"
            :format="percentFormat"
          />
          <div class="probability-level" :class="getProbabilityLevelClass()">
            {{ predictionResult.probability_level }}
          </div>
        </div>
        
        <el-divider />
        
        <div class="data-info">
          <div class="info-item">
            <span class="label">分数标准差：</span>
            <span class="value">{{ predictionResult.score_std_dev }}</span>
          </div>
          <div class="info-item">
            <span class="label">历史数据量：</span>
            <span class="value">{{ predictionResult.historical_data_count }} 条</span>
          </div>
        </div>
        
        <div class="suggestion-section">
          <h4>录取建议</h4>
          <p>{{ getSuggestion() }}</p>
        </div>
      </el-card>
    </div>
    
    <div class="empty-block" v-if="!loading && !predictionResult">
      <el-empty description="请输入学生编号、院校代码和专业代码进行概率预测" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'
import { predictAdmissionProbability } from '@/api/recommendation'

// 查询参数
const queryParams = reactive({
  studentId: '',
  universityId: '',
  majorId: ''
})

// 预测结果
const predictionResult = ref(null)
const loading = ref(false)

// 查询数据
const handleQuery = async () => {
  if (!queryParams.studentId || !queryParams.universityId || !queryParams.majorId) {
    ElMessage.warning('请输入完整的查询信息')
    return
  }
  
  loading.value = true
  try {
    // 调用API接口获取数据
    const res = await predictAdmissionProbability(
      queryParams.studentId,
      queryParams.universityId,
      queryParams.majorId
    )
    
    if (res.code === 200 && res.data) {
      predictionResult.value = res.data
      ElMessage.success('预测完成')
    } else {
      ElMessage.error(res.message || '预测失败')
      predictionResult.value = null
    }
  } catch (error) {
    console.error('预测录取概率失败', error)
    ElMessage.error('预测录取概率失败，请检查网络或服务器状态')
    predictionResult.value = null
  } finally {
    loading.value = false
  }
}

// 格式化百分比
const percentFormat = (percentage) => {
  return percentage + '%'
}

// 获取分数差距文本
const getScoreDiffText = () => {
  if (!predictionResult.value) return ''
  
  const diff = predictionResult.value.your_score - predictionResult.value.avg_admission_score
  if (diff > 0) {
    return `高出平均分 ${diff.toFixed(1)} 分`
  } else if (diff < 0) {
    return `低于平均分 ${Math.abs(diff).toFixed(1)} 分`
  } else {
    return '与平均分持平'
  }
}

// 获取分数差距样式类
const getScoreDiffClass = () => {
  if (!predictionResult.value) return ''
  
  const diff = predictionResult.value.your_score - predictionResult.value.avg_admission_score
  if (diff >= 20) return 'score-high'
  if (diff <= -20) return 'score-low'
  return 'score-medium'
}

// 获取概率颜色
const getProbabilityColor = () => {
  if (!predictionResult.value) return ''
  
  const prob = predictionResult.value.admission_probability
  if (prob >= 80) return '#67C23A'
  if (prob >= 50) return '#E6A23C'
  if (prob >= 20) return '#F56C6C'
  return '#909399'
}

// 获取概率状态
const getProbabilityStatus = () => {
  if (!predictionResult.value) return ''
  
  const prob = predictionResult.value.admission_probability
  if (prob >= 80) return 'success'
  if (prob >= 50) return 'warning'
  if (prob >= 20) return 'exception'
  return ''
}

// 获取概率等级样式类
const getProbabilityLevelClass = () => {
  if (!predictionResult.value) return ''
  
  const level = predictionResult.value.probability_level
  if (level.includes('高')) return 'level-high'
  if (level.includes('中')) return 'level-medium'
  if (level.includes('低')) return 'level-low'
  return 'level-very-low'
}

// 获取录取建议
const getSuggestion = () => {
  if (!predictionResult.value) return ''
  
  const prob = predictionResult.value.admission_probability
  
  if (prob >= 80) {
    return '您的录取概率较高，可以考虑将该专业作为稳妥志愿。'
  } else if (prob >= 50) {
    return '您的录取概率中等，建议将该专业作为较有把握的冲刺志愿。'
  } else if (prob >= 20) {
    return '您的录取概率较低，建议将该专业作为冲刺志愿，同时准备更稳妥的备选方案。'
  } else {
    return '您的录取概率极低，建议考虑其他更适合您分数的专业选择。'
  }
}
</script>

<style scoped>
.probability-prediction-container {
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

.result-section {
  margin-top: 30px;
}

.prediction-card {
  width: 100%;
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h3 {
  margin: 0;
  font-size: 18px;
  color: #303133;
}

.university-info {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 15px;
}

.info-item {
  margin-right: 30px;
  margin-bottom: 10px;
}

.label {
  font-weight: bold;
  color: #606266;
}

.value {
  color: #303133;
}

.score-comparison {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
}

.score-item {
  text-align: center;
  width: 120px;
}

.score-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 5px;
}

.score-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.score-arrow {
  margin: 0 20px;
}

.score-diff {
  margin-left: 20px;
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 14px;
}

.score-high {
  background-color: #f0f9eb;
  color: #67c23a;
}

.score-medium {
  background-color: #fdf6ec;
  color: #e6a23c;
}

.score-low {
  background-color: #fef0f0;
  color: #f56c6c;
}

.probability-section {
  margin: 20px 0;
  text-align: center;
}

.probability-title {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 15px;
}

.probability-level {
  margin-top: 15px;
  font-size: 18px;
  font-weight: bold;
  padding: 5px 0;
}

.level-high {
  color: #67c23a;
}

.level-medium {
  color: #e6a23c;
}

.level-low {
  color: #f56c6c;
}

.level-very-low {
  color: #909399;
}

.data-info {
  display: flex;
  flex-wrap: wrap;
  margin: 15px 0;
}

.suggestion-section {
  margin-top: 20px;
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
}

.suggestion-section h4 {
  margin: 0 0 10px 0;
  font-size: 16px;
  color: #303133;
}

.suggestion-section p {
  margin: 0;
  color: #606266;
  line-height: 1.6;
}

.empty-block {
  margin-top: 40px;
}
</style> 
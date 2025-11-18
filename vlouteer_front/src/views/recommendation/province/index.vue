<template>
  <div class="province-score-container">
    <div class="page-header">
      <h2>省份分数线分析</h2>
      <p class="description">
        分析特定省份和学科门类的分数线趋势，帮助您了解录取难度和竞争情况
      </p>
    </div>
    
    <div class="search-section">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="省份" style="width: 200px;">
          <el-select v-model="queryParams.provinceId" placeholder="请选择省份" clearable>
            <el-option
              v-for="item in provinceOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学科门类" style="width: 250px;">
          <el-select 
            v-model="queryParams.disciplineCategoryId" 
            placeholder="请选择学科门类" 
            clearable>
            <el-option
              v-for="item in categoryOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">分析分数线</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="provinceData" class="result-section">
      <el-card class="province-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>{{ provinceData.province_name }} - {{ provinceData.category_name }} 分数线分析</h3>
            <el-tag :type="getDifficultyTagType()">{{ provinceData.admission_difficulty }}</el-tag>
          </div>
        </template>
        
        <div class="basic-info">
          <div class="info-item">
            <span class="label">省份：</span>
            <span class="value">{{ provinceData.province_name }}</span>
          </div>
          <div class="info-item">
            <span class="label">地区：</span>
            <span class="value">{{ provinceData.region }}</span>
          </div>
          <div class="info-item">
            <span class="label">学科门类：</span>
            <span class="value">{{ provinceData.category_name }}</span>
          </div>
          <div class="info-item">
            <span class="label">录取难度：</span>
            <span class="value difficulty" :class="getDifficultyClass()">
              {{ provinceData.admission_difficulty }}
            </span>
          </div>
        </div>
        
        <el-divider />
        
        <div class="chart-section">
          <div class="chart-title">分数线趋势图 (近三年)</div>
          <div class="chart-container">
            <el-row :gutter="20">
              <el-col :span="8">
                <div class="score-card">
                  <div class="score-title">最低分数线</div>
                  <div class="score-trend">
                    <div class="trend-item">
                      <div class="year">前年</div>
                      <div class="score">{{ provinceData.min_score_two_years_ago }}</div>
                    </div>
                    <div class="trend-arrow">
                      <el-icon :size="20" color="#909399"><ArrowRight /></el-icon>
                    </div>
                    <div class="trend-item">
                      <div class="year">去年</div>
                      <div class="score">{{ provinceData.min_score_last_year }}</div>
                    </div>
                    <div class="trend-arrow">
                      <el-icon :size="20" color="#909399"><ArrowRight /></el-icon>
                    </div>
                    <div class="trend-item">
                      <div class="year">今年</div>
                      <div class="score">{{ provinceData.min_score_current_year }}</div>
                    </div>
                  </div>
                  <div class="trend-change" :class="getScoreTrendClass('min')">
                    {{ getScoreTrendText('min') }}
                  </div>
                </div>
              </el-col>
              
              <el-col :span="8">
                <div class="score-card">
                  <div class="score-title">平均分数线</div>
                  <div class="score-trend">
                    <div class="trend-item">
                      <div class="year">前年</div>
                      <div class="score">{{ provinceData.avg_score_two_years_ago }}</div>
                    </div>
                    <div class="trend-arrow">
                      <el-icon :size="20" color="#909399"><ArrowRight /></el-icon>
                    </div>
                    <div class="trend-item">
                      <div class="year">去年</div>
                      <div class="score">{{ provinceData.avg_score_last_year }}</div>
                    </div>
                    <div class="trend-arrow">
                      <el-icon :size="20" color="#909399"><ArrowRight /></el-icon>
                    </div>
                    <div class="trend-item">
                      <div class="year">今年</div>
                      <div class="score">{{ provinceData.avg_score_current_year }}</div>
                    </div>
                  </div>
                  <div class="trend-change" :class="getScoreTrendClass('avg')">
                    {{ getScoreTrendText('avg') }}
                  </div>
                </div>
              </el-col>
              
              <el-col :span="8">
                <div class="score-card">
                  <div class="score-title">最高分数线</div>
                  <div class="score-trend">
                    <div class="trend-item">
                      <div class="year">前年</div>
                      <div class="score">{{ provinceData.max_score_two_years_ago }}</div>
                    </div>
                    <div class="trend-arrow">
                      <el-icon :size="20" color="#909399"><ArrowRight /></el-icon>
                    </div>
                    <div class="trend-item">
                      <div class="year">去年</div>
                      <div class="score">{{ provinceData.max_score_last_year }}</div>
                    </div>
                    <div class="trend-arrow">
                      <el-icon :size="20" color="#909399"><ArrowRight /></el-icon>
                    </div>
                    <div class="trend-item">
                      <div class="year">今年</div>
                      <div class="score">{{ provinceData.max_score_current_year }}</div>
                    </div>
                  </div>
                  <div class="trend-change" :class="getScoreTrendClass('max')">
                    {{ getScoreTrendText('max') }}
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
        
        <el-divider />
        
        <div class="analysis-section">
          <h4>分数线趋势分析</h4>
          <p class="analysis-text">{{ provinceData.score_trend_analysis }}</p>
        </div>
        
        <div class="universities-section">
          <h4>热门高校</h4>
          <p class="universities-text">{{ provinceData.popular_universities }}</p>
        </div>
        
        <el-divider />
        
        <div class="recommendation-section">
          <h4>报考建议</h4>
          <div class="recommendation-content">
            <el-alert
              :title="provinceData.recommendation"
              type="info"
              :closable="false"
              show-icon>
            </el-alert>
          </div>
        </div>
      </el-card>
    </div>
    
    <div class="empty-block" v-if="!loading && !provinceData">
      <el-empty description="请选择省份和学科门类进行分数线分析" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'
import { provinceScoreData } from '@/mock/recommendation'

// 查询参数
const queryParams = reactive({
  provinceId: '',
  disciplineCategoryId: ''
})

// 分析结果
const provinceData = ref(null)
const loading = ref(false)

// 省份选项
const provinceOptions = [
  { value: '1', label: '北京市' },
  { value: '2', label: '上海市' },
  { value: '3', label: '江苏省' },
  { value: '4', label: '浙江省' },
  { value: '5', label: '山东省' },
  { value: '6', label: '广东省' },
  { value: '7', label: '湖北省' },
  { value: '8', label: '四川省' }
]

// 学科门类选项 - 使用静态数据
const categoryOptions = ref([
  { value: '1', label: '哲学' },
  { value: '2', label: '经济学' },
  { value: '3', label: '法学' },
  { value: '4', label: '教育学' },
  { value: '5', label: '文学' },
  { value: '6', label: '历史学' },
  { value: '7', label: '理学' },
  { value: '8', label: '工学' },
  { value: '9', label: '农学' },
  { value: '10', label: '医学' },
  { value: '11', label: '管理学' },
  { value: '12', label: '艺术学' }
])

// 查询数据
const handleQuery = async () => {
  if (!queryParams.provinceId || !queryParams.disciplineCategoryId) {
    ElMessage.warning('请选择省份和学科门类')
    return
  }
  
  loading.value = true
  try {
    // 直接使用模拟数据
    const res = provinceScoreData
    
    // 使用模拟数据，根据选择更新省份名称和学科门类名称
    if (res.code === 200 && res.data) {
      // 复制数据，而不是直接引用
      const data = JSON.parse(JSON.stringify(res.data))
      
      // 根据选择更新数据的省份和学科门类
      const selectedProvince = provinceOptions.find(p => p.value === queryParams.provinceId)
      if (selectedProvince) {
        data.province_name = selectedProvince.label
        data.province_id = queryParams.provinceId
      }
      
      const selectedCategory = categoryOptions.value.find(c => c.value === queryParams.disciplineCategoryId)
      if (selectedCategory) {
        data.category_name = selectedCategory.label
        data.category_id = queryParams.disciplineCategoryId
      }
      
      provinceData.value = data
      ElMessage.success('分析完成')
    } else {
      ElMessage.error(res.message || '分析失败')
      provinceData.value = null
    }
  } catch (error) {
    console.error('分析省份分数线失败', error)
    ElMessage.error('分析省份分数线失败，请检查网络或服务器状态')
    provinceData.value = null
  } finally {
    loading.value = false
  }
}

// 获取录取难度标签类型
const getDifficultyTagType = () => {
  if (!provinceData.value) return 'info'
  
  const difficulty = provinceData.value.admission_difficulty
  if (difficulty === '高') return 'danger'
  if (difficulty === '中') return 'warning'
  return 'success'
}

// 获取录取难度样式类
const getDifficultyClass = () => {
  if (!provinceData.value) return ''
  
  const difficulty = provinceData.value.admission_difficulty
  if (difficulty === '高') return 'difficulty-high'
  if (difficulty === '中') return 'difficulty-medium'
  return 'difficulty-low'
}

// 获取分数趋势样式类
const getScoreTrendClass = (type) => {
  if (!provinceData.value) return ''
  
  let currentYear, lastYear
  if (type === 'min') {
    currentYear = provinceData.value.min_score_current_year
    lastYear = provinceData.value.min_score_last_year
  } else if (type === 'avg') {
    currentYear = provinceData.value.avg_score_current_year
    lastYear = provinceData.value.avg_score_last_year
  } else {
    currentYear = provinceData.value.max_score_current_year
    lastYear = provinceData.value.max_score_last_year
  }
  
  const diff = currentYear - lastYear
  if (diff > 10) return 'trend-up-large'
  if (diff > 0) return 'trend-up-small'
  if (diff < -10) return 'trend-down-large'
  if (diff < 0) return 'trend-down-small'
  return 'trend-stable'
}

// 获取分数趋势文本
const getScoreTrendText = (type) => {
  if (!provinceData.value) return ''
  
  let currentYear, lastYear
  if (type === 'min') {
    currentYear = provinceData.value.min_score_current_year
    lastYear = provinceData.value.min_score_last_year
  } else if (type === 'avg') {
    currentYear = provinceData.value.avg_score_current_year
    lastYear = provinceData.value.avg_score_last_year
  } else {
    currentYear = provinceData.value.max_score_current_year
    lastYear = provinceData.value.max_score_last_year
  }
  
  const diff = currentYear - lastYear
  if (diff > 10) return `上升明显 (+${diff}分)`
  if (diff > 0) return `小幅上升 (+${diff}分)`
  if (diff < -10) return `下降明显 (${diff}分)`
  if (diff < 0) return `小幅下降 (${diff}分)`
  return '基本稳定 (±0分)'
}
</script>

<style scoped>
.province-score-container {
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

.province-card {
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

.basic-info {
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

.difficulty {
  font-weight: bold;
}

.difficulty-high {
  color: #f56c6c;
}

.difficulty-medium {
  color: #e6a23c;
}

.difficulty-low {
  color: #67c23a;
}

.chart-section {
  margin: 20px 0;
}

.chart-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  color: #303133;
}

.chart-container {
  margin-top: 20px;
}

.score-card {
  background-color: #f5f7fa;
  border-radius: 4px;
  padding: 15px;
  height: 100%;
}

.score-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
  color: #303133;
}

.score-trend {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.trend-item {
  text-align: center;
}

.year {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.score {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.trend-change {
  text-align: center;
  font-size: 14px;
  font-weight: bold;
  padding: 5px 0;
}

.trend-up-large {
  color: #f56c6c;
}

.trend-up-small {
  color: #e6a23c;
}

.trend-stable {
  color: #409eff;
}

.trend-down-small {
  color: #67c23a;
}

.trend-down-large {
  color: #67c23a;
}

.analysis-section,
.universities-section {
  margin: 20px 0;
}

.analysis-section h4,
.universities-section h4,
.recommendation-section h4 {
  margin: 0 0 10px 0;
  font-size: 16px;
  color: #303133;
}

.analysis-text,
.universities-text {
  color: #606266;
  line-height: 1.6;
  margin: 0;
}

.recommendation-section {
  margin: 20px 0;
}

.recommendation-content {
  margin-top: 10px;
}

.empty-block {
  margin-top: 40px;
}
</style> 
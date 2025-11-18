<template>
  <div class="employment-prospects-container">
    <div class="page-header">
      <h2>专业就业前景分析</h2>
      <p class="description">
        分析专业的就业前景、薪资水平和行业需求等信息，助您做出更明智的选择
      </p>
    </div>
    
    <div class="search-section">
      <el-form :inline="true" :model="queryParams" class="demo-form-inline">
        <el-form-item label="专业代码">
          <el-input 
            v-model="queryParams.majorId" 
            placeholder="请输入专业代码" 
            clearable 
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">分析就业前景</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="employmentData" class="result-section">
      <el-card class="employment-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>{{ employmentData.major_name }} 就业前景分析</h3>
            <el-tag type="success">{{ employmentData.employment_status }}</el-tag>
          </div>
        </template>
        
        <div class="basic-info">
          <div class="info-item">
            <span class="label">专业代码：</span>
            <span class="value">{{ employmentData.major_id }}</span>
          </div>
          <div class="info-item">
            <span class="label">学科门类：</span>
            <span class="value">{{ employmentData.discipline_category }}</span>
          </div>
          <div class="info-item">
            <span class="label">学位类型：</span>
            <span class="value">{{ employmentData.degree_type }}</span>
          </div>
          <div class="info-item">
            <span class="label">学制：</span>
            <span class="value">{{ employmentData.schooling_length }} 年</span>
          </div>
        </div>
        
        <el-divider />
        
        <div class="stats-section">
          <div class="stat-card">
            <div class="stat-title">平均就业率</div>
            <el-progress 
              type="dashboard" 
              :percentage="employmentData.average_employment_rate" 
              :color="getEmploymentRateColor()"
              :stroke-width="10"
            />
            <div class="stat-description">
              {{ getEmploymentRateDescription() }}
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-title">平均月薪</div>
            <div class="salary-value">{{ formatSalary(employmentData.average_salary) }}</div>
            <div class="stat-description">
              {{ getSalaryDescription() }}
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-title">行业需求</div>
            <div class="demand-level" :class="getDemandLevelClass()">
              {{ employmentData.demand_level }}
            </div>
            <div class="stat-description">
              {{ getDemandDescription() }}
            </div>
          </div>
        </div>
        
        <el-divider />
        
        <div class="outlook-section">
          <h4>行业前景</h4>
          <p class="outlook-text">{{ employmentData.industry_outlook }}</p>
        </div>
        
        <div class="advice-section">
          <h4>职业建议</h4>
          <p class="advice-text">{{ employmentData.career_advice }}</p>
        </div>
        
        <el-divider />
        
        <div class="related-majors">
          <h4>相关专业推荐</h4>
          <div class="major-tags">
            <el-tag 
              v-for="(major, index) in relatedMajors" 
              :key="index" 
              class="major-tag"
              type="info"
              effect="plain">
              {{ major }}
            </el-tag>
          </div>
        </div>
      </el-card>
    </div>
    
    <div class="empty-block" v-if="!loading && !employmentData">
      <el-empty description="请输入专业代码进行就业前景分析" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { analyzeMajorEmployment } from '@/api/recommendation'

// 查询参数
const queryParams = reactive({
  majorId: ''
})

// 分析结果
const employmentData = ref(null)
const loading = ref(false)

// 相关专业列表
const relatedMajors = computed(() => {
  if (!employmentData.value || !employmentData.value.related_majors) return []
  return employmentData.value.related_majors.split('、')
})

// 查询数据
const handleQuery = async () => {
  if (!queryParams.majorId) {
    ElMessage.warning('请输入专业代码')
    return
  }
  
  loading.value = true
  try {
    // 调用API接口获取数据
    const res = await analyzeMajorEmployment(queryParams.majorId)
    
    if (res.code === 200 && res.data) {
      employmentData.value = res.data
      ElMessage.success('分析完成')
    } else {
      ElMessage.error(res.message || '分析失败')
      employmentData.value = null
    }
  } catch (error) {
    console.error('分析就业前景失败', error)
    ElMessage.error('分析就业前景失败，请检查网络或服务器状态')
    employmentData.value = null
  } finally {
    loading.value = false
  }
}

// 格式化薪资
const formatSalary = (salary) => {
  if (!salary) return '0元'
  return `${salary.toLocaleString()}元/月`
}

// 获取就业率颜色
const getEmploymentRateColor = () => {
  if (!employmentData.value) return ''
  
  const rate = employmentData.value.average_employment_rate
  if (rate >= 90) return '#67C23A'
  if (rate >= 80) return '#E6A23C'
  return '#F56C6C'
}

// 获取就业率描述
const getEmploymentRateDescription = () => {
  if (!employmentData.value) return ''
  
  const rate = employmentData.value.average_employment_rate
  if (rate >= 90) return '就业率极高，毕业生就业形势良好'
  if (rate >= 80) return '就业率较高，大部分毕业生能顺利就业'
  if (rate >= 70) return '就业率一般，需要提前做好就业准备'
  return '就业率较低，就业竞争压力较大'
}

// 获取薪资描述
const getSalaryDescription = () => {
  if (!employmentData.value) return ''
  
  const salary = employmentData.value.average_salary
  if (salary >= 10000) return '薪资水平较高，行业待遇优厚'
  if (salary >= 8000) return '薪资水平中上，具有较好的发展空间'
  if (salary >= 6000) return '薪资水平一般，符合行业平均水准'
  return '薪资水平较低，可能需要积累经验提升薪资'
}

// 获取需求等级样式类
const getDemandLevelClass = () => {
  if (!employmentData.value) return ''
  
  const level = employmentData.value.demand_level
  if (level.includes('高')) return 'demand-high'
  if (level.includes('稳定')) return 'demand-medium'
  return 'demand-low'
}

// 获取需求描述
const getDemandDescription = () => {
  if (!employmentData.value) return ''
  
  const level = employmentData.value.demand_level
  if (level.includes('高')) return '市场对该专业人才需求旺盛，就业机会多'
  if (level.includes('稳定')) return '市场对该专业人才需求稳定，就业前景可靠'
  return '市场对该专业人才需求有限，就业竞争较激烈'
}
</script>

<style scoped>
.employment-prospects-container {
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

.employment-card {
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

.stats-section {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  margin: 20px 0;
}

.stat-card {
  text-align: center;
  width: 200px;
  margin-bottom: 20px;
}

.stat-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  color: #303133;
}

.stat-description {
  margin-top: 10px;
  font-size: 14px;
  color: #606266;
}

.salary-value {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  margin: 15px 0;
}

.demand-level {
  font-size: 20px;
  font-weight: bold;
  padding: 5px 0;
  margin: 15px 0;
}

.demand-high {
  color: #67c23a;
}

.demand-medium {
  color: #e6a23c;
}

.demand-low {
  color: #f56c6c;
}

.outlook-section,
.advice-section {
  margin: 20px 0;
}

.outlook-section h4,
.advice-section h4,
.related-majors h4 {
  margin: 0 0 10px 0;
  font-size: 16px;
  color: #303133;
}

.outlook-text,
.advice-text {
  color: #606266;
  line-height: 1.6;
  margin: 0;
}

.major-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
}

.major-tag {
  margin-right: 10px;
  margin-bottom: 10px;
}

.empty-block {
  margin-top: 40px;
}
</style> 
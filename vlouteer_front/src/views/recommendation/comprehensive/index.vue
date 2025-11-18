<template>
  <div class="comprehensive-analysis-container">
    <div class="page-header">
      <h2>志愿综合分析</h2>
      <p class="description">
        对您的志愿填报进行全方位分析，评估志愿组合的合理性、风险分布和录取可能性
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
          <el-button type="primary" @click="handleQuery">开始分析</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="analysisData" class="result-section">
      <el-card class="overview-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>志愿填报综合评估</h3>
            <el-tag :type="getOverallRatingTagType()">{{ analysisData.overall_rating }}</el-tag>
          </div>
        </template>
        
        <div class="summary-section">
          <div class="student-info">
            <div class="info-item">
              <span class="label">学生姓名：</span>
              <span class="value">{{ analysisData.student_name }}</span>
            </div>
            <div class="info-item">
              <span class="label">高考分数：</span>
              <span class="value score">{{ analysisData.score }}</span>
            </div>
            <div class="info-item">
              <span class="label">省市排名：</span>
              <span class="value">{{ analysisData.rank }}</span>
            </div>
            <div class="info-item">
              <span class="label">志愿数量：</span>
              <span class="value">{{ analysisData.volunteer_count }}</span>
            </div>
          </div>
          
          <el-divider />
          
          <div class="rating-section">
            <div class="rating-title">综合评分</div>
            <div class="rating-items">
              <div class="rating-item">
                <div class="rating-name">合理性</div>
                <el-rate
                  v-model="analysisData.rationality_score"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
                />
              </div>
              <div class="rating-item">
                <div class="rating-name">安全性</div>
                <el-rate
                  v-model="analysisData.safety_score"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
                />
              </div>
              <div class="rating-item">
                <div class="rating-name">梯度分布</div>
                <el-rate
                  v-model="analysisData.gradient_score"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
                />
              </div>
              <div class="rating-item">
                <div class="rating-name">专业匹配</div>
                <el-rate
                  v-model="analysisData.major_match_score"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
                />
              </div>
            </div>
          </div>
        </div>
      </el-card>
      
      <el-card class="volunteers-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>志愿填报详情</h3>
          </div>
        </template>
        
        <el-table
          :data="analysisData.volunteers"
          stripe
          border
          style="width: 100%">
          <el-table-column prop="order" label="志愿顺序" width="100" />
          <el-table-column prop="university_name" label="院校名称" min-width="180" />
          <el-table-column prop="major_name" label="专业名称" min-width="180" />
          <el-table-column prop="last_year_score" label="去年分数线" width="120" />
          <el-table-column prop="score_difference" label="分数差距" width="120">
            <template #default="scope">
              <span :class="getScoreDifferenceClass(scope.row.score_difference)">
                {{ formatScoreDifference(scope.row.score_difference) }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="admission_probability" label="录取概率" width="120">
            <template #default="scope">
              <el-progress
                :percentage="scope.row.admission_probability"
                :color="getProbabilityColor(scope.row.admission_probability)"
                :stroke-width="10"
                :format="percentageFormat"
              />
            </template>
          </el-table-column>
          <el-table-column prop="risk_level" label="风险等级" width="120">
            <template #default="scope">
              <el-tag :type="getRiskLevelType(scope.row.risk_level)">
                {{ scope.row.risk_level }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      
      <el-card class="analysis-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <h3>志愿分析与建议</h3>
          </div>
        </template>
        
        <div class="analysis-content">
          <div class="analysis-section">
            <h4>风险分布</h4>
            <div class="risk-distribution">
              <el-progress
                type="dashboard"
                :percentage="analysisData.risk_distribution.high"
                :color="'#F56C6C'"
                :stroke-width="10"
                :width="120"
              >
                <template #default>
                  <div class="progress-content">
                    <span class="progress-value">{{ analysisData.risk_distribution.high }}%</span>
                    <span class="progress-label">冲刺</span>
                  </div>
                </template>
              </el-progress>
              
              <el-progress
                type="dashboard"
                :percentage="analysisData.risk_distribution.medium"
                :color="'#E6A23C'"
                :stroke-width="10"
                :width="120"
              >
                <template #default>
                  <div class="progress-content">
                    <span class="progress-value">{{ analysisData.risk_distribution.medium }}%</span>
                    <span class="progress-label">稳妥</span>
                  </div>
                </template>
              </el-progress>
              
              <el-progress
                type="dashboard"
                :percentage="analysisData.risk_distribution.low"
                :color="'#67C23A'"
                :stroke-width="10"
                :width="120"
              >
                <template #default>
                  <div class="progress-content">
                    <span class="progress-value">{{ analysisData.risk_distribution.low }}%</span>
                    <span class="progress-label">保底</span>
                  </div>
                </template>
              </el-progress>
            </div>
            <div class="risk-analysis">
              {{ analysisData.risk_analysis }}
            </div>
          </div>
          
          <el-divider />
          
          <div class="analysis-section">
            <h4>志愿梯度</h4>
            <div class="gradient-analysis">
              {{ analysisData.gradient_analysis }}
            </div>
          </div>
          
          <el-divider />
          
          <div class="analysis-section">
            <h4>专业匹配度</h4>
            <div class="major-match-analysis">
              {{ analysisData.major_match_analysis }}
            </div>
          </div>
          
          <el-divider />
          
          <div class="analysis-section">
            <h4>整体建议</h4>
            <div class="overall-suggestion">
              <el-alert
                :title="analysisData.overall_suggestion"
                type="info"
                :closable="false"
                show-icon>
              </el-alert>
            </div>
          </div>
          
          <div class="action-section">
            <el-button type="primary" @click="handleGenerateReport">生成分析报告</el-button>
            <el-button type="success" @click="handleOptimizeVolunteer">优化志愿方案</el-button>
          </div>
        </div>
      </el-card>
    </div>
    
    <div class="empty-block" v-if="!loading && !analysisData">
      <el-empty description="请输入学生编号进行志愿综合分析" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { analyzeVolunteerComprehensive } from '@/api/recommendation'

// 查询参数
const queryParams = reactive({
  studentId: ''
})

// 分析结果
const analysisData = ref(null)
const loading = ref(false)

// 查询数据
const handleQuery = async () => {
  if (!queryParams.studentId) {
    ElMessage.warning('请输入学生编号')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用，使用测试数据
    // const res = await analyzeVolunteerComprehensive(queryParams.studentId)
    
    // 使用测试数据
    const mockData = {
      code: 200,
      message: '操作成功',
      data: {
        student_name: '张三',
        score: 650.5,
        rank: 5000,
        volunteer_count: 6,
        overall_rating: '良好',
        rationality_score: 4,
        safety_score: 3.5,
        gradient_score: 4.5,
        major_match_score: 4,
        risk_distribution: {
          high: 33,
          medium: 50,
          low: 17
        },
        risk_analysis: '您的志愿填报风险分布较为合理，冲刺、稳妥和保底志愿比例为3:3:0，建议适当增加保底志愿以提高录取保障。',
        gradient_analysis: '志愿梯度设置合理，相邻志愿间分数差距适中，有利于提高录取机会。',
        major_match_analysis: '专业选择较为集中在计算机类和电子信息类，与您的兴趣和能力匹配度较高。',
        overall_suggestion: '总体来看，您的志愿填报方案较为合理，但建议增加1-2个保底志愿，以确保录取。同时可以考虑在专业选择上适当增加多样性，拓宽录取渠道。',
        volunteers: [
          {
            order: 1,
            university_name: '北京大学',
            major_name: '计算机科学与技术',
            last_year_score: 680,
            score_difference: -29.5,
            admission_probability: 35,
            risk_level: '高风险'
          },
          {
            order: 2,
            university_name: '清华大学',
            major_name: '软件工程',
            last_year_score: 685,
            score_difference: -34.5,
            admission_probability: 30,
            risk_level: '高风险'
          },
          {
            order: 3,
            university_name: '浙江大学',
            major_name: '人工智能',
            last_year_score: 660,
            score_difference: -9.5,
            admission_probability: 65,
            risk_level: '中风险'
          },
          {
            order: 4,
            university_name: '复旦大学',
            major_name: '数据科学与大数据技术',
            last_year_score: 655,
            score_difference: -4.5,
            admission_probability: 70,
            risk_level: '中风险'
          },
          {
            order: 5,
            university_name: '上海交通大学',
            major_name: '信息安全',
            last_year_score: 650,
            score_difference: 0.5,
            admission_probability: 75,
            risk_level: '中风险'
          },
          {
            order: 6,
            university_name: '南京大学',
            major_name: '软件工程',
            last_year_score: 645,
            score_difference: 5.5,
            admission_probability: 85,
            risk_level: '低风险'
          }
        ]
      }
    };
    
    const res = mockData;
    
    if (res.code === 200 && res.data) {
      analysisData.value = res.data
      ElMessage.success('分析完成')
    } else {
      ElMessage.error(res.message || '分析失败')
      analysisData.value = null
    }
  } catch (error) {
    console.error('志愿综合分析失败', error)
    ElMessage.error('志愿综合分析失败，请检查网络或服务器状态')
    analysisData.value = null
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

// 获取概率颜色
const getProbabilityColor = (probability) => {
  if (probability < 50) return '#F56C6C'
  if (probability < 80) return '#E6A23C'
  return '#67C23A'
}

// 格式化百分比
const percentageFormat = (percentage) => {
  return `${percentage}%`
}

// 获取风险等级类型
const getRiskLevelType = (level) => {
  if (level.includes('高')) return 'danger'
  if (level.includes('中')) return 'warning'
  return 'success'
}

// 获取总体评价标签类型
const getOverallRatingTagType = () => {
  if (!analysisData.value) return 'info'
  
  const rating = analysisData.value.overall_rating
  if (rating.includes('优')) return 'success'
  if (rating.includes('良')) return 'warning'
  if (rating.includes('一般')) return 'info'
  return 'danger'
}

// 生成分析报告
const handleGenerateReport = () => {
  ElMessage.info('生成分析报告功能开发中...')
}

// 优化志愿方案
const handleOptimizeVolunteer = () => {
  ElMessage.info('优化志愿方案功能开发中...')
}
</script>

<style scoped>
.comprehensive-analysis-container {
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

.overview-card,
.volunteers-card,
.analysis-card {
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

.summary-section {
  margin-bottom: 15px;
}

.student-info {
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

.score {
  font-weight: bold;
  color: #409EFF;
}

.rating-section {
  margin: 20px 0;
}

.rating-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  color: #303133;
}

.rating-items {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.rating-item {
  margin-right: 30px;
  margin-bottom: 10px;
}

.rating-name {
  margin-bottom: 5px;
  font-weight: bold;
  color: #606266;
}

.score-high {
  color: #67c23a;
  font-weight: bold;
}

.score-medium {
  color: #e6a23c;
  font-weight: bold;
}

.score-low {
  color: #f56c6c;
  font-weight: bold;
}

.analysis-content {
  padding: 10px 0;
}

.analysis-section {
  margin: 20px 0;
}

.analysis-section h4 {
  margin: 0 0 15px 0;
  font-size: 16px;
  color: #303133;
}

.risk-distribution {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-bottom: 20px;
}

.progress-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.progress-value {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
}

.progress-label {
  font-size: 14px;
  color: #909399;
}

.risk-analysis,
.gradient-analysis,
.major-match-analysis {
  color: #606266;
  line-height: 1.6;
  margin: 15px 0;
}

.overall-suggestion {
  margin: 15px 0;
}

.action-section {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

.empty-block {
  margin-top: 40px;
}
</style> 
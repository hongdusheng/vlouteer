<template>
  <div class="university-major-match-container">
    <div class="page-header">
      <h2>院校专业匹配</h2>
      <p class="description">
        根据您的高考成绩和专业偏好，匹配最适合您的院校和专业组合
      </p>
    </div>
    
    <div class="search-section">
      <el-card shadow="hover">
        <el-form :model="queryParams" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="学生编号">
                <el-input 
                  v-model="queryParams.studentId" 
                  placeholder="请输入学生编号" 
                  clearable 
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="专业偏好">
                <el-select 
                  v-model="queryParams.majorPreference" 
                  placeholder="请选择专业偏好" 
                  clearable
                  multiple
                  collapse-tags
                  collapse-tags-tooltip
                >
                  <el-option
                    v-for="item in majorOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="地区偏好">
                <el-select 
                  v-model="queryParams.locationPreference" 
                  placeholder="请选择地区偏好" 
                  clearable
                  multiple
                  collapse-tags
                  collapse-tags-tooltip
                >
                  <el-option
                    v-for="item in locationOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="匹配数量">
                <el-input-number 
                  v-model="queryParams.matchCount" 
                  :min="5" 
                  :max="20" 
                  :step="5" 
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="匹配类型">
                <el-radio-group v-model="queryParams.matchType">
                  <el-radio label="balanced">均衡匹配</el-radio>
                  <el-radio label="university">院校优先</el-radio>
                  <el-radio label="major">专业优先</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item>
                <el-button type="primary" @click="handleQuery" :loading="loading">开始匹配</el-button>
                <el-button @click="resetQuery">重置</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
    </div>
    
    <div v-if="matchData && matchData.length > 0" class="result-section">
      <el-card shadow="hover" class="result-card">
        <template #header>
          <div class="card-header">
            <h3>匹配结果 (共{{ matchData.length }}个)</h3>
            <div class="header-actions">
              <el-radio-group v-model="viewMode" size="small">
                <el-radio-button label="card">卡片视图</el-radio-button>
                <el-radio-button label="table">表格视图</el-radio-button>
              </el-radio-group>
            </div>
          </div>
        </template>
        
        <!-- 卡片视图 -->
        <div v-if="viewMode === 'card'" class="card-view">
          <el-row :gutter="20">
            <el-col 
              v-for="(item, index) in matchData" 
              :key="index" 
              :xs="24" 
              :sm="12" 
              :md="8" 
              :lg="6"
              class="match-card-col"
            >
              <el-card shadow="hover" class="match-card">
                <div class="match-header">
                  <h4 class="university-name">{{ item.university_name }}</h4>
                  <el-tag :type="getUniversityLevelType(item.university_level)">
                    {{ item.university_level }}
                  </el-tag>
                </div>
                
                <div class="match-content">
                  <div class="match-item">
                    <span class="label">专业名称：</span>
                    <span class="value">{{ item.major_name }}</span>
                  </div>
                  <div class="match-item">
                    <span class="label">专业类别：</span>
                    <span class="value">{{ item.major_category }}</span>
                  </div>
                  <div class="match-item">
                    <span class="label">所在地区：</span>
                    <span class="value">{{ item.location }}</span>
                  </div>
                  <div class="match-item">
                    <span class="label">录取分数：</span>
                    <span class="value score">{{ item.admission_score }}</span>
                  </div>
                  <div class="match-item">
                    <span class="label">分数差距：</span>
                    <span :class="getScoreDifferenceClass(item.score_difference)">
                      {{ formatScoreDifference(item.score_difference) }}
                    </span>
                  </div>
                </div>
                
                <div class="match-footer">
                  <div class="match-rate">
                    <div class="rate-title">匹配度</div>
                    <el-progress 
                      :percentage="item.match_rate" 
                      :color="getMatchRateColor(item.match_rate)"
                      :stroke-width="10"
                      :format="percentageFormat"
                    />
                  </div>
                  
                  <div class="match-actions">
                    <el-button 
                      type="primary" 
                      link 
                      @click="handleAddToVolunteer(item)">
                      加入志愿
                    </el-button>
                    <el-button 
                      type="success" 
                      link 
                      @click="handleViewDetails(item)">
                      查看详情
                    </el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
        
        <!-- 表格视图 -->
        <div v-else class="table-view">
          <el-table
            :data="matchData"
            stripe
            border
            style="width: 100%">
            <el-table-column prop="university_name" label="院校名称" min-width="180" />
            <el-table-column prop="university_level" label="院校层次" width="120">
              <template #default="scope">
                <el-tag :type="getUniversityLevelType(scope.row.university_level)">
                  {{ scope.row.university_level }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="major_name" label="专业名称" min-width="180" />
            <el-table-column prop="major_category" label="专业类别" width="120" />
            <el-table-column prop="location" label="所在地区" width="120" />
            <el-table-column prop="admission_score" label="录取分数" width="100" />
            <el-table-column prop="score_difference" label="分数差距" width="100">
              <template #default="scope">
                <span :class="getScoreDifferenceClass(scope.row.score_difference)">
                  {{ formatScoreDifference(scope.row.score_difference) }}
                </span>
              </template>
            </el-table-column>
            <el-table-column prop="match_rate" label="匹配度" width="150">
              <template #default="scope">
                <el-progress
                  :percentage="scope.row.match_rate"
                  :color="getMatchRateColor(scope.row.match_rate)"
                  :stroke-width="10"
                  :format="percentageFormat"
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
        </div>
      </el-card>
    </div>
    
    <div class="empty-block" v-if="!loading && (!matchData || matchData.length === 0)">
      <el-empty description="请输入查询条件进行院校专业匹配" />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { matchUniversityMajor } from '@/api/recommendation'
import { saveApplication } from '@/api/application'

// 查询参数
const queryParams = reactive({
  studentId: '',
  majorPreference: [],
  locationPreference: [],
  matchCount: 10,
  matchType: 'balanced'
})

// 匹配结果
const matchData = ref([])
const loading = ref(false)
const viewMode = ref('card')

// 专业选项
const majorOptions = [
  { value: '1', label: '计算机类' },
  { value: '2', label: '电子信息类' },
  { value: '3', label: '机械类' },
  { value: '4', label: '土木类' },
  { value: '5', label: '经济学类' },
  { value: '6', label: '管理学类' },
  { value: '7', label: '医学类' },
  { value: '8', label: '文学类' },
  { value: '9', label: '理学类' },
  { value: '10', label: '艺术学类' }
]

// 地区选项
const locationOptions = [
  { value: '1', label: '北京' },
  { value: '2', label: '上海' },
  { value: '3', label: '江苏' },
  { value: '4', label: '浙江' },
  { value: '5', label: '广东' },
  { value: '6', label: '湖北' },
  { value: '7', label: '四川' },
  { value: '8', label: '陕西' },
  { value: '9', label: '辽宁' },
  { value: '10', label: '山东' }
]

// 查询数据
const handleQuery = async () => {
  if (!queryParams.studentId) {
    ElMessage.warning('请输入学生编号')
    return
  }
  
  loading.value = true
  try {
    // 模拟API调用，使用测试数据
    // const res = await matchUniversityMajor(
    //   queryParams.studentId,
    //   queryParams.majorPreference,
    //   queryParams.locationPreference,
    //   queryParams.matchCount,
    //   queryParams.matchType
    // )
    
    // 使用测试数据
    const mockData = {
      code: 200,
      message: '操作成功',
      data: [
        {
          university_id: '10001',
          university_name: '北京大学',
          university_level: '985/211',
          major_id: '080901',
          major_name: '计算机科学与技术',
          major_category: '计算机类',
          location: '北京',
          admission_score: 680,
          score_difference: -29.5,
          match_rate: 95
        },
        {
          university_id: '10002',
          university_name: '清华大学',
          university_level: '985/211',
          major_id: '080902',
          major_name: '软件工程',
          major_category: '计算机类',
          location: '北京',
          admission_score: 685,
          score_difference: -34.5,
          match_rate: 94
        },
        {
          university_id: '10003',
          university_name: '浙江大学',
          university_level: '985/211',
          major_id: '080903',
          major_name: '人工智能',
          major_category: '计算机类',
          location: '浙江',
          admission_score: 660,
          score_difference: -9.5,
          match_rate: 92
        },
        {
          university_id: '10004',
          university_name: '复旦大学',
          university_level: '985/211',
          major_id: '080904',
          major_name: '数据科学与大数据技术',
          major_category: '计算机类',
          location: '上海',
          admission_score: 655,
          score_difference: -4.5,
          match_rate: 90
        },
        {
          university_id: '10005',
          university_name: '上海交通大学',
          university_level: '985/211',
          major_id: '080905',
          major_name: '信息安全',
          major_category: '计算机类',
          location: '上海',
          admission_score: 670,
          score_difference: -19.5,
          match_rate: 88
        },
        {
          university_id: '10006',
          university_name: '南京大学',
          university_level: '985/211',
          major_id: '080902',
          major_name: '软件工程',
          major_category: '计算机类',
          location: '江苏',
          admission_score: 645,
          score_difference: 5.5,
          match_rate: 86
        },
        {
          university_id: '10007',
          university_name: '武汉大学',
          university_level: '985/211',
          major_id: '080901',
          major_name: '计算机科学与技术',
          major_category: '计算机类',
          location: '湖北',
          admission_score: 635,
          score_difference: 15.5,
          match_rate: 84
        },
        {
          university_id: '10008',
          university_name: '华中科技大学',
          university_level: '985/211',
          major_id: '080903',
          major_name: '人工智能',
          major_category: '计算机类',
          location: '湖北',
          admission_score: 630,
          score_difference: 20.5,
          match_rate: 82
        }
      ]
    };
    
    const res = mockData;
    
    if (res.code === 200 && res.data) {
      matchData.value = res.data
      ElMessage.success('匹配完成')
    } else {
      ElMessage.error(res.message || '匹配失败')
      matchData.value = []
    }
  } catch (error) {
    console.error('院校专业匹配失败', error)
    ElMessage.error('院校专业匹配失败，请检查网络或服务器状态')
    matchData.value = []
  } finally {
    loading.value = false
  }
}

// 重置查询条件
const resetQuery = () => {
  queryParams.studentId = ''
  queryParams.majorPreference = []
  queryParams.locationPreference = []
  queryParams.matchCount = 10
  queryParams.matchType = 'balanced'
  matchData.value = []
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

// 获取匹配度颜色
const getMatchRateColor = (rate) => {
  if (rate >= 90) return '#67C23A'
  if (rate >= 80) return '#409EFF'
  if (rate >= 70) return '#E6A23C'
  return '#F56C6C'
}

// 格式化百分比
const percentageFormat = (percentage) => {
  return `${percentage}%`
}

// 获取院校层次标签类型
const getUniversityLevelType = (level) => {
  if (level.includes('985')) return 'danger'
  if (level.includes('211')) return 'warning'
  if (level.includes('双一流')) return 'success'
  return 'info'
}

// 添加到志愿
const handleAddToVolunteer = (row) => {
  ElMessageBox.confirm(`确认将 ${row.university_name} 的 ${row.major_name} 专业添加到志愿中吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'info'
  }).then(() => {
    // 保存志愿
    saveApplication({
      studentId: queryParams.studentId,
      universityId: row.university_id,
      majorId: row.major_id,
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

// 查看详情
const handleViewDetails = (row) => {
  // 跳转到专业详情页面或显示详情弹窗
  ElMessage.info('查看详情功能开发中...')
}
</script>

<style scoped>
.university-major-match-container {
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
}

.result-section {
  margin-top: 30px;
}

.result-card {
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

.card-view {
  margin-top: 20px;
}

.match-card-col {
  margin-bottom: 20px;
}

.match-card {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.match-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.university-name {
  margin: 0;
  font-size: 16px;
  color: #303133;
  font-weight: bold;
}

.match-content {
  flex: 1;
  margin-bottom: 15px;
}

.match-item {
  margin-bottom: 8px;
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

.match-footer {
  margin-top: auto;
}

.match-rate {
  margin-bottom: 15px;
}

.rate-title {
  margin-bottom: 5px;
  font-weight: bold;
  color: #606266;
}

.match-actions {
  display: flex;
  justify-content: space-between;
}

.table-view {
  margin-top: 20px;
}

.empty-block {
  margin-top: 40px;
}
</style> 
<template>
  <div class="application-manage-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
        <el-form-item label="学号" prop="studentNo">
          <el-input
            v-model="queryParams.studentNo"
            placeholder="请输入学号"
            clearable
            @keyup="handleQuery"
            style="width: 200px;"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input
            v-model="queryParams.studentName"
            placeholder="请输入姓名"
            clearable
            @keyup="handleQuery"
            style="width: 200px;"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 表格区域 -->
    <el-table
      v-loading="loading"
      :data="applicationList"
      border
      style="width: 100%"
    >
      <el-table-column type="selection" width="45" align="center" />
      <el-table-column label="ID" align="center" prop="relationId" min-width="60" />
      <el-table-column label="学号" align="center" prop="studentId" min-width="120" />
      <el-table-column label="学生姓名" align="center" prop="studentName" min-width="120" />
      <el-table-column label="省份" align="center" prop="province" min-width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" min-width="170" />
      <el-table-column label="操作" align="center" min-width="120" fixed="right">
        <template #default="scope">
          <el-button type="primary" link @click="showDetail(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页区域 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        prev-text="上一页"
        next-text="下一页"
      >
        <template #total>
          共 <strong>{{ total }}</strong> 条记录
        </template>
        <template #sizes>
          <span class="el-pagination__sizes">
            每页 
            <el-select
              v-model="queryParams.pageSize"
              @change="handleSizeChange"
              :disabled="false">
              <el-option
                v-for="item in [10, 20, 50]"
                :key="item"
                :value="item"
                :label="item + ' 条/页'"
              />
            </el-select>
          </span>
        </template>
      </el-pagination>
    </div>

    <!-- 详情弹框 -->
    <el-dialog
      title="志愿详情"
      v-model="dialog.visible"
      width="700px"
      append-to-body
    >
      <div v-loading="detailLoading" class="detail-container">
        <div v-if="detailData" class="detail-content">
          <div class="detail-header">
            <div class="detail-item">
              <span class="detail-label">学号：</span>
              <span class="detail-value">{{ detailData.studentNo || detailData.studentId }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">姓名：</span>
              <span class="detail-value">{{ detailData.studentName }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">省份：</span>
              <span class="detail-value">{{ detailData.province }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">提交时间：</span>
              <span class="detail-value">{{ detailData.createTime }}</span>
            </div>
          </div>

          <el-divider content-position="center">志愿信息</el-divider>

          <!-- 第一志愿 -->
          <div class="volunteer-detail">
            <div class="volunteer-title">第一志愿</div>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="院校代码">{{ detailData.firstVolunteer?.universityId || '-' }}</el-descriptions-item>
              <el-descriptions-item label="院校名称">{{ detailData.firstVolunteer?.universityName || '-' }}</el-descriptions-item>
              <el-descriptions-item label="专业代码">{{ detailData.firstVolunteer?.majorId || '-' }}</el-descriptions-item>
              <el-descriptions-item label="专业名称">{{ detailData.firstVolunteer?.majorName || '-' }}</el-descriptions-item>
            </el-descriptions>
          </div>

          <!-- 第二志愿 -->
          <div class="volunteer-detail">
            <div class="volunteer-title">第二志愿</div>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="院校代码">{{ detailData.secondVolunteer?.universityId || '-' }}</el-descriptions-item>
              <el-descriptions-item label="院校名称">{{ detailData.secondVolunteer?.universityName || '-' }}</el-descriptions-item>
              <el-descriptions-item label="专业代码">{{ detailData.secondVolunteer?.majorId || '-' }}</el-descriptions-item>
              <el-descriptions-item label="专业名称">{{ detailData.secondVolunteer?.majorName || '-' }}</el-descriptions-item>
            </el-descriptions>
          </div>

          <!-- 第三志愿 -->
          <div class="volunteer-detail">
            <div class="volunteer-title">第三志愿</div>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="院校代码">{{ detailData.thirdVolunteer?.universityId || '-' }}</el-descriptions-item>
              <el-descriptions-item label="院校名称">{{ detailData.thirdVolunteer?.universityName || '-' }}</el-descriptions-item>
              <el-descriptions-item label="专业代码">{{ detailData.thirdVolunteer?.majorId || '-' }}</el-descriptions-item>
              <el-descriptions-item label="专业名称">{{ detailData.thirdVolunteer?.majorName || '-' }}</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
        <div v-else-if="!detailLoading" class="empty-tip">
          <el-empty description="暂无数据" />
        </div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialog.visible = false">关 闭</el-button>
          <el-button type="primary" @click="printDetail" :disabled="!detailData">打 印</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Search, Refresh } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getApplicationList, getApplication } from '@/api/application'

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  studentNo: '',
  studentName: ''
})

// 列表数据
const applicationList = ref([])
const total = ref(0)
const loading = ref(false)

// 表单引用
const queryForm = ref()

// 对话框控制
const dialog = reactive({
  visible: false
})

// 详情数据
const detailData = ref(null)
const detailLoading = ref(false)

// 页面初始加载
onMounted(() => {
  getList()
})

// 获取列表数据
const getList = () => {
  loading.value = true
  getApplicationList(queryParams).then(res => {
    if (res && res.code === 200) {
      applicationList.value = res.data.records || []
      total.value = res.data.total || 0
    } else {
      ElMessage.error(res.message || '获取志愿列表失败')
      applicationList.value = []
      total.value = 0
    }
  }).catch(error => {
    console.error('获取志愿列表出错', error)
    ElMessage.error('获取志愿列表失败，请检查网络或服务器状态')
    applicationList.value = []
    total.value = 0
  }).finally(() => {
    loading.value = false
  })
}

// 处理查询
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

// 重置查询
const resetQuery = () => {
  queryForm.value?.resetFields()
  queryParams.studentNo = ''
  queryParams.studentName = ''
  handleQuery()
}

// 处理分页大小变化
const handleSizeChange = (size) => {
  queryParams.pageSize = size
  getList()
}

// 处理页码变化
const handleCurrentChange = (page) => {
  queryParams.pageNum = page
  getList()
}

// 显示详情
const showDetail = (row) => {
  dialog.visible = true
  detailLoading.value = true
  detailData.value = null
  
  getApplication(row.studentId).then(res => {
    if (res && res.code === 200) {
      detailData.value = res.data
    } else {
      ElMessage.error(res.message || '获取志愿详情失败')
      detailData.value = null
    }
  }).catch(error => {
    console.error('获取志愿详情出错', error)
    ElMessage.error('获取志愿详情失败，请检查网络或服务器状态')
    detailData.value = null
  }).finally(() => {
    detailLoading.value = false
  })
}

// 打印详情
const printDetail = () => {
  ElMessage.success('打印功能将在未来实现')
  // 实际打印功能可以在此处实现
  window.print()
}
</script>

<style scoped>
.application-manage-container {
  padding: 10px;
}

.search-container {
  background-color: #fff;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.detail-container {
  padding: 15px;
}

.detail-header {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 15px;
}

.detail-item {
  margin-right: 30px;
  margin-bottom: 10px;
}

.detail-label {
  font-weight: bold;
  margin-right: 5px;
}

.volunteer-detail {
  margin-bottom: 20px;
}

.volunteer-title {
  font-weight: bold;
  font-size: 16px;
  margin-bottom: 10px;
  color: #409EFF;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}

.empty-tip {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 0;
}

.detail-content {
  min-height: 300px;
}
</style> 
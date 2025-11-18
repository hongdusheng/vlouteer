<template>
  <div class="major-query-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
        <el-form-item label="院校代码" prop="schoolCode">
          <el-input
            v-model="queryParams.schoolCode"
            placeholder="请输入院校代码"
            clearable
            @keyup="handleQuery"
            style="width: 140px;"
          />
        </el-form-item>
        <el-form-item label="院校名称" prop="schoolName">
          <el-input
            v-model="queryParams.schoolName"
            placeholder="请输入院校名称"
            clearable
            @keyup="handleQuery"
            style="width: 140px;"
          />
        </el-form-item>
        <el-form-item label="专业代码" prop="majorCode">
          <el-input
            v-model="queryParams.majorCode"
            placeholder="请输入专业代码"
            clearable
            @keyup="handleQuery"
            style="width: 140px;"
          />
        </el-form-item>
        <el-form-item label="专业名称" prop="majorName">
          <el-input
            v-model="queryParams.majorName"
            placeholder="请输入专业名称"
            clearable
            @keyup="handleQuery"
            style="width: 140px;"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 操作区域 -->
    <div class="tools-container">
      <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      <el-button 
        type="success" 
        plain 
        icon="Edit" 
        :disabled="single" 
        @click="handleEdit"
      >修改</el-button>
      <el-button 
        type="danger" 
        plain 
        icon="Delete" 
        :disabled="selectedIds.length === 0" 
        @click="handleBatchDelete"
      >删除</el-button>
    </div>
      
    <!-- 表格区域 -->
    <el-table
      v-loading="loading"
      :data="majorList"
      @selection-change="handleSelectionChange"
      border
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="专业代码" align="center" prop="majorId" />
      <el-table-column label="专业名称" align="center" prop="majorName" />
      <el-table-column label="院校代码" align="center" prop="universityId" />
      <el-table-column label="院校名称" align="center" prop="universityName" />
      <el-table-column label="修业年限" align="center" prop="schoolingLength" width="90" />
      <el-table-column label="分数线" align="center" prop="lineScore" width="90" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" width="220" fixed="right">
        <template #default="scope">
          <el-button type="primary" link icon="Edit" @click="handleEdit(scope.row)">修改</el-button>
          <el-button type="danger" link icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[5, 10, 15]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        :pager-count="5"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
        <template #total>
          共 <strong>{{ total }}</strong> 条记录
        </template>
      </el-pagination>
    </div>

    <!-- 添加或修改专业对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="600px"
      append-to-body
    >
      <el-form ref="majorFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="专业代码" prop="majorId">
          <el-input v-model="form.majorId" placeholder="请输入专业代码" />
        </el-form-item>
        <el-form-item label="专业名称" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入专业名称" />
        </el-form-item>
        <el-form-item label="学科门类" prop="disciplineCategoryId">
          <el-input v-model="form.disciplineCategoryId" placeholder="请输入学科门类" />
        </el-form-item>
        <el-form-item label="学制" prop="schoolingLength">
          <el-input-number v-model="form.schoolingLength" :min="1" :max="10" :precision="0" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="学位类型" prop="degreeType">
          <el-input v-model="form.degreeType" placeholder="请输入学位类型" />
        </el-form-item>
        <el-form-item label="分数线" prop="lineScore">
          <el-input-number v-model="form.lineScore" :min="0" :max="750" :precision="0" style="width: 100%;" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialog.visible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getMajorPage, getMajor, saveOrUpdateMajor, deleteMajors } from '@/api/major'

// 页面加载状态
const loading = ref(false)

// 专业列表数据
const majorList = ref([])

// 分页总条数
const total = ref(0)

// 选中ID数组
const selectedIds = ref([])

// 单个选中标识
const single = computed(() => selectedIds.value.length !== 1)

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  schoolCode: '',
  schoolName: '',
  majorCode: '',
  majorName: ''
})

// 表单参数
const form = reactive({
  majorId: '',
  majorName: '',
  disciplineCategoryId: '',
  schoolingLength: 4,
  degreeType: '',
  lineScore: 0
})

// 表单校验规则
const rules = {
  majorId: [{ required: true, message: '专业代码不能为空', trigger: 'blur' }],
  majorName: [{ required: true, message: '专业名称不能为空', trigger: 'blur' }],
  disciplineCategoryId: [{ required: true, message: '学科门类不能为空', trigger: 'blur' }],
  schoolingLength: [{ required: true, message: '学制不能为空', trigger: 'blur' }],
  degreeType: [{ required: true, message: '学位类型不能为空', trigger: 'blur' }]
}

// 弹窗配置
const dialog = reactive({
  visible: false,
  title: '添加专业'
})

// 表单引用
const majorFormRef = ref()
const queryForm = ref()

// 页面初始加载
onMounted(() => {
  getList()
})

// 获取专业列表数据
const getList = () => {
  loading.value = true
  getMajorPage(queryParams).then(res => {
    if (res.code === 200) {
      majorList.value = res.data.records
      total.value = res.data.total
    } else {
      ElMessage.error(res.message || '获取专业列表失败')
      majorList.value = []
      total.value = 0
    }
  }).catch(error => {
    console.error('获取专业列表错误', error)
    ElMessage.error('获取专业列表失败，请检查网络或服务器状态')
    majorList.value = []
    total.value = 0
  }).finally(() => {
    loading.value = false
  })
}

// 处理搜索
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

// 重置搜索条件
const resetQuery = () => {
  queryForm.value?.resetFields()
  queryParams.schoolCode = ''
  queryParams.schoolName = ''
  queryParams.majorCode = ''
  queryParams.majorName = ''
  handleQuery()
}

// 分页大小变化事件
const handleSizeChange = (size) => {
  queryParams.pageSize = size
  getList()
}

// 分页页码变化事件
const handleCurrentChange = (page) => {
  queryParams.pageNum = page
  getList()
}

// 复选框选中事件
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.majorId)
}

// 新增按钮点击事件
const handleAdd = () => {
  form.majorId = ''
  form.majorName = ''
  form.disciplineCategoryId = ''
  form.schoolingLength = 4
  form.degreeType = ''
  form.lineScore = 0
  
  dialog.title = '添加专业'
  dialog.visible = true
  
  setTimeout(() => {
    majorFormRef.value?.resetFields()
  }, 200)
}

// 编辑按钮点击事件
const handleEdit = (row) => {
  const majorId = row?.majorId || selectedIds.value[0]
  
  getMajor(majorId).then(res => {
    if (res.code === 200) {
      Object.assign(form, res.data)
    } else {
      ElMessage.error(res.message || '获取专业信息失败')
      // 使用表格行数据作为备选
      if (row) {
        Object.assign(form, row)
      } else {
        const selectedRow = majorList.value.find(item => item.majorId === selectedIds.value[0])
        if (selectedRow) {
          Object.assign(form, selectedRow)
        }
      }
    }
    
    dialog.title = '编辑专业'
    dialog.visible = true
  }).catch(error => {
    console.error('获取专业信息错误', error)
    ElMessage.error('获取专业信息失败，请检查网络或服务器状态')
    // 使用表格行数据作为备选
    if (row) {
      Object.assign(form, row)
    } else {
      const selectedRow = majorList.value.find(item => item.majorId === selectedIds.value[0])
      if (selectedRow) {
        Object.assign(form, selectedRow)
      }
    }
    
    dialog.title = '编辑专业'
    dialog.visible = true
  })
}

// 删除按钮点击事件
const handleDelete = (row) => {
  const majorIds = row?.majorId || selectedIds.value.join(',')
  
  ElMessageBox.confirm('确认删除选中的数据?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    return deleteMajors(majorIds)
  }).then(() => {
    ElMessage.success('删除成功')
    getList()
    selectedIds.value = []
  }).catch(() => {})
}

// 批量删除
const handleBatchDelete = () => {
  handleDelete()
}

// 提交表单
const submitForm = () => {
  majorFormRef.value?.validate(valid => {
    if (valid) {
      saveOrUpdateMajor(form).then(res => {
        if (res.code === 200) {
          ElMessage.success('操作成功')
          dialog.visible = false
          getList()
        } else {
          ElMessage.error(res.message || '保存专业信息失败')
        }
      }).catch(error => {
        console.error('保存专业信息错误', error)
        ElMessage.error('保存专业信息失败，请检查网络或服务器状态')
      })
    }
  })
}
</script>

<style scoped>
.major-query-container {
  padding: 10px;
}

.search-container {
  background-color: #fff;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.tools-container {
  margin-bottom: 15px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-table {
  margin-top: 10px;
}

:deep(.el-input__inner) {
  width: 100%;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style> 
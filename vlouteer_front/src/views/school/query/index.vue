<template>
  <div class="school-query-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
        <el-form-item label="院校代码" prop="universityId">
          <el-input v-model="queryParams.universityId" placeholder="请输入院校代码" clearable @keyup.enter="handleQuery" @clear="handleQuery"
            style="width: 115px;" />
        </el-form-item>
        <el-form-item label="院校名称" prop="universityName">
          <el-input v-model="queryParams.universityName" placeholder="请输入院校名称" clearable @keyup.enter="handleQuery" @clear="handleQuery"
            style="width: 115px;" />
        </el-form-item>
        <el-form-item label="院校层次" prop="level">
          <el-select v-model="queryParams.level" placeholder="请选择院校层次" clearable style="width: 150px;" @change="handleQuery" @clear="handleQuery">
            <el-option label="985" value="985" />
            <el-option label="211" value="211" />
            <el-option label="一本" value="一本" />
            <el-option label="二本" value="二本" />
            <el-option label="专科" value="专科" />
          </el-select>
        </el-form-item>
        <el-form-item label="院校类型" prop="typeId">
          <el-select v-model="queryParams.typeId" placeholder="请选择院校类型" clearable style="width: 150px;" @change="handleQuery" @clear="handleQuery">
            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="queryParams.province" placeholder="请输入心仪省份" clearable @keyup.enter="handleQuery" @clear="handleQuery"
            style="width: 115px;" />
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
      <el-button type="success" plain icon="Edit" :disabled="single" @click="handleEdit">修改</el-button>
      <el-button type="danger" plain icon="Delete" :disabled="selectedIds.length === 0"
        @click="handleBatchDelete">删除</el-button>
    </div>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="schoolList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="院校代码" align="center" prop="universityId" />
      <el-table-column label="院校名称" align="center" prop="universityName" />
      <el-table-column label="所属省份" align="center" prop="province" width="90" />
      <el-table-column label="排名" align="center" prop="ranking" width="70" />
      <el-table-column label="招生人数" align="center" prop="enrollmentNumber" width="90" />
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
      <el-pagination v-model:current-page="queryParams.pageNum" v-model:page-size="queryParams.pageSize"
        :page-sizes="[5, 10, 15]" :total="total" layout="total, sizes, prev, pager, next, jumper" :pager-count="5"
        background @size-change="handleSizeChange" @current-change="handleCurrentChange" prev-text="上一页"
        next-text="下一页">
        <template #total>
          共 <strong>{{ total }}</strong> 条记录
        </template>
        <template #sizes>
          <span class="el-pagination__sizes">
            每页
            <el-select v-model="queryParams.pageSize" @change="handleSizeChange" :disabled="false">
              <el-option v-for="item in [5, 10, 15]" :key="item" :value="item" :label="item + ' 条/页'" />
            </el-select>
          </span>
        </template>
      </el-pagination>
    </div>

    <!-- 添加或修改院校对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="600px" append-to-body>
      <el-form ref="schoolFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="院校代码" prop="universityId">
          <el-input v-model="form.universityId" placeholder="请输入院校代码" />
        </el-form-item>
        <el-form-item label="院校名称" prop="universityName">
          <el-input v-model="form.universityName" placeholder="请输入院校名称" />
        </el-form-item>
        <el-form-item label="所属省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入所属省份" />
        </el-form-item>
        <el-form-item label="院校层次" prop="level">
          <el-select v-model="form.level" placeholder="请选择院校层次" style="width: 100%;">
            <el-option label="985" value="985" />
            <el-option label="211" value="211" />
            <el-option label="一本" value="一本" />
            <el-option label="二本" value="二本" />
            <el-option label="专科" value="专科" />
          </el-select>
        </el-form-item>
        <el-form-item label="院校类型" prop="typeId">
          <el-select v-model="form.typeId" placeholder="请选择院校类型" style="width: 100%;">
            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="排名" prop="ranking">
          <el-input-number v-model="form.ranking" :min="1" :precision="0" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="招生人数" prop="studentCount">
          <el-input-number v-model="form.studentCount" :min="0" :precision="0" style="width: 100%;" />
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
import { getSchoolPage, getSchool, saveOrUpdateSchool, deleteSchools, getSchoolTypes } from '@/api/school'
import { useRouter } from 'vue-router'

// 路由实例
const router = useRouter()

// 登录状态检查
const checkLoginStatus = () => {
  const isLogin = localStorage.getItem('isLogin') === 'true'
  const token = localStorage.getItem('token')
  
  if (!isLogin || !token) {
    ElMessage.warning('您尚未登录或登录已过期，请先登录')
    router.push('/login')
    return false
  }
  return true
}

// 页面加载状态
const loading = ref(false)

// 院校列表数据
const schoolList = ref([])

// 分页总条数
const total = ref(0)

// 选中ID数组
const selectedIds = ref([])

// 单个选中标识
const single = computed(() => selectedIds.value.length !== 1)

// 院校类型选项
const typeOptions = ref([
  { value: '1', label: '综合类' },
  { value: '2', label: '理工类' },
  { value: '3', label: '师范类' },
  { value: '4', label: '医药类' },
  { value: '5', label: '财经类' },
  { value: '6', label: '农林类' },
  { value: '7', label: '艺术类' },
  { value: '8', label: '语言类' }
])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  universityId: '',
  universityName: '',
  level: '',
  typeId: '',
  province: ''
})

// 表单参数
const form = reactive({
  universityId: '',
  universityName: '',
  provinceId: '',
  province: '',
  level: '',
  typeId: '',
  ranking: 0
})

// 表单校验规则
const rules = {
  universityId: [{ required: true, message: '院校代码不能为空', trigger: 'blur' }],
  universityName: [{ required: true, message: '院校名称不能为空', trigger: 'blur' }],
  province: [{ required: true, message: '所属省份不能为空', trigger: 'blur' }],
  level: [{ required: true, message: '院校层次不能为空', trigger: 'change' }],
  typeId: [{ required: true, message: '院校类型不能为空', trigger: 'change' }]
}

// 弹窗配置
const dialog = reactive({
  visible: false,
  title: '添加院校'
})

// 表单引用
const schoolFormRef = ref()
const queryForm = ref()

// 页面初始加载
onMounted(() => {
  // 检查登录状态
  if (!checkLoginStatus()) return
  
  // 加载院校类型列表
  getSchoolTypes().then(res => {
    if (res.code === 200) {
      typeOptions.value = res.data.map(item => ({
        value: item.typeId.toString(),
        label: item.typeName
      }))
    } else {
      ElMessage.warning(res.message || '获取院校类型失败')
    }
  }).catch(error => {
    console.error('获取院校类型错误', error)
    ElMessage.warning('获取院校类型失败，请检查网络或服务器状态')
  })

  // 加载院校列表
  getList()
})

// 获取院校列表数据
const getList = () => {
  loading.value = true
  getSchoolPage(queryParams).then(res => {
    if (res.code === 200) {
      schoolList.value = res.data.records
      total.value = res.data.total
    } else {
      ElMessage.error(res.message || '获取院校列表失败')
      schoolList.value = []
      total.value = 0
    }
  }).catch(error => {
    console.error('获取院校列表错误', error)
    ElMessage.error('获取院校列表失败，请检查网络或服务器状态')
    schoolList.value = []
    total.value = 0
  }).finally(() => {
    loading.value = false
  })
}

// 重置搜索条件
const resetQuery = () => {
  queryForm.value?.resetFields()
  queryParams.universityId = ''
  queryParams.universityName = ''
  queryParams.level = ''
  queryParams.typeId = ''
  queryParams.province = ''
  handleQuery()
}

// 处理查询操作
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
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
  selectedIds.value = selection.map(item => item.universityId)
}

// 新增按钮点击事件
const handleAdd = () => {
  form.universityId = ''
  form.universityName = ''
  form.provinceId = ''
  form.province = ''
  form.level = ''
  form.typeId = ''
  form.ranking = 0

  dialog.title = '添加院校'
  dialog.visible = true

  setTimeout(() => {
    schoolFormRef.value?.resetFields()
  }, 200)
}

// 编辑按钮点击事件
const handleEdit = (row) => {
  const schoolId = row?.universityId || selectedIds.value[0]

  getSchool(schoolId).then(res => {
    if (res.code === 200) {
      Object.assign(form, res.data)
    } else {
      ElMessage.error(res.message || '获取院校信息失败')
      // 使用表格行数据作为备选
      if (row) {
        Object.assign(form, row)
      } else {
        const selectedRow = schoolList.value.find(item => item.universityId === selectedIds.value[0])
        if (selectedRow) {
          Object.assign(form, selectedRow)
        }
      }
    }

    dialog.title = '编辑院校'
    dialog.visible = true
  }).catch(error => {
    console.error('获取院校信息错误', error)
    ElMessage.error('获取院校信息失败，请检查网络或服务器状态')
    // 使用表格行数据作为备选
    if (row) {
      Object.assign(form, row)
    } else {
      const selectedRow = schoolList.value.find(item => item.universityId === selectedIds.value[0])
      if (selectedRow) {
        Object.assign(form, selectedRow)
      }
    }

    dialog.title = '编辑院校'
    dialog.visible = true
  })
}

// 删除按钮点击事件
const handleDelete = (row) => {
  const schoolIds = row?.universityId || selectedIds.value.join(',')

  ElMessageBox.confirm('确认删除选中的数据?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    return deleteSchools(schoolIds)
  }).then(() => {
    ElMessage.success('删除成功')
    getList()
    selectedIds.value = []
  }).catch(() => { })
}

// 批量删除
const handleBatchDelete = () => {
  handleDelete()
}

// 提交表单
const submitForm = () => {
  schoolFormRef.value?.validate(valid => {
    if (valid) {
      saveOrUpdateSchool(form).then(res => {
        if (res.code === 200) {
          ElMessage.success('操作成功')
          dialog.visible = false
          getList()
        } else {
          ElMessage.error(res.message || '保存院校信息失败')
        }
      }).catch(error => {
        console.error('保存院校信息错误', error)
        ElMessage.error('保存院校信息失败，请检查网络或服务器状态')
      })
    }
  })
}
</script>

<style scoped>
.school-query-container {
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
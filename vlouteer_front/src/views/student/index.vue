<template>
  <div class="student-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
        <el-form-item label="学生编号" prop="studentId">
          <el-input
            v-model="queryParams.studentId"
            placeholder="请输入学生编号"
            clearable
            @keyup="handleQuery"
            style="width: 150px;"
          />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <el-input
            v-model="queryParams.studentName"
            placeholder="请输入学生姓名"
            clearable
            @keyup="handleQuery"
            style="width: 150px;"
          />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="queryParams.gender" placeholder="请选择性别" clearable style="width: 150px;">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="省份" prop="provinceId">
          <el-select v-model="queryParams.provinceId" placeholder="请选择省份" clearable style="width: 150px;">
            <el-option 
              v-for="item in provinceOptions" 
              :key="item.value" 
              :label="item.label" 
              :value="item.value"
            />
          </el-select>
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
      :data="studentList"
      @selection-change="handleSelectionChange"
      border
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学生编号" align="center" prop="studentId" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="性别" align="center" prop="gender" width="60" />
      <el-table-column label="身份证号" align="center" prop="idCard" width="180" />
      <el-table-column label="高考成绩" align="center" prop="collegeEntranceExamScore" width="90" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="所属省份" align="center" prop="provinceName" />
      <el-table-column label="排名" align="center" prop="ranking" width="80" />
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
                v-for="item in [5,10,15]"
                :key="item"
                :value="item"
                :label="item + ' 条/页'"
              />
            </el-select>
          </span>
        </template>
      </el-pagination>
    </div>

    <!-- 添加或修改学生对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="600px"
      append-to-body
    >
      <el-form ref="studentFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="学生编号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学生编号" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%;">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="高考成绩" prop="collegeEntranceExamScore">
          <el-input-number v-model="form.collegeEntranceExamScore" :min="0" :max="750" :precision="1" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="所属省份" prop="provinceId">
          <el-select v-model="form.provinceId" placeholder="请选择省份" style="width: 100%;">
            <el-option
              v-for="item in provinceOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="排名" prop="ranking">
          <el-input-number v-model="form.ranking" :min="0" :precision="0" style="width: 100%;" />
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
import { getStudentPage, getStudent, saveOrUpdateStudent, deleteStudents, getProvinceList } from '@/api/student'

// 页面加载状态
const loading = ref(false)

// 学生列表数据
const studentList = ref([])

// 分页总条数
const total = ref(0)

// 选中ID数组
const selectedIds = ref([])

// 单个选中标识
const single = computed(() => selectedIds.value.length !== 1)

// 省份选项
const provinceOptions = ref([
  { value: '1', label: '北京市' },
  { value: '2', label: '天津市' },
  { value: '3', label: '河北省' },
  { value: '4', label: '山西省' },
  { value: '5', label: '内蒙古自治区' },
  { value: '6', label: '辽宁省' },
  { value: '7', label: '吉林省' },
  { value: '8', label: '黑龙江省' },
  { value: '9', label: '上海市' },
  { value: '10', label: '江苏省' },
  { value: '11', label: '浙江省' },
  { value: '12', label: '安徽省' },
  { value: '13', label: '福建省' },
  { value: '14', label: '江西省' },
  { value: '15', label: '山东省' },
  { value: '16', label: '河南省' },
  { value: '17', label: '湖北省' },
  { value: '18', label: '湖南省' },
  { value: '19', label: '广东省' },
  { value: '20', label: '广西壮族自治区' },
  { value: '21', label: '海南省' },
  { value: '22', label: '重庆市' },
  { value: '23', label: '四川省' },
  { value: '24', label: '贵州省' },
  { value: '25', label: '云南省' },
  { value: '26', label: '西藏自治区' },
  { value: '27', label: '陕西省' },
  { value: '28', label: '甘肃省' },
  { value: '29', label: '青海省' },
  { value: '30', label: '宁夏回族自治区' },
  { value: '31', label: '新疆维吾尔自治区' }
])

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  studentId: '',
  studentName: '',
  gender: '',
  provinceId: ''
})

// 表单参数
const form = reactive({
  studentId: '',
  studentName: '',
  gender: '',
  idCard: '',
  collegeEntranceExamScore: 0,
  provinceId: '',
  ranking: 0
})

// 表单校验规则
const rules = {
  studentId: [{ required: true, message: '学生编号不能为空', trigger: 'blur' }],
  studentName: [{ required: true, message: '学生姓名不能为空', trigger: 'blur' }],
  gender: [{ required: true, message: '性别不能为空', trigger: 'change' }],
  idCard: [{ required: true, message: '身份证号不能为空', trigger: 'blur' }],
  collegeEntranceExamScore: [{ required: true, message: '高考成绩不能为空', trigger: 'blur' }],
  provinceId: [{ required: true, message: '所属省份不能为空', trigger: 'change' }]
}

// 弹窗配置
const dialog = reactive({
  visible: false,
  title: '添加学生'
})

// 表单引用
const studentFormRef = ref()
const queryForm = ref()

// 页面初始加载
onMounted(() => {
  getList()
  loadProvinceOptions()
})

// 加载省份选项
const loadProvinceOptions = () => {
  getProvinceList().then(res => {
    if (res.code === 200 && res.data) {
      provinceOptions.value = res.data.map(item => ({
        value: item.provinceId.toString(),
        label: item.provinceName
      }))
    } else {
      ElMessage.warning(res.message || '获取省份数据失败')
    }
  }).catch(error => {
    console.error('获取省份数据错误', error)
    ElMessage.warning('获取省份数据失败，请检查网络或服务器状态')
  })
}

// 获取学生列表数据
const getList = () => {
  loading.value = true
  getStudentPage(queryParams).then(res => {
    if (res.code === 200) {
      studentList.value = res.data.records
      total.value = res.data.total
    } else {
      ElMessage.error(res.message || '获取学生列表失败')
      studentList.value = []
      total.value = 0
    }
  }).catch(error => {
    console.error('获取学生列表错误', error)
    ElMessage.error('获取学生列表失败，请检查网络或服务器状态')
    studentList.value = []
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
  queryParams.studentId = ''
  queryParams.studentName = ''
  queryParams.gender = ''
  queryParams.provinceId = ''
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
  selectedIds.value = selection.map(item => item.studentId)
}

// 新增按钮点击事件
const handleAdd = () => {
  form.studentId = ''
  form.studentName = ''
  form.gender = ''
  form.idCard = ''
  form.collegeEntranceExamScore = 0
  form.provinceId = ''
  form.ranking = 0
  
  dialog.title = '添加学生'
  dialog.visible = true
  
  setTimeout(() => {
    studentFormRef.value?.resetFields()
  }, 200)
}

// 编辑按钮点击事件
const handleEdit = (row) => {
  const studentId = row?.studentId || selectedIds.value[0]
  
  getStudent(studentId).then(res => {
    if (res.code === 200) {
      Object.assign(form, res.data)
    } else {
      ElMessage.error(res.message || '获取学生信息失败')
      // 使用表格行数据作为备选
      if (row) {
        Object.assign(form, row)
      } else {
        const selectedRow = studentList.value.find(item => item.studentId === selectedIds.value[0])
        if (selectedRow) {
          Object.assign(form, selectedRow)
        }
      }
    }
    
    dialog.title = '编辑学生'
    dialog.visible = true
  }).catch(error => {
    console.error('获取学生信息错误', error)
    ElMessage.error('获取学生信息失败，请检查网络或服务器状态')
    // 使用表格行数据作为备选
    if (row) {
      Object.assign(form, row)
    } else {
      const selectedRow = studentList.value.find(item => item.studentId === selectedIds.value[0])
      if (selectedRow) {
        Object.assign(form, selectedRow)
      }
    }
    
    dialog.title = '编辑学生'
    dialog.visible = true
  })
}

// 删除按钮点击事件
const handleDelete = (row) => {
  const studentIds = row?.studentId || selectedIds.value.join(',')
  
  ElMessageBox.confirm('确认删除选中的数据?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    return deleteStudents(studentIds)
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
  studentFormRef.value?.validate(valid => {
    if (valid) {
      saveOrUpdateStudent(form).then(res => {
        if (res.code === 200) {
          ElMessage.success('操作成功')
          dialog.visible = false
          getList()
        } else {
          ElMessage.error(res.message || '操作失败')
        }
      }).catch(error => {
        console.error('保存学生信息错误', error)
        ElMessage.error('操作失败，请检查网络或服务器状态')
      })
    }
  })
}
</script>

<style scoped>
.student-container {
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
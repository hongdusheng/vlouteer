<template>
  <div class="user-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-form :inline="true" :model="queryParams" ref="queryForm" class="search-form">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入用户名"
            clearable
            @keyup="handleQuery"
            style="width: 180px;"
          />
        </el-form-item>
        <el-form-item label="用户级别" prop="role">
          <el-select v-model="queryParams.role" placeholder="请选择用户级别" clearable style="width: 180px;">
            <el-option v-for="dict in userLevelOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 180px;">
            <el-option v-for="dict in statusOptions" :key="dict.value" :label="dict.label" :value="dict.value" />
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
        type="danger" 
        plain 
        icon="Delete" 
        :disabled="selectedIds.length === 0" 
        @click="handleBatchDelete"
      >批量删除</el-button>
    </div>
      
    <!-- 表格区域 -->
    <el-table
      v-loading="loading"
      :data="userList"
      @selection-change="handleSelectionChange"
      border
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户编号" align="center" prop="userId" width="80" />
      <el-table-column label="用户名" align="center" prop="username" :show-overflow-tooltip="true" />
      <el-table-column label="用户级别" align="center" prop="role">
        <template #default="scope">
          <dict-tag :options="userLevelOptions" :value="scope.row.role" />
        </template>
      </el-table-column>
      <el-table-column label="学生编号" align="center" prop="studentId" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" width="100">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button type="primary" link icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
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

    <!-- 添加或修改用户对话框 -->
    <el-dialog
      :title="dialog.title"
      v-model="dialog.visible"
      width="600px"
      append-to-body
    >
      <el-form ref="userFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="用户级别" prop="role">
          <el-select v-model="form.role" placeholder="请选择用户级别">
            <el-option
              v-for="item in userLevelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学生编号" prop="studentId" v-if="form.role === 'student'">
          <el-input v-model="form.studentId" placeholder="请输入学生编号" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!form.userId">
          <el-input v-model="form.password" placeholder="请输入密码" show-password />
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
import { ref, reactive, onMounted, nextTick, defineComponent, h } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Edit, Delete } from '@element-plus/icons-vue'
import { getUserPage, getUser, updateUser, deleteUsers, updateUserStatus } from '@/api/user'

// 字典标签组件
const DictTag = defineComponent({
  props: {
    options: {
      type: Array,
      required: true
    },
    value: {
      type: [Number, String],
      required: true
    }
  },
  setup(props) {
    const getOption = () => {
      return props.options.find(item => item.value == props.value) || { label: props.value }
    }
    
    return () => {
      const option = getOption()
      return h('span', {}, option.label)
    }
  }
})

// 页面加载状态
const loading = ref(false)

// 用户列表数据
const userList = ref([])

// 分页总条数
const total = ref(0)

// 选中ID数组
const selectedIds = ref([])

// 用户级别选项
const userLevelOptions = [
  { value: 'admin', label: '超级管理员' },
  { value: 'common', label: '普通角色' },
  { value: 'student', label: '学生角色' }
]

// 状态选项
const statusOptions = [
  { value: 1, label: '启用' },
  { value: 0, label: '禁用' }
]

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  username: '',
  role: '',
  status: ''
})

// 表单参数
const form = reactive({
  userId: undefined,
  username: '',
  password: '',
  role: '',
  status: 1,
  studentId: null
})

// 表单校验规则
const rules = {
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  role: [{ required: true, message: '用户级别不能为空', trigger: 'change' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
}

// 弹窗配置
const dialog = reactive({
  visible: false,
  title: '添加用户'
})

// 表单引用
const userFormRef = ref()
const queryForm = ref()

// 页面初始加载
onMounted(() => {
  getList()
})

// 获取用户列表数据
const getList = () => {
  loading.value = true
  getUserPage(queryParams).then(res => {
    if (res.code === 200) {
      userList.value = res.data.records || []
      total.value = res.data.total || 0
    } else {
      ElMessage.error(res.message || '获取用户列表失败')
      userList.value = []
      total.value = 0
    }
  }).catch(error => {
    console.error('获取用户列表出错', error)
    ElMessage.error('获取用户列表失败，请检查网络或服务器状态')
    userList.value = []
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
  queryForm.value.resetFields()
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
  selectedIds.value = selection.map(item => item.userId)
}

// 状态切换事件
const handleStatusChange = (row) => {
  const status = row.status ? 1 : 0
  const text = status === 1 ? '启用' : '禁用'
  
  ElMessageBox.confirm(`确认要${text}该用户吗?`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    return updateUserStatus({ userId: row.userId, status: status })
  }).then(() => {
    ElMessage.success(`${text}成功`)
    getList()
  }).catch(() => {
    row.status = row.status === 1 ? 0 : 1
  })
}

// 新增按钮点击事件
const handleAdd = () => {
  form.userId = undefined
  form.username = ''
  form.password = ''
  form.role = ''
  form.status = 1
  form.studentId = null
  
  dialog.title = '添加用户'
  dialog.visible = true
  
  nextTick(() => {
    userFormRef.value?.resetFields()
  })
}

// 编辑按钮点击事件
const handleEdit = (row) => {
  const userId = row.userId || 0
  
  getUser(userId).then(res => {
    if (res.code === 200) {
      Object.assign(form, res.data)
      form.password = '' // 编辑时不需要显示密码
    } else {
      ElMessage.error(res.message || '获取用户信息失败')
      // 基于当前行数据填充表单
      form.userId = row.userId
      form.username = row.username
      form.role = row.role
      form.status = row.status
      form.studentId = row.studentId
      form.password = '' // 编辑时不需要填写密码
    }
    
    dialog.title = '编辑用户'
    dialog.visible = true
  }).catch(error => {
    console.error('获取用户信息出错', error)
    ElMessage.error('获取用户信息失败，请检查网络或服务器状态')
    // 基于当前行数据填充表单
    form.userId = row.userId
    form.username = row.username
    form.role = row.role
    form.status = row.status
    form.studentId = row.studentId
    form.password = '' // 编辑时不需要填写密码
    
    dialog.title = '编辑用户'
    dialog.visible = true
  })
}

// 删除按钮点击事件
const handleDelete = (row) => {
  const userIds = row.userId || 0
  
  ElMessageBox.confirm('确认删除选中的数据?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    return deleteUsers(userIds)
  }).then(() => {
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 批量删除
const handleBatchDelete = () => {
  const userIds = selectedIds.value.join(',')
  
  ElMessageBox.confirm('确认删除选中的数据?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消', 
    type: 'warning'
  }).then(() => {
    return deleteUsers(userIds)
  }).then(() => {
    ElMessage.success('删除成功')
    getList()
  }).catch(() => {})
}

// 提交表单
const submitForm = () => {
  userFormRef.value?.validate(valid => {
    if (valid) {
      updateUser(form).then(res => {
        if (res.code === 200) {
          ElMessage.success('操作成功')
          dialog.visible = false
          getList()
        } else {
          ElMessage.error(res.message || '操作失败')
        }
      }).catch(error => {
        console.error('提交表单出错', error)
        ElMessage.error('操作失败，请检查网络或服务器状态')
      })
    }
  })
}
</script>

<style scoped>
.user-container {
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
</style> 
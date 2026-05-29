<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit, Plus, Search } from '@element-plus/icons-vue'
import { createArticle, deleteArticle, getArticles, updateArticle } from '@/api'

const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('新增文章')
const formRef = ref()
const keyword = ref('')
const tableData = ref([])
const editingId = ref(null)
const page = reactive({
  current: 1,
  size: 6,
  total: 0,
})
const form = reactive({
  title: '',
  category: '通知公告',
  author: '管理员',
  summary: '',
  status: '草稿',
})

const rules = {
  title: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
  category: [{ required: true, message: '请选择分类', trigger: 'change' }],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  summary: [{ required: true, message: '请输入摘要', trigger: 'blur' }],
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getArticles({
      keyword: keyword.value,
      page: page.current,
      size: page.size,
    })
    if (res.success) {
      tableData.value = res.data.records
      page.total = res.data.total
    }
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  Object.assign(form, {
    title: '',
    category: '通知公告',
    author: '管理员',
    summary: '',
    status: '草稿',
  })
  editingId.value = null
}

const openCreate = () => {
  resetForm()
  dialogTitle.value = '新增文章'
  dialogVisible.value = true
}

const openEdit = (row) => {
  editingId.value = row.id
  dialogTitle.value = '编辑文章'
  Object.assign(form, {
    title: row.title,
    category: row.category,
    author: row.author,
    summary: row.summary,
    status: row.status,
  })
  dialogVisible.value = true
}

const submit = async () => {
  await formRef.value.validate()
  const res = editingId.value ? await updateArticle(editingId.value, form) : await createArticle(form)
  if (!res.success) {
    ElMessage.error(res.message)
    return
  }
  ElMessage.success(res.message)
  dialogVisible.value = false
  loadData()
}

const remove = async (row) => {
  await ElMessageBox.confirm(`确定删除文章“${row.title}”吗？`, '删除确认', { type: 'warning' })
  const res = await deleteArticle(row.id)
  if (res.success) {
    ElMessage.success(res.message)
    loadData()
  }
}

const search = () => {
  page.current = 1
  loadData()
}

onMounted(loadData)
</script>

<template>
  <section class="panel full-card">
    <div class="panel-body">
      <div class="table-header">
        <div>
          <h3>文章管理</h3>
          <p>维护公告、资料和系统说明，支持快速检索与内容更新。</p>
        </div>
        <div class="table-actions">
          <el-input v-model="keyword" :prefix-icon="Search" placeholder="搜索标题、分类或作者" clearable @clear="search" />
          <el-button @click="search">查询</el-button>
          <el-button type="primary" :icon="Plus" @click="openCreate">新增文章</el-button>
        </div>
      </div>

      <el-table v-loading="loading" :data="tableData" stripe border>
        <el-table-column prop="title" label="标题" min-width="220" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="author" label="作者" width="110" />
        <el-table-column prop="summary" label="摘要" min-width="260" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === '已发布' ? 'success' : 'warning'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="170" fixed="right">
          <template #default="{ row }">
            <el-button size="small" :icon="Edit" @click="openEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" :icon="Delete" @click="remove(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="display: flex; justify-content: flex-end; margin-top: 18px">
        <el-pagination
          v-model:current-page="page.current"
          v-model:page-size="page.size"
          layout="total, prev, pager, next"
          :total="page.total"
          @current-change="loadData"
        />
      </div>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
        <el-form-item label="文章标题" prop="title">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="form.category" style="width: 100%">
            <el-option label="通知公告" value="通知公告" />
            <el-option label="学习资料" value="学习资料" />
            <el-option label="系统说明" value="系统说明" />
            <el-option label="设计规范" value="设计规范" />
          </el-select>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" />
        </el-form-item>
        <el-form-item label="摘要" prop="summary">
          <el-input v-model="form.summary" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio-button label="草稿" value="草稿" />
            <el-radio-button label="已发布" value="已发布" />
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </section>
</template>

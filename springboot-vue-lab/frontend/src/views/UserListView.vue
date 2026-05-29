<script setup>
import { onMounted, reactive, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { getUsers } from '@/api'

const loading = ref(false)
const keyword = ref('')
const tableData = ref([])
const page = reactive({
  current: 1,
  size: 8,
  total: 0,
})

const statusType = (status) => {
  if (status === '正常') return 'success'
  if (status === '待审核') return 'warning'
  return 'info'
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await getUsers({
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
          <h3>用户管理</h3>
          <p>查看成员基础信息、所属部门和账号状态。</p>
        </div>
        <div class="table-actions">
          <el-input v-model="keyword" :prefix-icon="Search" placeholder="搜索姓名、部门或手机号" clearable @clear="search" />
          <el-button type="primary" @click="search">查询</el-button>
        </div>
      </div>
      <el-table v-loading="loading" :data="tableData" stripe border>
        <el-table-column prop="id" label="编号" width="80" />
        <el-table-column prop="name" label="姓名" min-width="110" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="phone" label="手机号" min-width="140" />
        <el-table-column prop="department" label="部门" min-width="140" />
        <el-table-column prop="status" label="状态" width="110">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status)">{{ row.status }}</el-tag>
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
  </section>
</template>

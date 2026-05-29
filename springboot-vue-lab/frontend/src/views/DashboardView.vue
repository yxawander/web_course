<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { getDashboardSummary } from '@/api'

const loading = ref(false)
const summary = reactive({
  loginUserCount: 0,
  userInfoCount: 0,
  articleCount: 0,
  pendingTasks: 0,
  recentArticles: [],
})

const selectedDate = ref(new Date())

const todayPlan = computed(() => [
  { time: '09:00', title: '检查用户注册数据', type: 'primary' },
  { time: '11:00', title: '整理文章发布草稿', type: 'success' },
  { time: '15:00', title: '完成实验报告截图', type: 'warning' },
  { time: '20:00', title: '复盘前后端接口联调', type: 'info' },
])

const stats = computed(() => [
  { label: '登录用户', value: summary.loginUserCount, icon: 'UserFilled' },
  { label: '信息用户', value: summary.userInfoCount, icon: 'Avatar' },
  { label: '文章数量', value: summary.articleCount, icon: 'Document' },
  { label: '待办事项', value: summary.pendingTasks, icon: 'Checked' },
])

const loadData = async () => {
  loading.value = true
  try {
    const res = await getDashboardSummary()
    if (res.success) {
      Object.assign(summary, res.data)
    }
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<template>
  <div v-loading="loading" class="content-grid">
    <section v-for="item in stats" :key="item.label" class="panel stat-card">
      <div class="stat-title">
        <el-icon size="26" color="var(--primary)"><component :is="item.icon" /></el-icon>
        <span>{{ item.label }}</span>
      </div>
      <strong>{{ item.value }}</strong>
    </section>

    <section class="panel calendar-card">
      <div class="panel-body">
        <div class="table-header">
          <div>
            <h3>学习与运营日历</h3>
            <p>集中查看近期安排、内容更新和日常处理事项。</p>
          </div>
          <el-tag type="success">今日计划 {{ todayPlan.length }} 项</el-tag>
        </div>
        <el-calendar v-model="selectedDate" />
      </div>
    </section>

    <section class="panel activity-card">
      <div class="panel-body">
        <div class="table-header">
          <div>
            <h3>今日待办</h3>
            <p>按时间顺序跟进当天需要处理的工作。</p>
          </div>
        </div>
        <div v-for="item in todayPlan" :key="item.time" class="calendar-note">
          <div>
            <strong>{{ item.time }}</strong>
            <div>{{ item.title }}</div>
          </div>
          <el-tag :type="item.type">{{ item.type }}</el-tag>
        </div>
      </div>
    </section>

    <section class="panel full-card">
      <div class="panel-body">
        <div class="table-header">
          <div>
            <h3>近期文章</h3>
            <p>查看最新发布和编辑中的内容。</p>
          </div>
        </div>
        <el-table :data="summary.recentArticles" stripe>
          <el-table-column prop="title" label="标题" min-width="220" />
          <el-table-column prop="category" label="分类" width="120" />
          <el-table-column prop="author" label="作者" width="120" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="row.status === '已发布' ? 'success' : 'warning'">{{ row.status }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </section>
  </div>
</template>

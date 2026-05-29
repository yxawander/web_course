<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Lock, User } from '@element-plus/icons-vue'
import { login } from '@/api'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const form = reactive({
  username: 'admin',
  password: '123456',
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

const submit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form)
    if (!res.success) {
      ElMessage.error(res.message)
      return
    }
    localStorage.setItem('currentUser', JSON.stringify(res.data))
    ElMessage.success('登录成功')
    router.push('/app/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <section class="auth-page">
    <div class="auth-visual">
      <div class="brand">
        <span class="brand-mark"><el-icon><Management /></el-icon></span>
        <span>Web课程系统</span>
      </div>
      <div class="auth-title">
        <h1>清晰高效的用户与内容管理平台</h1>
        <p>集中处理账号访问、用户资料、内容发布和日程安排，让日常管理流程更直观。</p>
      </div>
      <div class="auth-highlights">
        <div class="highlight-item">
          <strong>便捷访问</strong>
          <span>登录、注册和密码找回流程清晰易用。</span>
        </div>
        <div class="highlight-item">
          <strong>内容管理</strong>
          <span>文章信息支持查询、维护和状态管理。</span>
        </div>
        <div class="highlight-item">
          <strong>数据列表</strong>
          <span>用户资料分页展示，适合桌面和移动端查看。</span>
        </div>
      </div>
    </div>

    <div class="auth-panel">
      <el-card class="auth-card" shadow="never">
        <h2>账号登录</h2>
        <p class="subtitle">默认账号 admin / 123456</p>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top" size="large">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" :prefix-icon="User" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" :prefix-icon="Lock" type="password" show-password placeholder="请输入密码" />
          </el-form-item>
          <el-button type="primary" size="large" :loading="loading" style="width: 100%" @click="submit">登录系统</el-button>
          <div class="auth-actions">
            <el-link type="primary" @click="router.push('/register')">注册账号</el-link>
            <el-link type="info" @click="router.push('/forgot')">修改密码</el-link>
          </div>
        </el-form>
      </el-card>
    </div>
  </section>
</template>

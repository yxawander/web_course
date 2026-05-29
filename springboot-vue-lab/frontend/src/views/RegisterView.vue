<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { EditPen, Lock, Message, User } from '@element-plus/icons-vue'
import { register } from '@/api'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const form = reactive({
  username: '',
  password: '',
  email: '',
  birthday: '',
  nickname: '',
})

const rules = {
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  email: [
    { required: true, message: '邮箱不能为空', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: ['blur', 'change'] },
  ],
  birthday: [{ required: true, message: '请选择出生日期', trigger: 'change' }],
}

const submit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await register(form)
    if (!res.success) {
      ElMessage.error(res.message)
      return
    }
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <section class="auth-page">
    <div class="auth-visual">
      <div class="brand">
        <span class="brand-mark"><el-icon><UserFilled /></el-icon></span>
        <span>账号注册</span>
      </div>
      <div class="auth-title">
        <h1>创建你的系统账号</h1>
        <p>注册页面包含用户名、密码、邮箱和出生日期校验，大屏采用左右分区，小屏自动压缩为单列。</p>
      </div>
      <div class="auth-highlights">
        <div class="highlight-item"><strong>邮箱校验</strong><span>输入格式错误时即时提示。</span></div>
        <div class="highlight-item"><strong>生日校验</strong><span>日期选择器保证数据规范。</span></div>
        <div class="highlight-item"><strong>接口提交</strong><span>注册信息写入登录用户表。</span></div>
      </div>
    </div>
    <div class="auth-panel">
      <el-card class="auth-card" shadow="never">
        <h2>注册账号</h2>
        <p class="subtitle">请填写基础信息</p>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top" size="large">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" :prefix-icon="User" />
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname" :prefix-icon="EditPen" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" :prefix-icon="Lock" type="password" show-password />
          </el-form-item>
          <el-form-item label="电子邮箱" prop="email">
            <el-input v-model="form.email" :prefix-icon="Message" />
          </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
            <el-date-picker v-model="form.birthday" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
          </el-form-item>
          <el-button type="primary" size="large" :loading="loading" style="width: 100%" @click="submit">提交注册</el-button>
          <div class="auth-actions">
            <el-link type="info" @click="router.push('/login')">返回登录</el-link>
            <el-link type="primary" @click="router.push('/forgot')">修改密码</el-link>
          </div>
        </el-form>
      </el-card>
    </div>
  </section>
</template>

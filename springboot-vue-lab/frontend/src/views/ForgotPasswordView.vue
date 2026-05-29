<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Lock, User } from '@element-plus/icons-vue'
import { changePassword } from '@/api'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const form = reactive({
  username: '',
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const validateConfirm = (_rule, value, callback) => {
  if (value !== form.newPassword) {
    callback(new Error('两次输入的新密码必须一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    { validator: validateConfirm, trigger: ['blur', 'change'] },
  ],
}

const submit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await changePassword(form)
    if (!res.success) {
      ElMessage.error(res.message)
      return
    }
    ElMessage.success('密码修改成功')
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
        <span class="brand-mark"><el-icon><Key /></el-icon></span>
        <span>密码管理</span>
      </div>
      <div class="auth-title">
        <h1>修改账号密码</h1>
        <p>输入旧密码完成身份确认，再设置新的登录密码。</p>
      </div>
      <div class="auth-highlights">
        <div class="highlight-item"><strong>身份确认</strong><span>旧密码正确后才能提交修改。</span></div>
        <div class="highlight-item"><strong>一致性判断</strong><span>两次密码不同无法提交。</span></div>
        <div class="highlight-item"><strong>实时生效</strong><span>修改成功后可立即使用新密码登录。</span></div>
      </div>
    </div>
    <div class="auth-panel">
      <el-card class="auth-card" shadow="never">
        <h2>修改密码</h2>
        <p class="subtitle">请输入旧密码和新密码</p>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top" size="large">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" :prefix-icon="User" />
          </el-form-item>
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input v-model="form.oldPassword" :prefix-icon="Lock" type="password" show-password />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="form.newPassword" :prefix-icon="Lock" type="password" show-password />
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="form.confirmPassword" :prefix-icon="Lock" type="password" show-password />
          </el-form-item>
          <el-button type="primary" size="large" :loading="loading" style="width: 100%" @click="submit">确认修改</el-button>
          <div class="auth-actions">
            <el-link type="info" @click="router.push('/login')">返回登录</el-link>
            <el-link type="primary" @click="router.push('/register')">注册账号</el-link>
          </div>
        </el-form>
      </el-card>
    </div>
  </section>
</template>

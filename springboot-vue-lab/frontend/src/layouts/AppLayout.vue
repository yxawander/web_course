<script setup>
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Menu, SwitchButton } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const drawerVisible = ref(false)
const theme = ref(localStorage.getItem('theme') || 'blue')

const menuItems = [
  { path: '/app/dashboard', label: '首页日历', icon: 'Calendar' },
  { path: '/app/users', label: '用户管理', icon: 'User' },
  { path: '/app/articles', label: '文章管理', icon: 'Document' },
]

const activeMenu = computed(() => route.path)
const shellClass = computed(() => `app-shell theme-${theme.value}`)

const setTheme = (value) => {
  theme.value = value
  localStorage.setItem('theme', value)
}

const go = (path) => {
  drawerVisible.value = false
  router.push(path)
}

const logout = () => {
  localStorage.removeItem('currentUser')
  router.push('/login')
}
</script>

<template>
  <div :class="shellClass">
    <aside class="sidebar">
      <div class="brand">
        <span class="brand-mark"><el-icon><Management /></el-icon></span>
        <span>Web课程系统</span>
      </div>
      <el-menu :default-active="activeMenu" class="side-menu" router>
        <el-menu-item v-for="item in menuItems" :key="item.path" :index="item.path">
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </aside>

    <main class="main-area">
      <div class="topbar">
        <div>
          <el-button class="mobile-menu-button" :icon="Menu" circle @click="drawerVisible = true" />
          <h1>用户内容管理平台</h1>
          <p>统一管理用户资料、内容发布与日程安排</p>
        </div>
        <div class="toolbar">
          <el-segmented
            :model-value="theme"
            :options="[
              { label: '蓝色风格', value: 'blue' },
              { label: '暖黄色', value: 'gold' },
            ]"
            @update:model-value="setTheme"
          />
          <el-button :icon="SwitchButton" @click="logout">退出</el-button>
        </div>
      </div>

      <RouterView />
    </main>

    <el-drawer v-model="drawerVisible" title="功能菜单" direction="ltr" size="260px">
      <el-menu :default-active="activeMenu">
        <el-menu-item v-for="item in menuItems" :key="item.path" :index="item.path" @click="go(item.path)">
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-drawer>
  </div>
</template>

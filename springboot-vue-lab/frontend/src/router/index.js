import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import AppLayout from '../layouts/AppLayout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login',
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue'),
    },
    {
      path: '/forgot',
      name: 'forgot',
      component: () => import('../views/ForgotPasswordView.vue'),
    },
    {
      path: '/app',
      component: AppLayout,
      children: [
        {
          path: '',
          redirect: '/app/dashboard',
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('../views/DashboardView.vue'),
        },
        {
          path: 'users',
          name: 'users',
          component: () => import('../views/UserListView.vue'),
        },
        {
          path: 'articles',
          name: 'articles',
          component: () => import('../views/ArticleManageView.vue'),
        },
      ],
    },
  ],
})

router.beforeEach((to) => {
  const currentUser = localStorage.getItem('currentUser')
  const isAuthPage = ['/login', '/register', '/forgot'].includes(to.path)

  if (to.path.startsWith('/app') && !currentUser) {
    return '/login'
  }

  if (isAuthPage && currentUser) {
    return '/app/dashboard'
  }

  return true
})

export default router

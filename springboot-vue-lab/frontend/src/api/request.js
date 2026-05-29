import axios from 'axios'

const service = axios.create({
  baseURL: '/api',
  timeout: 8000,
})

service.interceptors.response.use(
  (response) => response.data,
  (error) =>
    Promise.resolve({
      success: false,
      message: error.response?.data?.message || '服务器请求失败，请检查后端服务和数据库',
      data: null,
    }),
)

export default service

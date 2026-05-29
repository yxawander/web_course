import request from './request'

export const login = (data) => request.post('/auth/login', data)
export const register = (data) => request.post('/auth/register', data)
export const changePassword = (data) => request.post('/auth/password', data)
export const getDashboardSummary = () => request.get('/dashboard/summary')
export const getUsers = (params) => request.get('/users', { params })
export const getArticles = (params) => request.get('/articles', { params })
export const createArticle = (data) => request.post('/articles', data)
export const updateArticle = (id, data) => request.put(`/articles/${id}`, data)
export const deleteArticle = (id) => request.delete(`/articles/${id}`)

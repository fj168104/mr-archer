import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/auth/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

/**
 * 更新用户的角色
 * @param perm
 */
export function updateUserRoles(data) {
  return request({
    url: '/user/role',
    method: 'post',
    data
  })
}

/**
 * 删除用户
 * @param perm
 */
export function deleteUser(uid) {
  return request({
    url: `/user/delete/${uid}`,
    method: 'delete'
  })
}

/**
 * 查询所有用户记录
 * @param query
 */
export function fetchUserList(data) {
  return request({
    url: '/user/list',
    method: 'post',
    data
  })
}

/**
 * 新增用户
 * @param data
 */
export function createUser(data) {
  return request({
    url: '/user/create',
    method: 'post',
    data
  })
}

/**
 * 更新用户
 * @param id, lock
 */
export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

/**
 * 查询所有用户记录
 * @param query
 */
export function queryUserAllList(data) {
  return request({
    url: '/user/alllist',
    method: 'post',
    data
  })
}
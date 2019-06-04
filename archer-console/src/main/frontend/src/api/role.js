import request from '@/utils/request'

//page query
export function queryRoles(data) {
  return request({
    url: '/role/list',
    method: 'post',
    data
  })
}

export function getRoles(data) {
  return request({
    url: '/roles',
    method: 'get',
    data
  })
}

export function addRole(data) {
  return request({
    url: '/role/create',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: `/role/update`,
    method: 'put',
    data
  })
}

export function deleteRole(rid) {
  return request({
    url: `/role/delete/${rid}`,
    method: 'delete'
  })
}

/**
 * 同步权限数据
 * @param data
 */
export function syncPerm(data) {
  return request({
    url: '/perm/sync',
    method: 'post',
    data
  })
}

import request from '@/utils/request'


export function getRoles(data) {
  return request({
    url: '/roles',
    method: 'get',
    data
  })
}

export function addRole(data) {
  return request({
    url: '/role/add',
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

export function deleteRole(id) {
  return request({
    url: `/role/detete/${id}`,
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

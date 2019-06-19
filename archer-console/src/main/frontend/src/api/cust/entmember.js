import request from '@/utils/request'

/**
 * 查询所有法人代表家族成员记录列表
 * @param data
 */
export function queryEntMemberList(data) {
  return request({
    url: '/entmember/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个法人代表家族成员记录
 * @param data
 */
export function queryEntMember(data) {
  return request({
    url: '/entmember/query',
    method: 'post',
    data
  })
}

/**
 * 新增法人代表家族成员记录
 * @param data
 */
export function createEntMember(data) {
  return request({
    url: '/entmember/create',
    method: 'post',
    data
  })
}

/**
 * 更新法人代表家族成员记录
 * @param data
 */
export function updateEntMember(data) {
  return request({
    url: '/entmember/update',
    method: 'post',
    data
  })
}

/**
 * 删除法人代表家族成员记录
 * @param uid
 */
export function deleteEntMember(uid) {
  return request({
    url: `/entmember/delete/${uid}`,
    method: 'delete'
  })
}

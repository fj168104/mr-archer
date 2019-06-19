import request from '@/utils/request'

/**
 * 查询所有高管记录列表
 * @param data
 */
export function queryEntManagerList(data) {
  return request({
    url: '/entmanager/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个高管记录
 * @param data
 */
export function queryEntManager(data) {
  return request({
    url: '/entmanager/query',
    method: 'post',
    data
  })
}

/**
 * 新增高管记录
 * @param data
 */
export function createEntManager(data) {
  return request({
    url: '/entmanager/create',
    method: 'post',
    data
  })
}

/**
 * 更新高管记录
 * @param data
 */
export function updateEntManager(data) {
  return request({
    url: '/entmanager/update',
    method: 'post',
    data
  })
}

/**
 * 删除高管记录
 * @param uid
 */
export function deleteEntManager(uid) {
  return request({
    url: `/entmanager/delete/${uid}`,
    method: 'delete'
  })
}

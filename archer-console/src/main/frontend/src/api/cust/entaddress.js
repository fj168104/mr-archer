import request from '@/utils/request'

/**
 * 查询所有客户地址信息记录列表
 * @param data
 */
export function queryEntAddressList(data) {
  return request({
    url: '/entaddress/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个客户地址信息记录
 * @param data
 */
export function queryEntAddress(data) {
  return request({
    url: '/entaddress/query',
    method: 'post',
    data
  })
}

/**
 * 新增客户地址信息记录
 * @param data
 */
export function createEntAddress(data) {
  return request({
    url: '/entaddress/create',
    method: 'post',
    data
  })
}

/**
 * 更新客户地址信息记录
 * @param data
 */
export function updateEntAddress(data) {
  return request({
    url: '/entaddress/update',
    method: 'post',
    data
  })
}

/**
 * 删除客户地址信息记录
 * @param uid
 */
export function deleteEntAddress(uid) {
  return request({
    url: `/entaddress/delete/${uid}`,
    method: 'delete'
  })
}
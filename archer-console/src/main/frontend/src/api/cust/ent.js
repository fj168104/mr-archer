import request from '@/utils/request'

/**
 * 查询客户基本信息
 * @param data
 */
export function queryEntInfo(data) {
  return request({
    url: '/cust/entinfo/query',
    method: 'post',
    data
  })
}

/**
 * 更新客户基本信息
 * @param data
 */
export function updateEntInfo(data) {
  return request({
    url: '/cust/entinfo/update',
    method: 'post',
    data
  })
}

/**
 * 查询所有股东记录列表
 * @param data
 */
export function queryEntStockList(data) {
  return request({
    url: '/cust/entstock/list',
    method: 'post',
    data
  })
}

/**
 * 新增股东记录
 * @param data
 */
export function createEntStock(data) {
  return request({
    url: '/cust/entstock/create',
    method: 'post',
    data
  })
}

/**
 * 更新股东记录
 * @param data
 */
export function updateEntStock(data) {
  return request({
    url: '/cust/entstock/update',
    method: 'post',
    data
  })
}

/**
 * 删除股东记录
 * @param uid
 */
export function deleteEntStock(uid) {
  return request({
    url: `/cust/entstock/delete/${uid}`,
    method: 'delete'
  })
}
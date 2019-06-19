import request from '@/utils/request'

/**
 * 查询所有涉诉信息记录列表
 * @param data
 */
export function queryCustomerLegalList(data) {
  return request({
    url: '/customerlegal/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个涉诉信息记录
 * @param data
 */
export function queryCustomerLegal(data) {
  return request({
    url: '/customerlegal/query',
    method: 'post',
    data
  })
}

/**
 * 新增涉诉信息记录
 * @param data
 */
export function createCustomerLegal(data) {
  return request({
    url: '/customerlegal/create',
    method: 'post',
    data
  })
}

/**
 * 更新涉诉信息记录
 * @param data
 */
export function updateCustomerLegal(data) {
  return request({
    url: '/customerlegal/update',
    method: 'post',
    data
  })
}

/**
 * 删除涉诉信息记录
 * @param uid
 */
export function deleteCustomerLegal(uid) {
  return request({
    url: `/customerlegal/delete/${uid}`,
    method: 'delete'
  })
}
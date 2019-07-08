import request from '@/utils/request'

/**
 * 查询所有客户配置信息记录列表
 * @param data
 */
export function queryCustomerConfigList(data) {
  return request({
    url: '/customerconfig/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个客户配置信息记录
 * @param data
 */
export function queryCustomerConfig(data) {
  return request({
    url: '/customerconfig/query',
    method: 'post',
    data
  })
}

/**
 * 新增客户配置信息记录
 * @param data
 */
export function createCustomerConfig(data) {
  return request({
    url: '/customerconfig/create',
    method: 'post',
    data
  })
}

/**
 * 更新客户配置信息记录
 * @param data
 */
export function updateCustomerConfig(data) {
  return request({
    url: '/customerconfig/update',
    method: 'post',
    data
  })
}

/**
 * 删除客户配置信息记录
 * @param uid
 */
export function deleteCustomerConfig(uid) {
  return request({
    url: `/customerconfig/delete/${uid}`,
    method: 'delete'
  })
}
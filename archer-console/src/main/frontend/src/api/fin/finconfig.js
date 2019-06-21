import request from '@/utils/request'

/**
 * 查询所有财报基本配置记录列表
 * @param data
 */
export function queryFinConfigList(data) {
  return request({
    url: '/finconfig/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个财报基本配置记录
 * @param data
 */
export function queryFinConfig(data) {
  return request({
    url: '/finconfig/query',
    method: 'post',
    data
  })
}

/**
 * 新增财报基本配置记录
 * @param data
 */
export function createFinConfig(data) {
  return request({
    url: '/finconfig/create',
    method: 'post',
    data
  })
}

/**
 * 更新财报基本配置记录
 * @param data
 */
export function updateFinConfig(data) {
  return request({
    url: '/finconfig/update',
    method: 'post',
    data
  })
}

/**
 * 删除财报基本配置记录
 * @param uid
 */
export function deleteFinConfig(uid) {
  return request({
    url: `/finconfig/delete/${uid}`,
    method: 'delete'
  })
}

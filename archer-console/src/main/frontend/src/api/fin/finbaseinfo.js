import request from '@/utils/request'

/**
 * 查询所有财报基本数据记录列表
 * @param data
 */
export function queryFinBaseInfoList(data) {
  return request({
    url: '/finbaseinfo/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个财报基本数据记录
 * @param data
 */
export function queryFinBaseInfo(data) {
  return request({
    url: '/finbaseinfo/query',
    method: 'post',
    data
  })
}

/**
 * 新增财报基本数据记录
 * @param data
 */
export function createFinBaseInfo(data) {
  return request({
    url: '/finbaseinfo/create',
    method: 'post',
    data
  })
}

/**
 * 更新财报基本数据记录
 * @param data
 */
export function updateFinBaseInfo(data) {
  return request({
    url: '/finbaseinfo/update',
    method: 'post',
    data
  })
}

/**
 * 删除财报基本数据记录
 * @param uid
 */
export function deleteFinBaseInfo(uid) {
  return request({
    url: `/finbaseinfo/delete/${uid}`,
    method: 'delete'
  })
}

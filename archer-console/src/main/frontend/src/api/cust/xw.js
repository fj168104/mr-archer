import request from '@/utils/request'

/**
 * 查询用户拥有信息查看权的客户列表
 * @param data
 */
export function queryDataList(data) {
  return request({
    url: '/customerinfo/list',
    method: 'post',
    data
  })
}

/**
 * 查询用户拥有业务申办权的客户列表
 * @param data
 */
export function queryBusiCustomerList(data) {
  return request({
    url: '/customerinfo/busilist',
    method: 'post',
    data
  })
}

/**
 * 删除
 * @param uid
 */
export function deleteData(uid) {
  return request({
    url: `/customerinfo/delete/${uid}`,
    method: 'delete'
  })
}

/**
 * 新增
 * @param data
 */
export function createData(data) {
  return request({
    url: '/customerinfo/create',
    method: 'post',
    data
  })
}

/**
 * 更新
 * @param data
 */
export function updateData(data) {
  return request({
    url: '/customerinfo/update',
    method: 'post',
    data
  })
}

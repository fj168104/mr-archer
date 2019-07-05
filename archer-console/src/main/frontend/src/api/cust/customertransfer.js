import request from '@/utils/request'

/**
 * 查询当前用户创建的所有客户转移信息记录列表
 * @param data
 */
export function queryCustomerTransferList(data) {
  return request({
    url: '/customertransfer/list',
    method: 'post',
    data
  })
}

/**
 * 查询当前用户下所有待处理和已处理的客户转移信息记录列表
 * @param data
 */
export function queryCustomerTransferApproveList(data) {
  return request({
    url: '/customertransfer/approvelist',
    method: 'post',
    data
  })
}

/**
 * 查询单个客户转移信息记录
 * @param data
 */
export function queryCustomerTransfer(data) {
  return request({
    url: '/customertransfer/query',
    method: 'post',
    data
  })
}

/**
 * 新增客户转移信息记录
 * @param data
 */
export function createCustomerTransfer(data) {
  return request({
    url: '/customertransfer/create',
    method: 'post',
    data
  })
}

/**
 * 更新客户转移信息记录
 * @param data
 */
export function updateCustomerTransfer(data) {
  return request({
    url: '/customertransfer/update',
    method: 'post',
    data
  })
}

/**
 * 更新客户转移状态信息记录
 * @param data
 */
export function updateCustomerTransferStatus(data) {
  return request({
    url: '/customertransfer/updatestatus',
    method: 'post',
    data
  })
}

/**
 * 删除客户转移信息记录
 * @param uid
 */
export function deleteCustomerTransfer(uid) {
  return request({
    url: `/customertransfer/delete/${uid}`,
    method: 'delete'
  })
}
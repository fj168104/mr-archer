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

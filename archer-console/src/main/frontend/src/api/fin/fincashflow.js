import request from '@/utils/request'

/**
 * 查询现金流量表记录
 * @param data
 */
export function queryFinCashFlow(data) {
  return request({
    url: '/fincashflow/query',
    method: 'post',
    data
  })
}
/**
 * 更新现金流量表记录
 * @param data
 */
export function updateFinCashFlow(data) {
  return request({
    url: '/fincashflow/update',
    method: 'post',
    data
  })
}

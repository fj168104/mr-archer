import request from '@/utils/request'

/**
 * 查询资产负债表记录
 * @param data
 */
export function queryFinBalanceSheet(data) {
  return request({
    url: '/finbalancesheet/query',
    method: 'post',
    data
  })
}
/**
 * 更新资产负债表记录
 * @param data
 */
export function updateFinBalanceSheet(data) {
  return request({
    url: '/finbalancesheet/update',
    method: 'post',
    data
  })
}

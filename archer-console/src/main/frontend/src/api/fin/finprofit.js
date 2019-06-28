import request from '@/utils/request'

/**
 * 查询利润表记录
 * @param data
 */
export function queryFinProfit(data) {
  return request({
    url: '/finprofit/query',
    method: 'post',
    data
  })
}
/**
 * 更新利润表记录
 * @param data
 */
export function updateFinProfit(data) {
  return request({
    url: '/finprofit/update',
    method: 'post',
    data
  })
}

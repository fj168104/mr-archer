import request from '@/utils/request'

/**
 * 查询授信申请下的调查报告列表
 * @param data
 */
export function queryBusinessReportDataList(data) {
  return request({
    url: '/businessreportdata/list',
    method: 'post',
    data
  })
}

/**
 * 更新贷款申请记录
 * @param data
 */
export function updateBusinessReportData(data) {
  return request({
    url: '/businessreportdata/update',
    method: 'post',
    data
  })
}

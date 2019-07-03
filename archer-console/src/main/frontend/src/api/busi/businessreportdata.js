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

export function queryBusinessReportData(data) {
  return request({
    url: '/businessreportdata/query',
    method: 'post',
    data
  })
}

/**
 * 更新调查报告记录
 * @param data
 */
export function updateBusinessReportData(data) {
  return request({
    url: '/businessreportdata/update',
    method: 'post',
    data
  })
}

/**
 * 更新调查报告节点数据
 * @param data
 */
export function updateNodeData(data) {
  return request({
    url: '/businessreportdata/updatenodedata',
    method: 'post',
    data
  })
}

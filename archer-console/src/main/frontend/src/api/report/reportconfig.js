import request from '@/utils/request'

/**
 * 查询所有调查报告配置记录列表
 * @param data
 */
export function queryReportConfigList(data) {
  return request({
    url: '/reportconfig/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个调查报告配置记录
 * @param data
 */
export function queryReportConfig(data) {
  return request({
    url: '/reportconfig/query',
    method: 'post',
    data
  })
}

/**
 * 新增调查报告配置记录
 * @param data
 */
export function createReportConfig(data) {
  return request({
    url: '/reportconfig/create',
    method: 'post',
    data
  })
}

/**
 * 更新调查报告配置记录
 * @param data
 */
export function updateReportConfig(data) {
  return request({
    url: '/reportconfig/update',
    method: 'post',
    data
  })
}

/**
 * 删除调查报告配置记录
 * @param uid
 */
export function deleteReportConfig(uid) {
  return request({
    url: `/reportconfig/delete/${uid}`,
    method: 'delete'
  })
}

import request from '@/utils/request'

/**
 * 查询所有调查报告配置关联信息记录列表
 * @param data
 */
export function queryReportConfigRelaList(data) {
  return request({
    url: '/reportconfigrela/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个调查报告配置关联信息记录
 * @param data
 */
export function queryReportConfigRela(data) {
  return request({
    url: '/reportconfigrela/query',
    method: 'post',
    data
  })
}

/**
 * 新增调查报告配置关联信息记录
 * @param data
 */
export function createReportConfigRela(data) {
  return request({
    url: '/reportconfigrela/create',
    method: 'post',
    data
  })
}

/**
 * 更新调查报告配置关联信息记录
 * @param data
 */
export function updateReportConfigRela(data) {
  return request({
    url: '/reportconfigrela/update',
    method: 'post',
    data
  })
}

/**
 * 批量更新调查报告配置关联信息记录
 * @param data
 */
export function updateReportConfigRelaList(data) {
  return request({
    url: '/reportconfigrela/updatelist',
    method: 'post',
    data
  })
}

/**
 * 删除调查报告配置关联信息记录
 * @param uid
 */
export function deleteReportConfigRela(uid) {
  return request({
    url: `/reportconfigrela/delete/${uid}`,
    method: 'delete'
  })
}

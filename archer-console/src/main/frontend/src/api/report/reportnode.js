import request from '@/utils/request'

/**
 * 查询所有调查报告节点记录列表
 * @param data
 */
export function queryReportNodeList(data) {
  return request({
    url: '/reportnode/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个调查报告节点记录
 * @param data
 */
export function queryReportNode(data) {
  return request({
    url: '/reportnode/query',
    method: 'post',
    data
  })
}

/**
 * 新增调查报告节点记录
 * @param data
 */
export function createReportNode(data) {
  return request({
    url: '/reportnode/create',
    method: 'post',
    data
  })
}

/**
 * 更新调查报告节点记录
 * @param data
 */
export function updateReportNode(data) {
  return request({
    url: '/reportnode/update',
    method: 'post',
    data
  })
}

/**
 * 删除调查报告节点记录
 * @param uid
 */
export function deleteReportNode(uid) {
  return request({
    url: `/reportnode/delete/${uid}`,
    method: 'delete'
  })
}

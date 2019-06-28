import request from '@/utils/request'

/**
 * 查询用户下的所有贷款申请记录列表
 * @param data
 */
export function queryBusinessApplyList(data) {
  return request({
    url: '/businessapply/user/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个贷款申请记录
 * @param data
 */
export function queryBusinessApply(data) {
  return request({
    url: '/businessapply/query',
    method: 'post',
    data
  })
}

/**
 * 新增贷款申请记录
 * @param data
 */
export function createBusinessApply(data) {
  return request({
    url: '/businessapply/create',
    method: 'post',
    data
  })
}

/**
 * 更新贷款申请记录
 * @param data
 */
export function updateBusinessApply(data) {
  return request({
    url: '/businessapply/update',
    method: 'post',
    data
  })
}

/**
 * 删除贷款申请记录
 * @param uid
 */
export function deleteBusinessApply(uid) {
  return request({
    url: `/businessapply/delete/${uid}`,
    method: 'delete'
  })
}
import request from '@/utils/request'

/**
 * 查询所有上下游关联方记录列表
 * @param data
 */
export function queryEntRelateList(data) {
  return request({
    url: '/entrelate/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个上下游关联方记录
 * @param data
 */
export function queryEntRelate(data) {
  return request({
    url: '/entrelate/query',
    method: 'post',
    data
  })
}

/**
 * 新增上下游关联方记录
 * @param data
 */
export function createEntRelate(data) {
  return request({
    url: '/entrelate/create',
    method: 'post',
    data
  })
}

/**
 * 更新上下游关联方记录
 * @param data
 */
export function updateEntRelate(data) {
  return request({
    url: '/entrelate/update',
    method: 'post',
    data
  })
}

/**
 * 删除上下游关联方记录
 * @param uid
 */
export function deleteEntRelate(uid) {
  return request({
    url: `/entrelate/delete/${uid}`,
    method: 'delete'
  })
}
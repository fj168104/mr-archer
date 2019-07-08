import request from '@/utils/request'

/**
 * 查询所有资料清单树图节点关联记录列表
 * @param data
 */
export function queryFilelistTreeNodeRelaList(data) {
  return request({
    url: '/filelisttreenoderela/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个资料清单树图节点关联记录
 * @param data
 */
export function queryFilelistTreeNodeRela(data) {
  return request({
    url: '/filelisttreenoderela/query',
    method: 'post',
    data
  })
}

/**
 * 新增资料清单树图节点关联记录
 * @param data
 */
export function createFilelistTreeNodeRela(data) {
  return request({
    url: '/filelisttreenoderela/create',
    method: 'post',
    data
  })
}

/**
 * 更新资料清单树图节点关联记录
 * @param data
 */
export function updateFilelistTreeNodeRela(data) {
  return request({
    url: '/filelisttreenoderela/update',
    method: 'post',
    data
  })
}

/**
 * 删除资料清单树图节点关联记录
 * @param uid
 */
export function deleteFilelistTreeNodeRela(uid) {
  return request({
    url: `/filelisttreenoderela/delete/${uid}`,
    method: 'delete'
  })
}

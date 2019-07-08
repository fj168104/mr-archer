import request from '@/utils/request'

/**
 * 查询所有资料清单节点记录列表
 * @param data
 */
export function queryFilelistNodeList(data) {
  return request({
    url: '/filelistnode/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个资料清单节点记录
 * @param data
 */
export function queryFilelistNode(data) {
  return request({
    url: '/filelistnode/query',
    method: 'post',
    data
  })
}

/**
 * 新增资料清单节点记录
 * @param data
 */
export function createFilelistNode(data) {
  return request({
    url: '/filelistnode/create',
    method: 'post',
    data
  })
}

/**
 * 更新资料清单节点记录
 * @param data
 */
export function updateFilelistNode(data) {
  return request({
    url: '/filelistnode/update',
    method: 'post',
    data
  })
}

/**
 * 删除资料清单节点记录
 * @param uid
 */
export function deleteFilelistNode(uid) {
  return request({
    url: `/filelistnode/delete/${uid}`,
    method: 'delete'
  })
}

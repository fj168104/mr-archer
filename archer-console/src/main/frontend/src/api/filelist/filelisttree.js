import request from '@/utils/request'

/**
 * 查询所有资料清单配置记录列表
 * @param data
 */
export function queryFilelistTreeList(data) {
  return request({
    url: '/filelisttree/list',
    method: 'post',
    data
  })
}

/**
 * 查询所有资料清单配置记录树图
 * @param data
 */
export function queryFilelistTreeView(data) {
  return request({
    url: '/filelisttree/treelist',
    method: 'post',
    data
  })
}

/**
 * 查询单个资料清单配置记录
 * @param data
 */
export function queryFilelistTree(data) {
  return request({
    url: '/filelisttree/query',
    method: 'post',
    data
  })
}

/**
 * 新增资料清单配置记录
 * @param data
 */
export function createFilelistTree(data) {
  return request({
    url: '/filelisttree/create',
    method: 'post',
    data
  })
}

/**
 * 更新资料清单配置记录
 * @param data
 */
export function updateFilelistTree(data) {
  return request({
    url: '/filelisttree/update',
    method: 'post',
    data
  })
}

/**
 * 删除资料清单配置记录
 * @param uid
 */
export function deleteFilelistTree(uid) {
  return request({
    url: `/filelisttree/delete/${uid}`,
    method: 'delete'
  })
}

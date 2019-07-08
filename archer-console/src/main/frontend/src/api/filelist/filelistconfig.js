import request from '@/utils/request'

/**
 * 查询所有资料清单配置记录列表
 * @param data
 */
export function queryFilelistConfigList(data) {
  return request({
    url: '/filelistconfig/list',
    method: 'post',
    data
  })
}

/**
 * 根据资料清单类型，查询资料清单配置代码列表
 * @param data
 */
export function queryFilelistConfigCodeList(data) {
  return request({
    url: '/filelistconfig/codelist',
    method: 'post',
    data
  })
}

/**
 * 查询单个资料清单配置记录
 * @param data
 */
export function queryFilelistConfig(data) {
  return request({
    url: '/filelistconfig/query',
    method: 'post',
    data
  })
}

/**
 * 新增资料清单配置记录
 * @param data
 */
export function createFilelistConfig(data) {
  return request({
    url: '/filelistconfig/create',
    method: 'post',
    data
  })
}

/**
 * 更新资料清单配置记录
 * @param data
 */
export function updateFilelistConfig(data) {
  return request({
    url: '/filelistconfig/update',
    method: 'post',
    data
  })
}

/**
 * 删除资料清单配置记录
 * @param uid
 */
export function deleteFilelistConfig(uid) {
  return request({
    url: `/filelistconfig/delete/${uid}`,
    method: 'delete'
  })
}

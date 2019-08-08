import request from '@/utils/request'

/**
 * 查询所有资料清单数据记录记录列表
 * @param data
 */
export function queryFilelistRecordList(data) {
  return request({
    url: '/filelistrecord/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个资料清单数据记录记录
 * @param data
 */
export function queryFilelistRecord(data) {
  return request({
    url: '/filelistrecord/query',
    method: 'post',
    data
  })
}

/**
 * 新增资料清单数据记录记录
 * @param data
 */
export function createFilelistRecord(data) {
  return request({
    url: '/filelistrecord/create',
    method: 'post',
    data
  })
}

/**
 * 更新资料清单数据记录记录
 * @param data
 */
export function updateFilelistRecord(data) {
  return request({
    url: '/filelistrecord/update',
    method: 'post',
    data
  })
}

/**
 * 删除资料清单数据记录记录
 * @param uid
 */
export function deleteFilelistRecord(uid) {
  return request({
    url: `/filelistrecord/delete/${uid}`,
    method: 'delete'
  })
}

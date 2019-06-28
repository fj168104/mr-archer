import request from '@/utils/request'

/**
 * 查询所有财报模板记录列表
 * @param data
 */
export function queryFinModelList(data) {
  return request({
    url: '/finmodel/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个财报模板记录
 * @param data
 */
export function queryFinModel(data) {
  return request({
    url: '/finmodel/query',
    method: 'post',
    data
  })
}

/**
 * 新增财报模板记录
 * @param data
 */
export function createFinModel(data) {
  return request({
    url: '/finmodel/create',
    method: 'post',
    data
  })
}

/**
 * 更新财报模板记录
 * @param data
 */
export function updateFinModel(data) {
  return request({
    url: '/finmodel/update',
    method: 'post',
    data
  })
}

/**
 * 删除财报模板记录
 * @param uid
 */
export function deleteFinModel(uid) {
  return request({
    url: `/finmodel/delete/${uid}`,
    method: 'delete'
  })
}

/**
 * 查询所有财报模板信息
 */
export function queryFinModelCodeList(data) {
  return request({
    url: '/finmodel/codelist',
    method: 'post',
    data
  })
}
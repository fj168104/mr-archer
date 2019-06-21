import request from '@/utils/request'

/**
 * 查询所有财报模板配置关联记录列表
 * @param data
 */
export function queryFinModelConfigRefList(data) {
  return request({
    url: '/finmodelconfigref/list',
    method: 'post',
    data
  })
}

export function updateFinModelConfigRefList(data) {
  return request({
    url: '/finmodelconfigref/updatelist',
    method: 'post',
    data
  })
}

/**
 * 查询单个财报模板配置关联记录
 * @param data
 */
export function queryFinModelConfigRef(data) {
  return request({
    url: '/finmodelconfigref/query',
    method: 'post',
    data
  })
}

/**
 * 新增财报模板配置关联记录
 * @param data
 */
export function createFinModelConfigRef(data) {
  return request({
    url: '/finmodelconfigref/create',
    method: 'post',
    data
  })
}

/**
 * 更新财报模板配置关联记录
 * @param data
 */
export function updateFinModelConfigRef(data) {
  return request({
    url: '/finmodelconfigref/update',
    method: 'post',
    data
  })
}

/**
 * 删除财报模板配置关联记录
 * @param uid
 */
export function deleteFinModelConfigRef(uid) {
  return request({
    url: `/finmodelconfigref/delete/${uid}`,
    method: 'delete'
  })
}

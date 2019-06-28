import request from '@/utils/request'

/**
 * 查询所有业务品种记录列表
 * @param data
 */
export function queryBusinessTypeList(data) {
  return request({
    url: '/businesstype/list',
    method: 'post',
    data
  })
}

/**
 * 查询所有业务品种代码列表
 * @param data
 */
export function queryBusinessTypeCodeList(data) {
  return request({
    url: '/businesstype/codelist',
    method: 'post',
    data
  })
}

/**
 * 查询单个业务品种记录
 * @param data
 */
export function queryBusinessType(data) {
  return request({
    url: '/businesstype/query',
    method: 'post',
    data
  })
}

/**
 * 新增业务品种记录
 * @param data
 */
export function createBusinessType(data) {
  return request({
    url: '/businesstype/create',
    method: 'post',
    data
  })
}

/**
 * 更新业务品种记录
 * @param data
 */
export function updateBusinessType(data) {
  return request({
    url: '/businesstype/update',
    method: 'post',
    data
  })
}

/**
 * 删除业务品种记录
 * @param uid
 */
export function deleteBusinessType(uid) {
  return request({
    url: `/businesstype/delete/${uid}`,
    method: 'delete'
  })
}
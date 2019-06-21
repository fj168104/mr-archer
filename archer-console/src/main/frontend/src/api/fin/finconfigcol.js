import request from '@/utils/request'

/**
 * 查询所有财报基本配置记录列表
 * @param data
 */
export function queryFinConfigColList(data) {
  return request({
    url: '/finconfigcol/list',
    method: 'post',
    data
  })
}

/**
 * 批量更新配置信息
 * @param {JSON} data 
 */
export function updateFinConfigColList(data) {
  return request({
    url: '/finconfigcol/updatelist',
    method: 'post',
    data
  })
}


/**
 * 查询单个财报基本配置记录
 * @param data
 */
export function queryFinConfigCol(data) {
  return request({
    url: '/finconfigcol/query',
    method: 'post',
    data
  })
}

/**
 * 新增财报基本配置记录
 * @param data
 */
export function createFinConfigCol(data) {
  return request({
    url: '/finconfigcol/create',
    method: 'post',
    data
  })
}

/**
 * 更新财报基本配置记录
 * @param data
 */
export function updateFinConfigCol(data) {
  return request({
    url: '/finconfigcol/update',
    method: 'post',
    data
  })
}

/**
 * 删除财报基本配置记录
 * @param uid
 */
export function deleteFinConfigCol(uid) {
  return request({
    url: `/finconfigcol/delete/${uid}`,
    method: 'delete'
  })
}

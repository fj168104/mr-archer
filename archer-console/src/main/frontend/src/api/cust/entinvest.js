import request from '@/utils/request'

/**
 * 查询所有对外股权投资记录列表
 * @param data
 */
export function queryEntInvestList(data) {
  return request({
    url: '/entinvest/list',
    method: 'post',
    data
  })
}

/**
 * 查询单个对外股权投资记录
 * @param data
 */
export function queryEntInvest(data) {
  return request({
    url: '/entinvest/query',
    method: 'post',
    data
  })
}

/**
 * 新增对外股权投资记录
 * @param data
 */
export function createEntInvest(data) {
  return request({
    url: '/entinvest/create',
    method: 'post',
    data
  })
}

/**
 * 更新对外股权投资记录
 * @param data
 */
export function updateEntInvest(data) {
  return request({
    url: '/entinvest/update',
    method: 'post',
    data
  })
}

/**
 * 删除对外股权投资记录
 * @param uid
 */
export function deleteEntInvest(uid) {
  return request({
    url: `/entinvest/delete/${uid}`,
    method: 'delete'
  })
}
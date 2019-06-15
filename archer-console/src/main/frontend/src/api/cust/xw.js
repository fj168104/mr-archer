import request from '@/utils/request'

/**
 * 删除
 * @param uid
 */
export function deleteData(uid) {
  return request({
    url: `/cust/xw/delete/${uid}`,
    method: 'delete'
  })
}

/**
 * 查询所有记录
 * @param data
 */
export function queryDataList(data) {
  return request({
    url: '/cust/xw/list',
    method: 'post',
    data
  })
}

/**
 * 新增
 * @param data
 */
export function createData(data) {
  return request({
    url: '/cust/xw/create',
    method: 'post',
    data
  })
}

/**
 * 更新
 * @param data
 */
export function updateData(data) {
  return request({
    url: '/cust/xw/update',
    method: 'post',
    data
  })
}

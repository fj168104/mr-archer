import request from '@/utils/request'

export function queryCodeList(data) {
  return request({
    url: '/syscode/querylist',
    method: 'post',
    data
  })
}
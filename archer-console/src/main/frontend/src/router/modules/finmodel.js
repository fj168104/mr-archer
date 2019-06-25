/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const finModelRouter = {
  path: '/fin/',
  component: Layout,
  redirect: '/fin/model/list',
  alwaysShow: true,
  name: 'fin',
  meta: {
    perm: 'm:fin',
    title: '财务报表配置管理',
    icon: 'peoples'
  },
  children: [
    {
      path: '/fin/model/list',
      component: () => import('@/views/fin/model/list'),
      name: 'finmodellist',
      meta: {
        perm: 'm:fin:templete',
        title: '模板配置管理列表'
      }
    },

    {
      path: '/fin/config/list',
      component: () => import('@/views/fin/config/list'),
      name: 'finconfiglist',
      meta: {
        perm: 'm:fin:common',
        title: '基本配置管理列表'
      }
    }
  ]
}
export default finModelRouter

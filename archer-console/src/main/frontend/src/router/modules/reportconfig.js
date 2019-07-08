/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const reportConfigRouter = {
  path: '/report/config',
  component: Layout,
  redirect: '/report/config/list',
  alwaysShow: true,
  name: 'report',
  meta: {
    perm: 'm:sys',
    title: '调查报告配置管理',
    icon: 'peoples'
  },
  children: [
    {
      path: '/report/config/list',
      component: () => import('@/views/report/config/list'),
      name: 'reportconfiglist',
      meta: {
        perm: 'm:sys',
        title: '调查报告模板配置管理列表'
      }
    },

    {
      path: '/report/node/list',
      component: () => import('@/views/report/node/list'),
      name: 'reportnodelist',
      meta: {
        perm: 'm:sys',
        title: '调查报告节点配置管理列表'
      }
    }
  ]
}
export default reportConfigRouter

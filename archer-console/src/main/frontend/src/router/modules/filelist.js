/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const filelistRouter = {
  path: '/filelist/',
  component: Layout,
  redirect: '/filelist/config/list',
  alwaysShow: true,
  name: 'filelist',
  meta: {
    perm: 'm:sys',
    title: '资料清单配置管理',
    icon: 'peoples'
  },
  children: [
    {
      path: '/filelist/config/list',
      component: () => import('@/views/filelist/config/list'),
      name: 'filelistconfiglist',
      meta: {
        perm: 'm:sys',
        title: '资料清单配置管理列表'
      }
    },
    {
      path: '/filelist/node/list',
      component: () => import('@/views/filelist/node/list'),
      name: 'filelistnodelist',
      meta: {
        perm: 'm:sys',
        title: '资料清单节点管理列表'
      }
    }
  ]
}
export default filelistRouter

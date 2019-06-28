/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const configRouter = {
  path: '/config',
  component: Layout,
  redirect: '/config',
  alwaysShow: true, // will always show the root menu
  name: '配置管理',
  meta: {
    perm: 'm:sys',
    title: '配置管理',
    icon: 'example'
  },
  children: [
    {
      path: 'businesstype/treeview',
      component: () => import('@/views/config/businesstype/treeview'),
      name: '业务品种管理',
      meta: {
        perm: 'm:sys',
        title: '业务品种管理'
      }
    }
  ]
}
export default configRouter

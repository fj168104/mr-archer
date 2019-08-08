/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const custRouter = {
  path: '/cust',
  component: Layout,
  redirect: '/cust/xw/list',
  alwaysShow: true,
  name: 'cust',
  meta: {
    perm: 'm:sys',
    title: '客户管理',
    icon: 'm_customer'
  },
  children: [
    {
      path: '/cust/xw/list',
      component: () => import('@/views/cust/xw/list'),
      name: 'xwlist',
      meta: { 
        perm: 'm:sys',
        title: '小微客户列表'
      }
    },
    
    {
      path: '/cust/ent/manager/treeview',
      component: () => import('@/views/cust/ent/manage/treeview'),
      name: 'xwlist',
      meta: { 
        perm: 'm:sys',
        title: '客户管理工具'
      }
    }
  ]
}
export default custRouter

/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const custRouter = {
  path: '/cust',
  component: Layout,
  redirect: '/cust/xw/list',
  alwaysShow: true,
  name: 'cust',
  meta: {
    title: '客户管理',
    icon: 'peoples'
  },
  children: [
    {
      path: '/cust/xw/list',
      component: () => import('@/views/cust/xw/list'),
      name: 'xwlist',
      meta: { title: '小微客户列表' }
    }
  ]
}
export default custRouter

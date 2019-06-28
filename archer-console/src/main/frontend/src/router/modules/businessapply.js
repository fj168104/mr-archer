/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const businessApplyRouter = {
  path: '/business/apply',
  component: Layout,
  redirect: '/business/apply',
  alwaysShow: true,
  name: 'businessapply',
  meta: {
    perm: 'm:sys',
    title: '授信申请管理',
    icon: 'peoples'
  },
  children: [
    {
      path: 'list',
      component: () => import('@/views/business/apply/list'),
      name: 'businessapplylist',
      meta: { 
        perm: 'm:sys',
        title: '授信申请列表'
      }
    }
  ]
}
export default businessApplyRouter

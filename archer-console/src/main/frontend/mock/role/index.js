import Mock from 'mockjs'
import { deepClone } from '../../src/utils/index.js'
import { asyncRoutes, constantRoutes } from './routes.js'

const routes = deepClone([...constantRoutes, ...asyncRoutes])

const roles = [
  {
    id: 1,
    roleValue: 'admin',
    roleName: 'admin',
    roleDesc: 'Super Administrator. Have access to view all pages.',
    // routes: routes,
    perms:[
      {name:'all', val:'*'}
    ]
  },
  {
    id: 2,
    roleValue: 'editor',
    roleName: 'editor',
    roleDesc: 'Normal Editor. Can see all pages except permission page',
    // routes: routes.filter(i => i.path !== '/permission'),// just a mock
    perms:[
      {name:'system', val:'m:sys'},
      {name:'permission', val:'m:sys:permission'},
      {name:'directive', val:'m:sys:directive'}
      ]
  },
  {
    id: 3,
    roleValue: 'visitor',
    roleName: 'visitor',
    roleDesc: 'Just a visitor. Can only see the home page and the document page',
    routes: [{
      path: '',
      redirect: 'dashboard',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          meta: { title: 'dashboard', icon: 'dashboard' }
        }
      ]
    }]
  }
]

const rList = []
const count = 10

for (let i = 0; i < count; i++) {
  rList.push(roles[i % 3]
  )
}

export default [
  // mock get all routes form server
  {
    url: '/routes',
    type: 'get',
    response: _ => {
      return {
        code: 20000,
        data: routes
      }
    }
  },

  // mock get all roles form server
  {
    url: '/roles',
    type: 'get',
    response: _ => {
      return {
        code: 20000,
        data: roles
      }
    }
  },

  // add role
  {
    url: '/role/create',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: {id : 1000}
      }
    }
  },

  // update role
  {
    url: '/role/update',
    type: 'put',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  },

  // delete role
  {
    url: '/role/[A-Za-z0-9]',
    type: 'delete',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  },

  // sync perm
  {
    url: '/perm/sync',
    type: 'post',
    response: {
      code: 20000,
      data: {
        status: 'success'
      }
    }
  },

  //list with page
  {
    url: '/role/list',
    type: 'post',
    response: config => {
      const { roleValue, roleName, page = 1, limit = 20, sort } = config.query

      let mockList = rList.filter(item => {
        if (roleValue && item.roleValue.indexOf(roleValue) < 0) return false
        if (roleName && item.roleName.indexOf(roleName) < 0) return false
        return true
      })

      const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

      return {
        code: 20000,
        data: {
          total: mockList.length,
          records: pageList
        }
      }
    }
  }

]
